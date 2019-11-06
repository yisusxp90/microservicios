package com.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    // spring va a buscar a algun componente o bean registrado q implemente esta interfaz y lo va a inyectar aca, en nuestro caso UsuarioService
    @Autowired
    private UserDetailsService usuarioService;
    @Autowired
    private AuthenticationEventPublisher authenticationEventPublisher;
    // lo guardamos en el contenedor de spring y poder usarlo despues al encryptar las password, se registra como bean lo q retorna el metodo
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired // tenemos q anotarlo para q se pueda el auth pasar e inyectar mediante el metodo
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // encypt la pass para dar mayor seguridad al pass con bcrypt para eso el passwordEncoder
        auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder())
                .and().authenticationEventPublisher(authenticationEventPublisher);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
