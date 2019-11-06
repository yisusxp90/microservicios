package com.springboot.app.usuarios;

import com.springboot.app.commons.models.entity.Role;
import com.springboot.app.commons.models.entity.Usuario;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    // configuracion para ver los id en el json
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Usuario.class, Role.class);
    }
}
