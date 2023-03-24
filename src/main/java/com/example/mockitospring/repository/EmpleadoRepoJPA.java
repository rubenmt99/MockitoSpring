package com.example.mockitospring.repository;

import com.example.mockitospring.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpleadoRepoJPA extends JpaRepository<Empleado, String> {

    List<Empleado> findByNombreLike(String nombre);
}
