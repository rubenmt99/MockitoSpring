package com.example.mockitospring.services;

import java.util.List;

import com.example.mockitospring.entity.Cliente;
import com.example.mockitospring.repository.ClienteRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteRepoJPA dao;
	
	@Override
	public Cliente insertar(Cliente c) {
		return dao.save(c);
	}

	@Override
	public Cliente modificar(Cliente c) {
		return dao.save(c);
	}

	@Override
	public void eliminar(Cliente c) throws Exception {
		dao.delete(c);
	}

	@Override
	public Cliente buscarPorDNI(String dni) throws Exception {
		return dao.findById(dni).orElse(null);
	}

	@Override
	public List<Cliente> buscarTodos() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<Cliente> buscarPorNombre(String nombre) throws Exception {
		return dao.findByNombreLike(nombre);
	}

}
