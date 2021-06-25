package de.freerider.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.freerider.model.Customer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerRepositoryTest {

	@Autowired
	private CrudRepository<Customer,String> customerRepository;

	// two sample customers
	private Customer mats;
	private Customer thomas;


	/**
	 * Set up test, runs before EVERY test execution
	 */
	@BeforeEach
	public void beforeEach() {
		//
		mats = new Customer( "Mats", "Hummels", "mh@weltmeister-2014.dfb.de" );
		thomas = new Customer( "Thomas", "Mueller", "th@weltmeister-2014.dfb.de" );

		customerRepository.deleteAll();		// clear repository
		assertEquals( customerRepository.count(), 0 );
	}

	@Test
	void testSaveCustomers() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(2, customerRepository.count());
	}

	@Test
	void testNullIdAssignment() {
		customerRepository.save(mats);

		Iterator<Customer> iter = customerRepository.findAll().iterator();
		String newId = iter.next().getId();

		assertTrue(newId.length() > 0);
	}

	@Test
	void testKeepId() {
		mats.setId("matsID");
		customerRepository.save(mats);
		Optional<Customer> savedCustomer = customerRepository.findById("matsID");

		assertTrue(savedCustomer.isPresent());
	}

	@Test
	void testSaveNull() {
		customerRepository.save(null);
		assertEquals(0, customerRepository.count());
	}

	@Test
	void testSaveTwice() {
		customerRepository.save(mats);
		customerRepository.save(mats);
		assertEquals(1, customerRepository.count());
	}

	@Test
	void testSaveWithSameId() {
		mats.setId("ID123");
		thomas.setId("ID123");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(1, customerRepository.count());
	}

	// SAVE ALL TESTS

	@Test
	void testSaveMultiple() {
		LinkedList listOfCustomers = new LinkedList();
		listOfCustomers.add(mats);
		listOfCustomers.add(thomas);

		customerRepository.saveAll(listOfCustomers);

		assertEquals(2, customerRepository.count());
	}

	@Test
	void testSaveOne() {
		LinkedList listOfCustomers = new LinkedList();
		listOfCustomers.add(thomas);

		customerRepository.saveAll(listOfCustomers);

		assertEquals(1, customerRepository.count());
	}

	@Test
	void testSaveNone() {
		LinkedList listOfCustomers = new LinkedList();

		customerRepository.saveAll(listOfCustomers);

		assertEquals(0, customerRepository.count());
	}


	@Test
	void testSaveAllNull() {
		customerRepository.saveAll(null);

		assertEquals(0, customerRepository.count());
	}

	// FIND BY ID TESTS

	@Test
	void testFindOne() {
		Customer saved = customerRepository.save(mats);
		assertTrue(customerRepository.findById(saved.getId()).isPresent());
	}

	@Test
	void testMissOne() {
		assertFalse(customerRepository.findById("NotInRepository").isPresent());
	}

	@Test
	void testFindNull() {
		assertFalse(customerRepository.findById(null).isPresent());
	}

	// FIND ALL TEST

	@Test
	void testFindAll() {
		Customer savedMats = customerRepository.save(mats);
		Customer savedThomas = customerRepository.save(thomas);

		LinkedList list = new LinkedList();
		list.add(savedMats);
		list.add(savedThomas);

//		assertEquals(2, Iterator.l customerRepository.findAllById(list));

	}

//	@Test
//	void testFive() {
//
//		Customer max = new Customer("Mustermann", "Max", "max@example.com");
//		Customer margret = new Customer("Mustermann", "Margret", "margret@example.com");
//		Customer michael = new Customer("Mustermann", "Michael", "michael@example.com");
//		Customer monika = new Customer("Mustermann", "Monika", "monika@example.com");
//		Customer michel = new Customer("Mustermann", "Michel", "michel@example.com");
//
//		repo.save(max);
//		repo.save(margret);
//		repo.save(michael);
//		repo.save(monika);
//		repo.save(michel);
//
//		assertEquals(5, repo.count());
//
//		// try saving using save all
//		LinkedList<Customer> customerLinkedList= new LinkedList<Customer>();
//		customerLinkedList.add(max);
//		repo.saveAll(customerLinkedList);
//		assertEquals(5, repo.count());
//
//
//		// try finding max
//		Optional<Customer> foundMax = repo.findById(max.getId());
//		assertEquals("Max", foundMax.get().getFirstName());
//
//		assertTrue(repo.existsById(max.getId()));
//
//		System.out.println("Sind alle Namen vorhanden?:");
//		repo.findAll().forEach(customer -> System.out.println(customer.getFirstName()));
//
//
//		String[] findAllByIdName = new String[]{max.getId()};
//		Iterable<Customer> findAllByIdIter = repo.findAllById(Arrays.asList(findAllByIdName));
//		System.out.println("Ist hier Max?:");
//		findAllByIdIter.forEach(customer -> System.out.println(customer.getFirstName()));
//
//		// try saving same people again
//		repo.save(max);
//		repo.save(margret);
//		repo.save(michael);
//		repo.save(monika);
//		repo.save(michel);
//
//		assertEquals(5, repo.count());
//
//	}
//
//
//	@Test
//	void deleteById() {
//		Customer max = new Customer("Mustermann", "Max", "max@example.com");
//		repo.save(max);
//		assertEquals(1, repo.count());
//		repo.deleteById(max.getId());
//		assertEquals(0, repo.count());
//	}
//
//	@Test
//	void delete() {
//		Customer max = new Customer("Mustermann", "Max", "max@example.com");
//		repo.save(max);
//		assertEquals(1, repo.count());
//		repo.delete(max);
//		assertEquals(0, repo.count());
//	}
//
//	@Test
//	void deleteAllById() {
//		Customer max = new Customer("Mustermann", "Max", "max@example.com");
//		Customer margret = new Customer("Mustermann", "Margret", "margret@example.com");
//		repo.save(margret);
//		repo.save(max);
//		assertEquals(2, repo.count());
//
//		String[] names = new String[]{max.getId(), margret.getId()};
//		repo.deleteAllById(Arrays.asList(names));
//		assertEquals(0, repo.count());
//	}
//
//	@Test
//	void deleteAll() {
//		Customer max = new Customer("Mustermann", "Max", "max@example.com");
//		Customer margret = new Customer("Mustermann", "Margret", "margret@example.com");
//		repo.save(margret);
//		repo.save(max);
//		assertEquals(2, repo.count());
//
//		repo.deleteAll();
//		assertEquals(0, repo.count());
//	}
}