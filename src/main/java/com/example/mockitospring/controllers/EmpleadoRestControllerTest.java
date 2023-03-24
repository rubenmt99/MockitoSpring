package com.example.mockitospring.controllers;

import com.example.mockitospring.entity.Empleado;
import com.example.mockitospring.repository.EmpleadoRepoJPA;
import com.example.mockitospring.services.EmpleadoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Tag("mockeado")
@ExtendWith(MockitoExtension.class)
@TestMethodOrder (MethodOrderer.OrderAnnotation.class)
class EmpleadoRestControllerTest {
	private static final String username = "userTest";
	
	@Mock
	EmpleadoRepoJPA daoMock;
	
	@Mock
	EmpleadoService serviceMock;
	
	@InjectMocks
	EmpleadoRestController controllerMock;
	
	@Test
	@Order (1)
	//@WithMockUser (username="user1", roles= {"ADMIN"})
	void insertarTest() throws Exception {
		//GIVEN:
		Empleado e1 = new Empleado (username, "nomre", "apel", 0);
		Empleado e2 = new Empleado (username, "nomre", "apel", 2000);
		
		//WHEN:
		when ( serviceMock.insertar(e1) ).thenReturn(e2);
		ResponseEntity<Empleado> ree = controllerMock.insertar(e1);
		
		//THEN:
		assertAll(	()-> assertEquals(HttpStatus.CREATED, ree.getStatusCode(), "Código devuelto servicio REST"),
					()-> assertEquals(e2, ree.getBody(), "Mismo empleado"),
					()-> assertEquals(2000, ree.getBody().getSalario(), "Nuevo salario")
				);		
	}
	
	@Test
	@Order (2)
	void insertarTestException() throws Exception {
		//GIVEN:
		Empleado e1 = new Empleado (username, "nomre", "apel", 0);
		
		//WHEN:
		/*doThrow (new Exception()).when( serviceMock ).insertar(e1);*/

		when(serviceMock.insertar(e1)).thenThrow(new RuntimeException());

		ResponseEntity<Empleado> ree = controllerMock.insertar(e1);

		System.out.println(HttpStatus.INTERNAL_SERVER_ERROR.value());
		System.out.println(ree.getStatusCode().value());
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), ree.getStatusCode().value(), "Salta excpción");
	}
	
	@Test
	@Order (3)
	void modificarTest() throws Exception {
		//GIVEN:
		long nuevoSalario=1500;
		Empleado e1 = new Empleado(username, "nombre", "ape", 0);
		Empleado e2 = new Empleado(username, "nombre", "ape", nuevoSalario);
		
		//WHEN:
		when ( serviceMock.modificar(e1) ).thenReturn(e2);
		ResponseEntity<Empleado> re = controllerMock.modificar(e1);		
		
		//THEN:
		assertEquals(HttpStatus.NO_CONTENT, re.getStatusCode(), "Comprobación de código de error del servicio REST" );
		assertEquals(e2, re.getBody(), "Mismo empleado");
		assertEquals(nuevoSalario, re.getBody().getSalario(), "Nuevo salario");
	}
	
	@Test
	@Order (4)
	void modificarTestExcepctin() throws Exception {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 0);
		
		//WHEN:
		doThrow(new RuntimeException()).when( serviceMock ).modificar(e);
		ResponseEntity<Empleado> re = controllerMock.modificar(e);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion" );			
	}
	
	@Test
	@Order (5)
	void buscarPorUsernameTest() throws Exception {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 0);
		
		//WHEN:
		when (serviceMock.buscarPorUsername(username)).thenReturn(e);
		ResponseEntity<Empleado> re = controllerMock.buscarPorUsername(username);
		
		//THEN:
		assertAll(  ()-> assertEquals(HttpStatus.OK, re.getStatusCode(), "Resultado"),
					()-> assertEquals(username, re.getBody().getUsername())				
				);		
	}
	
	@Test
	@Order (6)
	void buscarPorUsernameTestException() throws Exception {
		//GIVEN:
		
		//WHEN:
		doThrow (new RuntimeException()).when(serviceMock).buscarPorUsername(username);
		ResponseEntity<Empleado> re = controllerMock.buscarPorUsername(username);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excecpción");
	}
	

	@Test
	@Order (7)
	void eliminarTest() throws Exception {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 0);
		
		//WHEN:
		ResponseEntity<String> re = controllerMock.eliminar(e);
		
		//THEN:	
		assertEquals(HttpStatus.OK, re.getStatusCode(), "Código de retorno correcto");		
	}
	
	@Test
	@Order (8)
	void eliminarTestExcepcion() throws Exception {
		//GIVEN:
		Empleado e = new Empleado(username, "nombre", "ape", 0);
		
		//WHEN:
		doThrow (new RuntimeException()).when( serviceMock ).eliminar(e);
		ResponseEntity<String> re = controllerMock.eliminar(e);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excecpción");
	}
	
	@Test
	@Order (9)
	void buscarTodos () throws Exception {
		//GIVEN:
		Empleado e1 = new Empleado(username, "nombre", "ape", 0);
		Empleado e2 = new Empleado(username+"2", "nombre", "ape", 1500);
		List<Empleado> le = Arrays.asList(e1, e2);
		
		//WHEN:
		when( serviceMock.buscarTodos() ).thenReturn(le);
		ResponseEntity<List<Empleado>> re = controllerMock.buscarTodos();
		
		//THEN:
		assertEquals(HttpStatus.OK, re.getStatusCode(), "Código de retorno correcto");
		assertEquals(2, re.getBody().size(), "Devuelve los dos empleados existentes");		
	}
	
	@Test
	@Order (10)
	void buscarTodosExcepcion () throws Exception {
		//GIVEN:
		
		//WHEN:
		doThrow(new RuntimeException()).when( serviceMock ).buscarTodos();
		ResponseEntity<List<Empleado>> re = controllerMock.buscarTodos();
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion" );			
	}
	
	@Test
	@Order (11)
	void buscarPorNombreTest () throws Exception {
		//GIVEN:
		String nombre = "Nombre Empleado";
		Empleado e = new Empleado(username, nombre, "ape", 0);
		List<Empleado> le = Arrays.asList(e);
		
		//WHEN:
		when (serviceMock.buscarPorNombre(nombre)).thenReturn(le);
		ResponseEntity<List<Empleado>> re = controllerMock.buscarPorNombre(nombre);
		
		//THEN:	
		assertEquals(HttpStatus.OK, re.getStatusCode(), "Código de retorno correcto");
		assertEquals(1, re.getBody().size(), "Número empleados correcto");
		assertEquals(nombre, re.getBody().get(0).getNombre());
	}
	

	@Test
	@Order (12)
	void buscarPorNombreTestExcepcion () throws Exception {
		//GIVEN:
		
		//WHEN:
		doThrow(new RuntimeException()).when( serviceMock ).buscarPorNombre(ArgumentMatchers.anyString());
		ResponseEntity<List<Empleado>> re = controllerMock.buscarPorNombre(ArgumentMatchers.anyString());
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion" );		
	}	

}
