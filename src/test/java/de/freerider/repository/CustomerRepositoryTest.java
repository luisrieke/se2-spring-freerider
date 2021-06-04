package de.freerider.repository;

import de.freerider.model.Customer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRepositoryTest {

    // neues CustomerRepository anlegen:
    CustomerRepository customerRepository = new CustomerRepository();

    @Test
    void testCustomerRepo() {

        // neue Customer anlegen:
        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        Customer peter = new Customer("Weiß", "Peter", "peter.weiss@hotmail.com");
        Customer hans = new Customer("Blau", "Hans", "hans.blau@mail.com");
        Customer sabina = new Customer("Grün", "Sabina", "sabina.gruen@outlook.de");
        Customer jakob = new Customer("Gelb", "Jakob", "jakob.gelb@gmx.de");

        // ins customerRepository einchecken:
        customerRepository.save(anna);
        customerRepository.save(peter);
        customerRepository.save(hans);
        customerRepository.save(sabina);
        customerRepository.save(jakob);

        // prüfen ob exakt 5 Einträge vorhanden sind:
        assertEquals(5, customerRepository.count());

        // saveAll prüfen:
        LinkedList<Customer> customerLinkedList = new LinkedList<Customer>();
        customerLinkedList.add(anna);
        customerRepository.saveAll(customerLinkedList);
        assertEquals(5, customerRepository.count());

        // versuchen Anna zu finden:
        Optional<Customer> foundAnna = customerRepository.findById(anna.getId());
        assertEquals("Anna", foundAnna.get().getFirstName());

        assertTrue(customerRepository.existsById(anna.getId()));

        System.out.println("Prüfen wer angelegt wurde:");
        customerRepository.findAll().forEach(customer -> System.out.println(customer.getFirstName()));


        String[] findAllByIdName = new String[]{anna.getId()};
        Iterable<Customer> findAllByIdIter = customerRepository.findAllById(Arrays.asList(findAllByIdName));
        System.out.println("\nPrüfen ob Anna gefunden wird:");
        findAllByIdIter.forEach(customer -> System.out.println(customer.getFirstName()));

        // prüfen ob alles klappt wenn wir wieder alle speichern:
        customerRepository.save(anna);
        customerRepository.save(peter);
        customerRepository.save(hans);
        customerRepository.save(sabina);
        customerRepository.save(jakob);
        assertEquals(5, customerRepository.count());

    }


    @Test
    void deleteById() {
        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        customerRepository.save(anna);
        assertEquals(1, customerRepository.count());
        customerRepository.deleteById(anna.getId());
        assertEquals(0, customerRepository.count());
    }

    @Test
    void delete() {

        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        customerRepository.save(anna);
        assertEquals(1, customerRepository.count());

        customerRepository.delete(anna);
        assertEquals(0, customerRepository.count());

    }

    @Test
    void deleteAllById() {

        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        Customer peter = new Customer("Weiß", "Peter", "peter.weiss@hotmail.com");

        customerRepository.save(anna);
        customerRepository.save(peter);
        assertEquals(2, customerRepository.count());

        String[] names = new String[]{anna.getId(), peter.getId()};
        customerRepository.deleteAllById(Arrays.asList(names));
        assertEquals(0, customerRepository.count());

    }

    @Test
    void deleteAll() {

        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        Customer peter = new Customer("Weiß", "Peter", "peter.weiss@hotmail.com");

        customerRepository.save(anna);
        customerRepository.save(peter);
        assertEquals(2, customerRepository.count());

        customerRepository.deleteAll();
        assertEquals(0, customerRepository.count());

    }
}
