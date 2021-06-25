package de.freerider.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
public class CustomerRepositoryTest_SaveTestCase {

	@Autowired
	CrudRepository<Customer,String> customerRepository;

	// two sample customers
	private Customer mats;
	private Customer thomas;

	private String id1 = "ABCDEF";
	private String id2 = "GHIJKL";


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
	 * Test save() methods on repository:
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
	void saveTest_save() {
		//
		assertNull( thomas.getId() );		// id is initially null
		//
		Customer saved1 = customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		assertEquals( saved1, thomas );
		String id1 = thomas.getId();
		assertNotNull( id1 );				// id now assigned by save()
		//
		assertEquals( customerRepository.count(), 1 );
	}

	@Test
	@Order(20)
	void saveTest_saveWithId() {
		//
		assertNull( thomas.getId() );		// id is initially null
		thomas.setId( id1 );
		assertEquals( id1, thomas.getId() );
		//
		thomas.setId( id2 );					// id, when set before, is not set (unchanged)
		assertEquals( id1, thomas.getId() );	// id still the prior one
		//
		mats.setId( id2 );
		//
		assertEquals( customerRepository.count(), 0 );			// repository empty
		//
		Customer saved1 = customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		assertEquals( saved1, thomas );
		assertEquals( id1, thomas.getId() );	// id unchanged
		//
		Customer saved2 = customerRepository.save( mats );
		assertEquals( customerRepository.count(), 2 );
		assertEquals( saved2, mats );
		assertEquals( id2, mats.getId() );		// id unchanged
	}

	@Test
	@Order(30)
	void saveTest_saveTwice() {
		//
		assertNull( thomas.getId() );			// id is initially null
		thomas.setId( id1 );
		assertEquals( id1, thomas.getId() );
		//
		Customer saved1 = customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		assertEquals( saved1, thomas );
		assertEquals( id1, thomas.getId() );	// id unchanged
		//
		// object saved second time, must only be stored once
		Customer saved2 = customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		assertEquals( saved2, thomas );
		assertEquals( saved1, saved2 );
		assertEquals( id1, thomas.getId() );	// id unchanged
	}

	@Test
	@Order(40)
	void saveTest_saveObjectsWithSameId() {
		//
		// preconditions:
		assertNotEquals( thomas, mats );		// two objects
		assertNull( thomas.getId() );			// id is initially null
		assertNull( mats.getId() );
		thomas.setId( id1 );
		assertEquals( id1, thomas.getId() );
		mats.setId( id1 );						// different object with same id
		assertEquals( id1, mats.getId() );
		//
		// test:
		Customer saved1 = customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		assertEquals( saved1, thomas );
		assertEquals( id1, thomas.getId() );	// id unchanged
		customerRepository.findById( id1 ).ifPresentOrElse(
				saved4 -> assertEquals( saved4, thomas ), () -> fail( "Object not found" ) );
		//
		// second object with same id saved, must OVERWRITE previous object
		// and return previously saved object
		Customer saved2 = customerRepository.save( mats );
		assertEquals( customerRepository.count(), 1 );		// count still 1
		assertEquals( saved2, thomas );						// returns previously saved object
		Customer saved3 = customerRepository.findAll().iterator().next();
		assertNotNull( saved3 );
		assertEquals( saved3, mats );						// new object replaced prior object
		//
		customerRepository.findById( id1 ).ifPresentOrElse(
				saved4 -> assertEquals( saved4, mats ), () -> fail( "Object not found" ) );
		//
		// same test as before, but in conventional style
		Optional<Customer> saved4Opt = customerRepository.findById( id1 );
		Customer saved4 = saved4Opt.get();
		assertNotNull( saved4 );
		assertEquals( saved4, mats );						// new object replaced prior object
		assertNotEquals( saved4, thomas );
	}

	@Test
	@Order(50)
	void saveTest_saveDeleteSave() {
		//
		customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		//
		customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
		//
		customerRepository.delete( thomas );
		assertEquals( customerRepository.count(), 0 );
		//
		customerRepository.save( thomas );
		assertEquals( customerRepository.count(), 1 );
	}

}
