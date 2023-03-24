package com.example.mockitospring.services;


import java.util.List;

import com.example.mockitospring.entity.Empleado;
import com.example.mockitospring.repository.EmpleadoRepoJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	EmpleadoRepoJPA dao;
	
	@Override
	public Empleado insertar(Empleado e) {
		return dao.save(e);
	}

	@Override
	public Empleado modificar(Empleado e) {
		return dao.save(e);
	}

	@Override
	public Empleado buscarPorUsername(String username) {
		return dao.findById(username).orElse(null);
	}

	@Override
	public void eliminar(Empleado e) {
		dao.delete(e);		
	}

	@Override
	public List<Empleado> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Empleado> buscarPorNombre(String nombre) {
		return dao.findByNombreLike(nombre);
	}




}
