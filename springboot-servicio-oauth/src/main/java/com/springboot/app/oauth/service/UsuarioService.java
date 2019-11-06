package com.springboot.app.oauth.service;

import brave.Tracer;
import com.springboot.app.commons.models.entity.Usuario;
import com.springboot.app.oauth.client.UsuarioFeignClient;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// UserDetailsService es porpia de spring security
@Service
public class UsuarioService implements UserDetailsService, IUsuarioService {

    @Autowired
    private UsuarioFeignClient client;
    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    @Autowired
    private Tracer tracer;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Usuario usuario = client.finfByUsername(username);
            List<GrantedAuthority> authorities = usuario.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                    .peek(authority -> logger.info(authority.getAuthority()))
                    .collect(Collectors.toList());
            logger.info("Ususario autenticado: " + username);
            return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
        }catch (FeignException e){
            String error = "No existe el usuario: " + username;
            tracer.currentSpan().tag("error.mensaje", error + ": " + e.getMessage());
            logger.info(error);
            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        return client.finfByUsername(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return client.update(usuario, id);
    }
}
