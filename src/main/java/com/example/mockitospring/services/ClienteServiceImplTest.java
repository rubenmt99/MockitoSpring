package com.example.mockitospring.services;

import com.example.mockitospring.entity.Cliente;
import com.example.mockitospring.repository.ClienteRepoJPA;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("bbdd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClienteServiceImplTest {
	public static final String DNITEST = "000001T";
	public static final String DNITEST2 = "00002A";
	public static final String DNITEST3 = "00003C";
	
	@Autowired
	ClienteRepoJPA dao;
	
	@Autowired
	ClienteService service;

	@Test
	@Order(1)
	void testInsertar() {
		//GIVEN:
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertFalse( oc.isPresent(), "No existe un cliente con tal DNI");
		
		//WHEN:
		Cliente c = new Cliente (DNITEST, "nomb", "apell", "direcc");
		c = service.insertar(c);
		
		//THEN:
		oc = dao.findById(DNITEST);
		assertTrue( oc.isPresent(), "Si existe el cliente buscado por DNI");
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c);		
	}
	

	@Test
	@Order(2)
	void testModificar() {
		//GIVEN:
		String nuevoNombre="NuevoNombre";
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		c = dao.save(c);
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertTrue(oc.isPresent(), "Si existe el cliente");
		
		//WHEN:
		c.setNombre(nuevoNombre);
		c = service.modificar(c);
		
		//THEN:
		oc = dao.findById(DNITEST);
		assertTrue(oc.isPresent(), "Existe el cliente");
		assertEquals(nuevoNombre, oc.get().getNombre(), "Debe tener mismo nombre");
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c);
	}

	@Test
	@Order(3)
	void testEliminar() throws Exception {
		//GIVEN:
		Cliente c = new Cliente(DNITEST, "n", "a", "d");
		c = dao.save(c);
		Optional<Cliente> oc = dao.findById(DNITEST);
		assertTrue(oc.isPresent(), "Si existe el cliente");
		
		//WHEN:
		service.eliminar(c);
		
		//THEN:
		oc = dao.findById(DNITEST);
		assertFalse(oc.isPresent(), "Ya no existe el cliente");		
	}

	@Test
	@Order(4)
	void testBuscarPorDNI() throws Exception {
		//GIVEN:
		Cliente c1 = new Cliente(DNITEST, "Ana", "a", "dttt");
		c1 = dao.save(c1);
		Cliente c2 = new Cliente(DNITEST2, "Lucas", "abbb", "daaa");
		c2 = dao.save(c2);
		
		//WHEN:
		Cliente resultado = service.buscarPorDNI(DNITEST);
		Cliente resultado2 = service.buscarPorDNI("");
		
		//THEN:
		assertNotNull(resultado, "Encuentra cliente en BBDD");
		assertEquals(DNITEST, resultado.getDni(), "Tiene el DNI por el que hemos buscado");
		assertNull(resultado2);
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c1);
		dao.delete(c2);		
	}

	@Test
	@Order(5)
	void testBuscarTodos() throws Exception {
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
		List<Cliente> resultado = service.buscarTodos();
		
		//THEN:
		assertEquals(3, resultado.size(), "Deben existir 3 clientes en BBDD");
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c1);
		dao.delete(c2);
		dao.delete(c3);		
	}

	@Test
	@Order(6)
	void testBuscarPorNombre() throws Exception {
		//GIVEN:
		String ana ="Ana";
		Cliente c1 = new Cliente(DNITEST, ana, "a", "dttt");
		c1 = dao.save(c1);
		Cliente c2 = new Cliente(DNITEST2, "Lucas", "abbb", "daaa");
		c2 = dao.save(c2);
		Cliente c3 = new Cliente(DNITEST3, ana, "abcccc", "dsss");
		c3 = dao.save(c3);
		List<Cliente> clientesDDBB = dao.findAll();
		assertEquals(3,  clientesDDBB.size(), "Hay tres clientes en BBDD");
		
		//WHEN:
		List<Cliente> resultado = service.buscarPorNombre(ana);
		
		//THEN:
		assertEquals(2, resultado.size(), "Solo existen dos empleados con nombre 'Ana'");
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c1);
		dao.delete(c2);
		dao.delete(c3);		
	}

}
