package com.formacionbdi.springboot.app.productos.models.dao;

import com.springboot.app.commons.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductoDao extends CrudRepository<Producto, Long>{

}
