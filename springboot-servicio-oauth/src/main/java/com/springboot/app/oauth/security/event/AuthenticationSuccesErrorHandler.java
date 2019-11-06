package com.springboot.app.oauth.security.event;

import brave.Tracer;
import com.springboot.app.commons.models.entity.Usuario;
import com.springboot.app.oauth.service.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccesErrorHandler implements AuthenticationEventPublisher {

    @Autowired
    private IUsuarioService iUsuarioService;
    private static Logger log = LoggerFactory.getLogger(AuthenticationSuccesErrorHandler.class);
    @Autowired
    private Tracer tracer;
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("Success Login: " + userDetails.getUsername());
        log.info("Success Login: " + userDetails.getUsername());
        Usuario usuario = iUsuarioService.findByUsername(authentication.getName());
        if(usuario.getIntentos() != null && usuario.getIntentos() > 0){
            usuario.setIntentos(0);
        }
        iUsuarioService.update(usuario, usuario.getId());
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        String message = "Error Login: " + e.getMessage();
        System.out.println(message);
        log.info("error Login: " + e.getMessage());
        try {
            StringBuilder errors = new StringBuilder();
            errors.append(message);
            Usuario usuario = iUsuarioService.findByUsername(authentication.getName());
            if(usuario.getIntentos() == null){
                usuario.setIntentos(0);
            }
            usuario.setIntentos(usuario.getIntentos() + 1);
            log.info("Intento: " + usuario.getIntentos());
            errors.append(" - " + "Intento: " + usuario.getIntentos());
            if(usuario.getIntentos() >= 3){
                String errorIntentos = String.format("El usuario %s ha sido deshabilitado por maximos intentos", usuario.getUsername());
                errors.append(" - " + errorIntentos);
                log.error(errorIntentos);
                usuario.setEnabled(false);
            }
            iUsuarioService.update(usuario, usuario.getId());
            tracer.currentSpan().tag("error.mensaje", errors.toString());
        } catch (FeignException ex) {
            log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
        }

    }
}
