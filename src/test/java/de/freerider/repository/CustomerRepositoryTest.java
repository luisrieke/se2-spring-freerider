package de.freerider.repository;

import de.freerider.model.Customer;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CrudRepository<Customer, String> customerRepository;

    //sample customers
    private Customer mats;
    private Customer thomas;

    @BeforeEach
    public void CustomersSetup() {
        mats = new Customer("Matsen", "Mats", "mmatsen@email.de");
        thomas = new Customer("Thomasen", "Thomas", "tthomasen@email.de");
    }

    @AfterEach
    public void ClearRepository() {
        customerRepository.deleteAll();
    }

    // Konstruktor-Tests:
    @Test
    void testThatRepoIsEmpty() {
        long customerCount = customerRepository.count();
        assertEquals(customerCount, 0);
        assertNotNull(customerRepository);
    }

}
