package com.example.mockitospring.controllers;


import com.example.mockitospring.entity.Cliente;
import com.example.mockitospring.repository.ClienteRepoJPA;
import com.example.mockitospring.services.ClienteService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@Tag("bbdd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteRestControllerTest {
	public static final String DNITEST = "000001T";
	public static final String DNITEST2 = "00002A";
	public static final String DNITEST3 = "00003C";
	
	@Autowired
	ClienteRepoJPA dao;
	@Autowired
	ClienteRestController controller;
	
	@InjectMocks
	ClienteRestController controllerMock;
	@Mock
	ClienteService serviceMock;

	@Test
	@Order(1)
	void insertarClienteTest() {
		//GIVEN:
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertFalse(oc.isPresent(), "No existe tal cliente");
		
		//WHEN:
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		ResponseEntity<Cliente> rec = controller.insertarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.CREATED, rec.getStatusCode(), "Comprobación de código de error del servicio REST" );
		assertEquals (DNITEST, rec.getBody().getDni(), "Mismo DNI");
		Optional<Cliente> oc2 = dao.findById(DNITEST);
		assertTrue(oc2.isPresent(), "El cliente sí está ya en BBDD");
		
		//Dejarlo todo en el estado inicial:
		dao.delete( oc2.get() );		
	}
	
	@Test
	@Order(2)
	void insertarClienteTestException() {
		//GIVEN:
		Cliente c = new Cliente(DNITEST+"0123456789", "n", "a", "d");
		
		//WHEN:
		ResponseEntity<Cliente> rec = controller.insertarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, rec.getStatusCode(), "Error controlado");
		assertNull (rec.getBody().getDni());		
	}
	
	
	@Test
	@Order(3)
	void modificarClienteTest() {
		//GIVEN:
		String nuevoNombre="NuevoNombre";
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		c = dao.save(c);
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertTrue(oc.isPresent(), "Si existe el cliente");
		
		//WHEN:
		c.setNombre(nuevoNombre);
		ResponseEntity<Cliente> rec = controller.modificarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.NO_CONTENT, rec.getStatusCode(), "Comprobación de código de error del servicio REST" );
		assertEquals (DNITEST, rec.getBody().getDni(), "Se devuelve el cliente correcto");
		assertEquals(nuevoNombre, rec.getBody().getNombre(), "El nombre ha cambiado");
		
		Optional<Cliente> oc2 = dao.findById(DNITEST);
		assertTrue(oc2.isPresent(), "El cliente debe estar ya insertado");
		assertEquals(nuevoNombre, oc2.get().getNombre(), "El nombre ha cambiado");
		
		//Dejarlo todo en el estado inicial:
		dao.delete( oc2.get() );		
	}
	
	@Test
	@Order(4)
	void modificarClienteTestException() {
		//GIVEN:
		Cliente c = new Cliente(DNITEST+"0123456700", "n", "a", "d");
		
		//WHEN:
		ResponseEntity<Cliente> rec = controller.modificarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, rec.getStatusCode(), "Error controlado");
		assertNull (rec.getBody().getDni());
	}
	
	@Test
	@Order(5)
	void eliminarClienteTest() {
		//GIVEN:
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		c = dao.save(c);
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertTrue(oc.isPresent(), "Si existe el cliente");
		
		//WHEN:
		ResponseEntity<String> rec = controller.eliminarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.OK, rec.getStatusCode(), "Código de retorno correcto");
		oc = dao.findById(DNITEST);
		assertFalse(oc.isPresent(), "Ya no existe el cliente en BBDD" );		
	}
	
	@Test
	@Order(6)
	void eliminarClienteTestException() throws Exception {
		//GIVEN:
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		
		//WHEN:
		doThrow (new Exception()).when (serviceMock).eliminar(c);
		ResponseEntity<String> rec = controllerMock.eliminarCliente(c);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, rec.getStatusCode(), "Error controlado");		
	}
	
	@Test
	@Order(7)
	void buscarPorDNITest() {
		//GIVEN:
		Cliente c1 = new Cliente(DNITEST, "Ana", "a", "dttt");
		c1 = dao.save(c1);
		Cliente c2 = new Cliente(DNITEST2, "Lucas", "abbb", "daaa");
		c2 = dao.save(c2);
		
		//WHEN:
		ResponseEntity<Cliente> rec = controller.buscarPorDNI(DNITEST);
		
		//THEN:
		assertEquals(HttpStatus.OK, rec.getStatusCode(), "Código de retorno correcto");
		assertNotNull( rec.getBody(), "El cliente existe");
		assertEquals(DNITEST, rec.getBody().getDni(), "Mismo DNI");
		
		//Dejarlo todo en el estado inicial:
		dao.delete( c1 );	
		dao.delete( c2 );
	}
	
	@Test
	@Order(8)
	void buscarPorDNITestException() throws Exception {
		//GIVEN:
		List<Cliente> lc = new ArrayList<Cliente> ();
		
		//WHEN:
		doThrow(new Exception()).when( serviceMock ).buscarPorDNI(DNITEST);
		ResponseEntity re = controllerMock.buscarPorDNI(DNITEST);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion" );		
	}
	
	@Test
	@Order(9)
	void buscarTodosTest() {
		//GIVEN:
		Cliente c1 = new Cliente(DNITEST, "Ana", "a", "dttt");
		c1 = dao.save(c1);
		Cliente c2 = new Cliente(DNITEST2, "Lucas", "abbb", "daaa");
		c2 = dao.save(c2);
		Cliente c3 = new Cliente(DNITEST3, "Tomás", "abcccc", "dsss");
		c3 = dao.save(c3);
		List<Cliente> clientesDDBB = dao.findAll();
		assertEquals(3,  clientesDDBB.size(), "Hay tres clientes en BBDD");
		
		//WHEN:
		ResponseEntity<List<Cliente>> rec = controller.buscarTodos();
		
		//THEN:
		assertEquals(HttpStatus.OK, rec.getStatusCode(), "Código de retorno correcto");
		assertEquals(3, rec.getBody().size(), "Número de clientes correcto");
		
		//Dejarlo todo en el estado inicial:
		dao.delete( c1 );	
		dao.delete( c2 );	
		dao.delete( c3 );
	}
	
	@Test
	@Order(10)
	void buscarTodosTestException() throws Exception  {
		//GIVEN:
		List<Cliente> lc = new ArrayList<Cliente> ();
		
		//WHEN:
		doThrow(new Exception()).when( serviceMock ).buscarTodos();
		ResponseEntity<List<Cliente>> rec = controllerMock.buscarTodos();
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, rec.getStatusCode(), "Salta excepcion" );		
	}
	
	@Test
	@Order(11)
	void buscarPorNombreTest() {
		//GIVEN:
		String ana = "Ana";
		Cliente c1 = new Cliente(DNITEST, ana, "a", "dttt");
		c1 = dao.save(c1);
		Cliente c2 = new Cliente(DNITEST2, "Lucas", "abbb", "daaa");
		c2 = dao.save(c2);
		Cliente c3 = new Cliente(DNITEST3, ana, "abcccc", "dsss");
		c3 = dao.save(c3);
		List<Cliente> clientesDDBB = dao.findAll();		
		assertEquals(3,  clientesDDBB.size());
		
		//WHEN:
		ResponseEntity<List<Cliente>> rec = controller.buscarPorNombre (ana);
		
		//THEN:
		assertEquals(HttpStatus.OK, rec.getStatusCode(), "Código de retorno correcto");
		assertEquals(2, rec.getBody().size(), "Sólo existen dos clientes que se llamen 'Ana'");
		
		//Dejarlo todo en el estado inicial:
		dao.delete( c1 );	
		dao.delete( c2 );	
		dao.delete( c3 );		
	}
	
	@Test
	@Order(12)
	void buscarPorNombreTestException () throws Exception  {
		//GIVEN:
		String ana = "Ana";
		List<Cliente> lc = new ArrayList<Cliente> ();
		
		//WHEN:
		doThrow(new Exception()).when( serviceMock ).buscarPorNombre(ana);
		ResponseEntity<List<Cliente>> re = controllerMock.buscarPorNombre(ana);
		
		//THEN:
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Salta excepcion" );		
	}

}
