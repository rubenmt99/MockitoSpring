package everfuture.repository.entity;

import com.example.mockitospring.entity.Empleado;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("normal")
class EmpleadoTest {

	@Test
	void test() {
		//GIVEN:
		String username="testUsername";
		String nombre="Nombre1";
		String apellidos="Apellidos 1y 2";
		long salario=1800;
		
		//WHEN:
		Empleado e1 = new Empleado();
		e1.setUsername(username);
		e1.setNombre(nombre);
		e1.setApellidos(apellidos);
		e1.setSalario(salario);
		Empleado e2 = new Empleado (username, "n", "a", 1000);
		
		//THEN:
		assertAll(  ()-> assertEquals(username, e1.getUsername(), "Mismo username"),
					()-> assertEquals(nombre, e1.getNombre(), "Mismo nombre"),
					()-> assertEquals(apellidos, e1.getApellidos(), "Mismos apellidos"),
					()-> assertEquals(salario, e1.getSalario(), "Mismo salario"),
					()-> assertEquals(e1, e1, "Mismo objeto"),
					()-> assertNotEquals(e1, nombre, "Distinto tipo de objeto"),
					()-> assertEquals(e1, e2, "Objetos distintos con mismo username"),
					()-> assertEquals(Objects.hash(e1.getUsername()), e1.hashCode())					
				);
	}

}
