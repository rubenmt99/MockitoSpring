package everfuture.repository.entity;

import com.example.mockitospring.entity.Cliente;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("normal")
class ClienteTest {

	@Test
	void test() {
		//GIVEN:
		String dni="00000001-A";
		String nombre1="Nom1";
		String apellido1="Ape1";
		String direccion1="Dir1";
		
		//WHEN:
		Cliente c1 = new Cliente();
		c1.setDni(dni);
		c1.setNombre(nombre1);
		c1.setApellidos(apellido1);
		c1.setDireccion(direccion1);
		Cliente c2 = new Cliente(dni, "Nom2", "Ape2", "Dir2");		
		
		//THEN:
		assertAll(	()-> assertEquals(dni, c1.getDni(), "DNI iguales"),
					()-> assertEquals(nombre1, c1.getNombre(), "Mismo nombre"),
					()-> assertEquals(apellido1, c1.getApellidos(), "Mismos apellidos"),
					()-> assertEquals(direccion1, c1.getDireccion(), "Misma dirección"),
					()-> assertEquals(c1, c2, "Mismo identificador"),
					()-> assertEquals(c1, c1, "Mismo objeto"),
					()-> assertNotEquals(dni, c2, "Distinto tipo de objeto"),
					()-> assertEquals(Objects.hash(dni), c1.hashCode(), "Mismo hasCode")
				);
	}

}
