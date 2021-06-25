package de.freerider.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

// https://howtodoinjava.com/junit5/junit-5-assertions-examples
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import de.freerider.model.Customer;


/**
 * CustomerRepository test class that refers to an implementation of the
 * CrudRepository<T, ID> interface:
 *  - https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/repository/CrudRepository.java
 * 
 * Behavior of methods is tested as specified in CrudRepository<T, ID> interface.
 * 
 * @author svgr2
 *
 */
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class CustomerRepositoryTest_NullArgumentTestCase {

	@Autowired
	CrudRepository<Customer,String> customerRepository;

	// two sample customers
	private Customer mats;
	private Customer thomas;


	/**
	 * Set up test, runs before EVERY test execution
	 */
	@BeforeEach
	public void setUpEach() {
		//
		mats = new Customer( "Mats", "Hummels", "mh@weltmeister-2014.dfb.de" );
		thomas = new Customer( "Thomas", "Mueller", "th@weltmeister-2014.dfb.de" );
		//
		customerRepository.deleteAll();		// clear repository
		assertEquals( customerRepository.count(), 0 );
	}



	/**
	 * Test all methods with null-arguments; according the spec (CrudRepository<T, ID>),
	 * method invocations with null arguments must throw IllegalArgumentException.
	 *  - save(null) -> must throw IllegalArgumentException
	 *  - saveAll(null) -> must throw IllegalArgumentException
	 *  - findById(null) -> must throw IllegalArgumentException
	 *  - existsById(null) -> must throw IllegalArgumentException
	 *  - findAllById(null) -> must throw IllegalArgumentException
	 *  - deleteById(null) -> must throw IllegalArgumentException
	 *  - delete(null) -> must throw IllegalArgumentException
	 *  - deleteAllById(null) -> must throw IllegalArgumentException
	 *  - deleteAll(null) -> must throw IllegalArgumentException
	 */
	@Test
	@Order(10)
	void nullArgumentTest_save() {
		assertThrows( IllegalArgumentException.class, () -> {
			Customer saved = customerRepository.save( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(20)
	void nullArgumentTest_saveAll() {
		assertThrows( IllegalArgumentException.class, () -> {
			Iterable<Customer> savedN = customerRepository.saveAll( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(30)
	void nullArgumentTest_findById() {
		assertThrows( IllegalArgumentException.class, () -> {
			Optional<Customer> foundOne = customerRepository.findById( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(40)
	void nullArgumentTest_existsById() {
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.existsById( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(50)
	void nullArgumentTest_findAllById() {
		assertThrows( IllegalArgumentException.class, () -> {
			Iterable<Customer> foundN = customerRepository.findAllById( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(60)
	void nullArgumentTest_deleteById() {
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.deleteById( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(70)
	void nullArgumentTest_delete() {
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.delete( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(80)
	void nullArgumentTest_deleteAllById() {
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.deleteAllById( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

	@Test
	@Order(90)
	void nullArgumentTest_deleteAll() {
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.deleteAll( null );
			fail( "IllegalArgumentException not thrown" );
		});
	}

}
