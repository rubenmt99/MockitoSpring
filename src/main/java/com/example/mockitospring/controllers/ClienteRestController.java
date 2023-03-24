package com.example.mockitospring.controllers;

import com.example.mockitospring.entity.Cliente;
import com.example.mockitospring.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {

	@Autowired
	ClienteService service;

	@PostMapping
	public ResponseEntity<Cliente> insertarCliente(@RequestBody Cliente c) {
		try {
			Cliente nuevo = service.insertar(c);
			return new ResponseEntity<> (nuevo, HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<> (new Cliente(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping
	public ResponseEntity<Cliente> modificarCliente(@RequestBody Cliente c) {
		try {
			Cliente modificado = service.modificar(c);
			return new ResponseEntity<> (modificado, HttpStatus.NO_CONTENT);
		} catch(Exception e) {
			return new ResponseEntity<> (new Cliente(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<String> eliminarCliente(@RequestBody Cliente c) {
		try {
			service.eliminar(c);
			return new ResponseEntity<> ("Cliente eliminado", HttpStatus.OK);			
		} catch(Exception e) {
			return new ResponseEntity<> ("Error desconocido", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@GetMapping ("/{dni}")
	public ResponseEntity<Cliente> buscarPorDNI(@PathVariable String dni) {
		try {
			return new ResponseEntity<> ( service.buscarPorDNI(dni), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<> (new Cliente(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> buscarTodos() {
		try {
			return new ResponseEntity<> ( service.buscarTodos(), HttpStatus.OK);
		} catch (Exception ex) {
    		return new ResponseEntity<> (new ArrayList<>(),  HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}

	@GetMapping ("/name/{nombre}")
	public ResponseEntity<List<Cliente>> buscarPorNombre(@PathVariable String nombre) {
		try {
			return new ResponseEntity<> ( service.buscarPorNombre(nombre), HttpStatus.OK);
		} catch (Exception ex) {
    		return new ResponseEntity<> (new ArrayList<>(),  HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	
}
