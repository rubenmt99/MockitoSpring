package com.example.mockitospring.controllers;

import com.example.mockitospring.entity.Empleado;
import com.example.mockitospring.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/empleado")
public class EmpleadoRestController {
	
	@Autowired
	EmpleadoService service;

	//@PreAuthrorize ("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Empleado> insertar(@RequestBody Empleado nuevo) {
		try {
			Empleado empleadoInsertado = service.insertar(nuevo);
			return new ResponseEntity<> (empleadoInsertado, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (new Empleado(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@PutMapping
	public ResponseEntity<Empleado> modificar(Empleado e) {
		try {
			Empleado empleadoModificado = service.modificar(e);
			return new ResponseEntity<> (empleadoModificado, HttpStatus.NO_CONTENT);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (new Empleado(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@GetMapping ("/{username}")
	public ResponseEntity<Empleado> buscarPorUsername(@PathVariable String username) {
		try {
			return new ResponseEntity<> ( service.buscarPorUsername(username) , HttpStatus.OK);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (new Empleado(), HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@DeleteMapping
	public ResponseEntity<String> eliminar(@RequestBody Empleado e) {
		try {
			service.eliminar(e);
			return new ResponseEntity<> ( "Emleado eliminado correctamente" , HttpStatus.OK);
		}
		catch (Exception ex) {
			return new ResponseEntity<> ("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}

	@GetMapping
	public ResponseEntity<List<Empleado>> buscarTodos() {
		try {	
    		return new ResponseEntity<> ( service.buscarTodos() , HttpStatus.OK);
    	} catch (Exception ex) {
    		return new ResponseEntity<> (new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@GetMapping ("/name/{nombre}")
	public ResponseEntity<List<Empleado>> buscarPorNombre(@PathVariable String nombre) {
		try {	
    		return new ResponseEntity<> ( service.buscarPorNombre(nombre) , HttpStatus.OK);
    	}catch (Exception ex) {
    		return new ResponseEntity<> (new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}


}
