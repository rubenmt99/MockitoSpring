package com.example.mockitospring.services;


import com.example.mockitospring.entity.Empleado;
import com.example.mockitospring.repository.EmpleadoRepoJPA;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@Tag("mockeado")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmpleadoServiceImplTest {
	private static final String username = "userTest";
	
	@Mock
	EmpleadoRepoJPA daoMock;
	
	@InjectMocks
	EmpleadoServiceImpl serviceMock;
	
	@Test
	@Order(1)
	void insertTest() {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 1500);
		
		//WHEN:
		when( daoMock.save(e) ).thenReturn(e);
		Empleado rdo = serviceMock.insertar (e);
		
		//THEN:
		assertEquals(e, rdo);
	}
	
	@Test
	@Order(2)
	void modificarTest() {
		//GIVEN:
		//Empleado e1 = new Empleado(username, "nombre", "ape", 1500);
		Empleado e2 = new Empleado(username, "nombre", "ape", 3500);
		
		//WHEN:
		when ( daoMock.save(e2) ).thenReturn(e2);
		Empleado rdo = serviceMock.modificar(e2);
		
		//THEN:
		assertEquals(e2, rdo);		
	}
	
	@Test
	@Order(3)
	void buscarPorUsernameTest() {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 3500);
		Optional<Empleado> ope = Optional.of(e);
		
		//WHEN:
		when( daoMock.findById(username) ).thenReturn(ope);
		Empleado rdo1 = serviceMock.buscarPorUsername(username);
		Empleado rdo2 = serviceMock.buscarPorUsername("");
		
		//THEN:
		assertAll(  ()-> assertEquals(e, rdo1),
					()-> assertNull(rdo2)
				);		
	}
	
	@Test
	@Order(4)
	void eliminarTest() {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 3500);
		Optional<Empleado> ope = Optional.ofNullable(null);
		
		//WHEN:
		when( daoMock.findById( ArgumentMatchers.anyString() ) ).thenReturn(ope);
		serviceMock.eliminar(e);
		Empleado rdo = serviceMock.buscarPorUsername(username);
		
		//THEN:
		assertNull(rdo);		
	}
	
	@Test
	@Order(5)
	void buscarTodosTest() {
		//GIVEN:
		Empleado e1 = new Empleado(username+"1", "nombre1", "ape2", 1000);
		Empleado e2 = new Empleado(username, "nombre2", "ape2", 3500);
		List<Empleado> le = Arrays.asList(e1, e2);
		
		//WHEN:
		when ( daoMock.findAll() ).thenReturn(le);
		List<Empleado> rdo = serviceMock.buscarTodos();
		
		//THEN:
		assertEquals(2, rdo.size());		
	}
	
	@Test
	@Order(6)
	void buscarPorNombreTest() {
		//GIVEN:
		String nombre="Nombre Empleado";
		Empleado e1 = new Empleado(username+"1", nombre, "ape2", 1000);
		Empleado e2 = new Empleado(username, "nombre2", "ape2", 3500);
		List<Empleado> le = Arrays.asList(e1);
		
		//WHEN:
		when ( daoMock.findByNombreLike(nombre) ).thenReturn(le);
		List<Empleado> rdo = serviceMock.buscarPorNombre(nombre);
		
		//THEN:
		assertAll (	()-> assertEquals(1,  rdo.size()),
					()-> assertEquals(nombre, rdo.get(0).getNombre() )
				);		
	}
}
