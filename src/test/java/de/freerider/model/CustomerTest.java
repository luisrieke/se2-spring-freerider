package de.freerider.model;

import de.freerider.model.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

//@SpringBootTest
public class CustomerTest {
	private Customer mats;
	private Customer thomas;

	@BeforeEach
	public void createCustomers() {
		mats = new Customer();
		thomas = new Customer();
	}

	@Test
	public void testIdNull() {
		assertNull(mats.getId());
	}

	@Test
	public void testSetId() {
		mats.setId("ExampleId123");
		assertEquals("ExampleId123", mats.getId());
	}

	@Test
	public void testSetIdOnlyOnce() {
		mats.setId("ExampleId123");
		mats.setId("NotTheNewId");
		assertEquals("ExampleId123", mats.getId());
	}

	@Test
	public void testResetId() {
		mats.setId("ExampleId123");
		mats.setId(null);
		assertNull(mats.getId());
	}


	// Name Tests

	@Test
	public void testNamesInitial() {
		assertEquals("", mats.getFirstName());
		assertEquals("", mats.getLastName());
	}


	@Test
	public void testNamesSetNull() {
		mats.setLastName(null);
		mats.setFirstName(null);
		assertEquals("", mats.getLastName());
		assertEquals("", mats.getFirstName());
	}


	@Test
	public void testSetNames() {
		mats.setLastName("NichtMatsNachname");
		mats.setFirstName("NichtMats");
		assertEquals("NichtMatsNachname", mats.getLastName());
		assertEquals("NichtMats", mats.getFirstName());
	}

	// Contact Tests

	@Test
	public void testContactsInitial() {
		assertNotNull(mats.getContact());
	}

	@Test
	public void testContactsSetNull() {
		mats.setContact(null);
		assertNotNull(mats.getContact());
	}

	@Test
	public void testSetContact() {
		mats.setContact("example@example.com");
		assertEquals("example@example.com", mats.getContact());
	}


	// status test

	@Test
	public void testStatusInitial() {
		assertEquals(Customer.Status.New, mats.getStatus());
	}

	@Test
	public void testSetStatus() {
		mats.setStatus(Customer.Status.Active);
		assertEquals(Customer.Status.Active, mats.getStatus());

		mats.setStatus(Customer.Status.Deleted);
		assertEquals(Customer.Status.Deleted, mats.getStatus());

		mats.setStatus(Customer.Status.InRegistration);
		assertEquals(Customer.Status.InRegistration, mats.getStatus());

		mats.setStatus(Customer.Status.Suspended);
		assertEquals(Customer.Status.Suspended, mats.getStatus());

		mats.setStatus(Customer.Status.New);
		assertEquals(Customer.Status.New, mats.getStatus());
	}
}
