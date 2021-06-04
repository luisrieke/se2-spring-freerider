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
    void test5Customers() {

        // 5 neue Customer anlegen:
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
        LinkedList<Customer> customerList = new LinkedList<Customer>();
        customerList.add(anna);
        customerRepository.save(peter);
        customerRepository.saveAll(customerList);
        assertEquals(5, customerRepository.count());

        // findById prüfen, versuchen Anna zu finden:
        Optional<Customer> annaByID = customerRepository.findById(anna.getId());
        assertEquals("Anna", annaByID.get().getFirstName());

        // existsById prüfen:
        assertTrue(customerRepository.existsById(anna.getId()));

        // findAll prüfen:
        System.out.println("Prüfen wer angelegt wurde:");
        customerRepository.findAll().forEach(customer -> System.out.println(customer.getFirstName()));

        // findAllById prüfen:
        String[] listByID = new String[]{anna.getId()};
        Iterable<Customer> customerIterable = customerRepository.findAllById(Arrays.asList(listByID));
        System.out.println("\nPrüfen ob Anna gefunden wird:");
        customerIterable.forEach(customer -> System.out.println(customer.getFirstName()));

        // prüfen ob alles klappt wenn wir alle ein zweites mal speichern:
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

        // anlegen:
        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        Customer peter = new Customer("Weiß", "Peter", "peter.weiss@hotmail.com");

        // speichern:
        customerRepository.save(anna);
        customerRepository.save(peter);
        assertEquals(2, customerRepository.count());

        // löschen:
        String[] names = new String[]{anna.getId(), peter.getId()};
        customerRepository.deleteAllById(Arrays.asList(names));
        assertEquals(0, customerRepository.count());

    }

    @Test
    void deleteAll() {

        // anlegen:
        Customer anna = new Customer("Schwarz", "Anna", "anna.schwarz@gmail.com");
        Customer peter = new Customer("Weiß", "Peter", "peter.weiss@hotmail.com");

        // speichern:
        customerRepository.save(anna);
        customerRepository.save(peter);
        assertEquals(2, customerRepository.count());

        // löschen:
        customerRepository.deleteAll();
        assertEquals(0, customerRepository.count());

    }
}
