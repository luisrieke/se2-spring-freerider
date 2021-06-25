package de.freerider.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

// https://howtodoinjava.com/junit5/junit-5-assertions-examples
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
public class CustomerRepositoryTest_EmptyRepositoryTestCase {

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
	 * Test all methods on empty repository:
	 *  - save(), saveAll() - not tested here
	 *  - findById(id), existsById(id), findAll() - tested with id: ""
	 *  - findAllById(Iterable{ids}) - tested with: {}, {""}
	 *  - count()
	 *  - deleteById(id) - tested with id: ""
	 *  - delete(entity) - tested with entity: mats
	 *  - deleteAllById(Iterable{ids}) - tested with: {}, {""}
	 *  - deleteAll(Iterable{entity}) - tested with: {}, {mats}
	 *  - deleteAll()
	 */
	@Test
	@Order(10)
	void emptyRepositoryTest_findById() {
		assertTrue(
			customerRepository.findById( "" ).isEmpty()
		);
	}

	@Test
	@Order(20)
	void emptyRepositoryTest_existsById() {
		assertFalse(
			customerRepository.existsById( "" )
		);
	}

	@Test
	@Order(30)
	void emptyRepositoryTest_findAll() {
		assertFalse(		// empty repository should have no next value
			customerRepository.findAll().iterator().hasNext()
		);
	}

	@Test
	@Order(40)
	void emptyRepositoryTest_findAllById() {
		List<String>ids = new ArrayList<String>();
		assertFalse(		// empty repository should have no next value
			customerRepository.findAllById( ids ).iterator().hasNext()
		);

		ids.add( "" );
		assertFalse(		// empty repository should have no next value
			customerRepository.findAllById( ids ).iterator().hasNext()
		);
	}

	@Test
	@Order(50)
	void emptyRepositoryTest_count() {
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	@Order(60)
	void emptyRepositoryTest_deleteById() {
		customerRepository.deleteById( "" );
		assertEquals( customerRepository.count(), 0 );

		customerRepository.deleteById( "ABCDEF" );
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	@Order(70)
	void emptyRepositoryTest_delete() {
		mats.setId( "" );		// id: null throws IllegalArgumentException
		customerRepository.delete( mats );
		assertEquals( customerRepository.count(), 0 );

		mats.setId( null );		// id: null throws IllegalArgumentException
		assertThrows( IllegalArgumentException.class, () -> {
			// throws IAE according to spec because mats.id is null
			customerRepository.delete( mats );
		});
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	@Order(80)
	void emptyRepositoryTest_deleteAllById() {
		List<String>ids = new ArrayList<String>();
		customerRepository.deleteAllById( ids );
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	@Order(90)
	void emptyRepositoryTest_deleteAllEntities() {
		List<Customer>entities = new ArrayList<Customer>();
		customerRepository.deleteAll( entities );
		assertEquals( customerRepository.count(), 0 );

		mats.setId( "" );		// id: null throws IllegalArgumentException
		entities.add( mats );
		customerRepository.deleteAll( entities );
		assertEquals( customerRepository.count(), 0 );

		entities.add( thomas );		// id: null
		assertThrows( IllegalArgumentException.class, () -> {
			customerRepository.deleteAll( entities );
		});
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	@Order(100)
	void emptyRepositoryTest_deleteAll() {
		customerRepository.deleteAll();
		assertEquals( customerRepository.count(), 0 );
	}

}
