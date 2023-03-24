package com.example.mockitospring.services;

import java.util.List;

import com.example.mockitospring.entity.Empleado;

public interface EmpleadoService {
	Empleado insertar(Empleado e);
	Empleado modificar(Empleado e);
	Empleado buscarPorUsername(String username);
	void eliminar(Empleado e);
	List<Empleado> buscarTodos();
	List<Empleado> buscarPorNombre(String nombre);
}
