package com.springboot.app.oauth.service;

import com.springboot.app.commons.models.entity.Usuario;

public interface IUsuarioService {

    public Usuario findByUsername(String username);

    public Usuario update(Usuario usuario, Long id);
}

