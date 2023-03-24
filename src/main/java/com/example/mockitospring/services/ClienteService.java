package com.example.mockitospring.services;

import com.example.mockitospring.entity.Cliente;

import java.util.List;


public interface ClienteService {
	Cliente insertar(Cliente c);
	Cliente modificar(Cliente c);
	void eliminar(Cliente c) throws Exception;
	Cliente buscarPorDNI(String dni) throws Exception;
	List<Cliente> buscarTodos() throws Exception;
	List<Cliente> buscarPorNombre(String nombre) throws Exception;
}
