package com.formacionbdi.springboot.app.item.clientes;

import java.util.List;

import com.springboot.app.commons.models.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {
	
	@GetMapping("/listar")
	public List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable("id") Long id);

	@PostMapping("/crear")
	public Producto crear(@RequestBody Producto producto);

	@PutMapping("/editar/{id}")
	public Producto editar(@RequestBody Producto producto, @PathVariable("id") Long id);

	@DeleteMapping("/eliminar/{id}")
	void eliminar(@PathVariable("id") Long id);


}
