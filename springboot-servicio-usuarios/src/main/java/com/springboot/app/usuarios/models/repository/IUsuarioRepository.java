package com.springboot.app.usuarios.models.repository;

import com.springboot.app.commons.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "usuarios")
public interface IUsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

    // por convencion http://localhost:8090/api/usuarios/usuarios/search/buscar-username?username=jesus
    @RestResource(path = "/buscar-username")
    public Usuario findByUsername(@Param("username") String username);

    // por query
    @Query("select u from Usuario u where u.username=?1")
    public Usuario obtenerPorUsername(String username);
}
