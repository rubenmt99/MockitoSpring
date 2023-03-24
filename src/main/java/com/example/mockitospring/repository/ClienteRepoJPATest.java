package com.example.mockitospring.repository;

import com.example.mockitospring.entity.Cliente;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//dependencia starter-tests
@Tag("bbdd_h2")
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class ClienteRepoJPATest {
	
	@Autowired
	ClienteRepoJPA dao;	

	@Test
	void test() {
		//GIVEN:
		String dnitest="00001T";
		Optional<Cliente> oc = dao.findById(dnitest);
		assertFalse( oc.isPresent() );
		
		//WHEN:
		Cliente c = new Cliente (dnitest, "nomb", "apell", "direcc");
		c = dao.save(c);
		
		//THEN:
		Optional<Cliente> oc2 = dao.findById(dnitest);
		assertTrue( oc2.isPresent() );
		
		//Dejarlo todo en el estado inicial:
		dao.delete(c);		
	}

}
