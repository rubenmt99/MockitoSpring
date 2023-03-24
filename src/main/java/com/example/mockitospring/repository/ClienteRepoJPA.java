package com.example.mockitospring.repository;

import com.example.mockitospring.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepoJPA extends JpaRepository<Cliente, String> {
	List<Cliente> findByNombreLike (String nombre);
}
