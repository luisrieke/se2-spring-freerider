package de.freerider.repository;

import de.freerider.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerRepositoryTest {

	// --- Attribute ---

	@Autowired
	private CrudRepository<Customer, String> customerRepository;

	private Customer mats;
	private Customer thomas;

	// --- Test Aufbau ---

	@BeforeEach
	public void CustomersSetup() {
		mats = new Customer("Matsen", "Mats", "mmatsen@email.de");
		thomas = new Customer("Thomasen", "Thomas", "tthomasen@email.de");
	}

	@AfterEach
	public void ClearRepository() {
		customerRepository.deleteAll();
	}

	// --- Tests ---

	// Konstruktor - Tests

	@Test
	void testLeeresRepo() {
		long customerCount = customerRepository.count();
		assertEquals(customerCount, 0);
		assertNotNull(customerRepository);
	}

	// save() – Tests

	@Test
	void testCustomerSpeichern() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(2, customerRepository.count());
	}

	@Test
	void testIdZuweisung() {
		customerRepository.save(mats);
		Iterator<Customer> iter = customerRepository.findAll().iterator();
		String newId = iter.next().getId();
		assertTrue(newId.length() > 0);
	}

	@Test
	void testIdZuweisenUndPruefen() {
		mats.setId("012345");
		customerRepository.save(mats);
		Optional<Customer> gespeichert = customerRepository.findById("012345");
		assertTrue(gespeichert.isPresent());
	}

	@Test
	void testCustomerMitNullId() {
		mats.setId(null);
		customerRepository.save(mats);
		String matsID = mats.getId();
		Optional<Customer> gespeichert = customerRepository.findById(matsID);
		assertTrue(gespeichert.isPresent());
	}

	@Test
	void testCustomerMitNichtNullId() {
		mats.setId("012345");
		customerRepository.save(mats);
		Optional<Customer> gespeichert = customerRepository.findById("012345");
		assertTrue(gespeichert.isPresent());
		assertEquals("012345", mats.getId());
	}

	@Test
	void testNullSpeichern() {
		customerRepository.save(null);
		assertEquals(0, customerRepository.count());
	}

	@Test
	void testZweimalSpeichern() {
		customerRepository.save(mats);
		customerRepository.save(mats);
		assertEquals(1, customerRepository.count());
	}

	@Test
	void testMitSelberIdSpeichern() {
		mats.setId("67789");
		thomas.setId("67789");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(1, customerRepository.count());
	}

	// saveAll() – Tests

	@Test
	void testEinmalSpeichern() {
		LinkedList listeDerCustomer = new LinkedList();
		listeDerCustomer.add(thomas);
		customerRepository.saveAll(listeDerCustomer);
		assertEquals(1, customerRepository.count());
	}

	@Test
	void testMehrmalsSpeichern() {
		LinkedList listeDerCustomer = new LinkedList();
		listeDerCustomer.add(mats);
		listeDerCustomer.add(thomas);
		customerRepository.saveAll(listeDerCustomer);
		assertEquals(2, customerRepository.count());
	}

	@Test
	void testLeereListe() {
		LinkedList listeDerCustomer = new LinkedList();
		customerRepository.saveAll(listeDerCustomer);
		assertEquals(0, customerRepository.count());
	}


	@Test
	void testSaveAllMitNullListe() {
		customerRepository.saveAll(null);
		assertEquals(0, customerRepository.count());
	}

	// findById() – Tests

	@Test
	void testCustomerFindenById() {
		Customer gespeichert = customerRepository.save(mats);
		assertTrue(customerRepository.findById(gespeichert.getId()).isPresent());
	}

	@Test
	void testSucheNachNull() {
		assertFalse(customerRepository.findById(null).isPresent());
	}

	@Test
	void testFehlenderEintrag() {
		assertFalse(customerRepository.findById("Fehler").isPresent());
	}

	// findAll() – Tests

	@Test
	void testLeeresRepoAlleFinden() {
		Iterable<Customer> listeDerCustomer = customerRepository.findAll();
		LinkedList<Customer> leereListe = new LinkedList<Customer>();
		assertIterableEquals(listeDerCustomer, leereListe);
	}

	@Test
	void testAlleFinden() {
		LinkedList<Customer> listeDerCustomer = new LinkedList<Customer>();
		listeDerCustomer.add(thomas);
		listeDerCustomer.add(mats);
		customerRepository.saveAll(listeDerCustomer);
		Collection<Customer> listeDerGefundenenCustomer = (Collection<Customer>) customerRepository.findAll();
		assertTrue(listeDerGefundenenCustomer.containsAll(listeDerCustomer));
	}

	// findAllById() – Tests

	@Test
	void testAlleMitIdFinden() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		List<String> idListe = new ArrayList<String>();
		idListe.add(mats.getId());
		idListe.add(thomas.getId());
		Iterable<Customer> gefunden = customerRepository.findAllById(idListe);
		List<Customer> listeDerCustomer = new ArrayList<Customer>();
		listeDerCustomer.add(mats);
		listeDerCustomer.add(thomas);
		assertEquals(gefunden, listeDerCustomer);
	}

	@Test
	void testFindAllBySucheNachFalscherId() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		ArrayList<String> idList = new ArrayList<String>();
		idList.add("ABCD");
		idList.add("EFGH");
		idList.add("Fehler");
		List<Customer> listeDerCustomer = new ArrayList<Customer>();
		listeDerCustomer.add(mats);
		listeDerCustomer.add(thomas);
		Iterable<Customer> foundById = customerRepository.findAllById(idList);
		assertEquals(foundById, listeDerCustomer);
	}

	// count() – Tests

	@Test
	void testCountVollesRepository() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(customerRepository.count(), 2);
	}

	@Test
	void testCountLeeresRepository() {
		CrudRepository<Customer, String> customerRepository = new CustomerRepository();
		assertEquals(customerRepository.count(), 0);
	}

	// deleteById() – Tests

	@Test
	void testDeleteMitId() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		customerRepository.deleteById("EFGH");
		assertEquals(customerRepository.count(), customerVorDelete - 1);
		assertFalse(customerRepository.existsById("EFGH"));
	}

	@Test
	void testDeleteMitFalscherId() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		customerRepository.deleteById("Fehler");
		assertEquals(customerRepository.count(), customerVorDelete);
	}

	// delete () – Tests

	@Test
	void testDelete() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		customerRepository.delete(mats);
		assertEquals(customerRepository.count(), customerVorDelete - 1);
		assertFalse(customerRepository.existsById("ABCD"));
	}

	@Test
	void testDeleteFehlerhaft() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		long customerVorDelete = customerRepository.count();
		customerRepository.delete(thomas);
		assertEquals(customerRepository.count(), customerVorDelete);
		assertFalse(customerRepository.existsById("EFGH"));
	}

	@Test
	void testDeleteMitNull() {
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					customerRepository.delete(null);
				}
		);
	}

	// deleteAllById () – Tests

	@Test
	void testDeleteAllById() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		ArrayList<String> idListe = new ArrayList<String>();
		idListe.add("ABCD");
		long listenGroesse = idListe.size();
		customerRepository.deleteAllById(idListe);
		assertEquals(customerRepository.count(), customerVorDelete - listenGroesse);
		assertFalse(customerRepository.existsById("ABCD"));
		assertTrue(customerRepository.existsById("EFGH"));
	}

	@Test
	void testDeleteAllByIdFehlerhaft() {
		mats.setId("ABCD");
		thomas.setId("EFGH");
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		LinkedList<String> idListe = new LinkedList<String>();
		idListe.add("ABCD");
		idListe.add("Fehler");
		customerRepository.deleteAllById(idListe);
		assertEquals(customerRepository.count(), customerVorDelete - 1);
		assertFalse(customerRepository.existsById("ABCD"));
		assertTrue(customerRepository.existsById("EFGH"));
	}

	// deleteAll ( Iterable<> entities ) – Tests

	@Test
	void testDeleteAllEntities() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		long customerVorDelete = customerRepository.count();
		ArrayList<Customer> listeDerCustomer = new ArrayList<Customer>();
		listeDerCustomer.add(mats);
		long listenGroesse = listeDerCustomer.size();
		customerRepository.deleteAll(listeDerCustomer);
		assertEquals(customerRepository.count(), customerVorDelete - listenGroesse);
	}

	@Test
	void testDeleteAllEntitiesFehlerhaft() {
		customerRepository.save(mats);
		long customerVorDelete = customerRepository.count();
		ArrayList<Customer> listeDerCustomer = new ArrayList<Customer>();
		listeDerCustomer.add(thomas);
		customerRepository.deleteAll(listeDerCustomer);
		assertEquals(customerRepository.count(), customerVorDelete);
	}

	// deleteAll () – Tests

	@Test
	void testDeleteAll() {
		customerRepository.save(mats);
		customerRepository.save(thomas);
		assertEquals(customerRepository.count(), 2);
		customerRepository.deleteAll();
		assertEquals(customerRepository.count(), 0);
	}

	@Test
	void testDeleteAllWennBereitsLeer() {
		long customerVorDelete = customerRepository.count();
		customerRepository.deleteAll();
		long customerNachDelete = customerRepository.count();
		assertEquals(customerVorDelete, customerNachDelete);
		assertEquals(customerNachDelete, 0);
	}

}