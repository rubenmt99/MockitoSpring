package com.example.mockitospring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table
public class Empleado {
	
	@Id
	@Column(length=15)
	private String username;
	
	@Column 
	private String nombre;
	
	@Column 
	private String apellidos;
	
	@Column
	private long salario;
	
	public Empleado() {
	}

	public Empleado(String username, String nombre, String apellidos, long salario) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.salario = salario;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public long getSalario() {
		return salario;
	}

	public void setSalario(long salario) {
		this.salario = salario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Empleado)) {
			return false;
		}
		Empleado other = (Empleado) obj;
		return Objects.equals(username, other.username);
	}	

}
