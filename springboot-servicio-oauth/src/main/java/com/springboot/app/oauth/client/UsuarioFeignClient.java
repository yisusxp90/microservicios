package com.springboot.app.oauth.client;

import com.springboot.app.commons.models.entity.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

    @GetMapping("/usuarios/search/buscar-username")
    public Usuario finfByUsername(@RequestParam("username") String username);

    @PutMapping("usuarios/{id}")
    public Usuario update(@RequestBody Usuario usuario, @PathVariable("id") Long id);
}
