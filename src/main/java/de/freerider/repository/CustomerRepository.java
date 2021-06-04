package de.freerider.repository;

import de.freerider.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;


@Component
class CustomerRepository implements CrudRepository<Customer, String> {

    // --- Attribute ---

    // HashMap um IDs zuordnen zu können:
    private final HashMap<String, Customer> customers = new HashMap<String, Customer>();

    private final IDGenerator idGen = new IDGenerator("C", IDGenerator.IDTYPE.NUM, 6);

    // --- Methoden ---

    @Override
    public <S extends Customer> S save(S entity) {

        // wenn keine ID vorliegt
        if (entity.getId() == null || entity.getId().length() == 0) {
            // neue ID erzeugen:
            String newId = idGen.nextId();
            // beim Erzeugen neuer ID wird geprüft, dass diese
            // ID noch nicht in anderen Objekten verwendet wird:
            while (customers.get(newId) != null) {
                newId = idGen.nextId();
            }
            // ID der Entity zuordnen:
            entity.setId(idGen.nextId());
        }
        // in HashMap speichern:
        customers.put(entity.getId(), entity);
        return entity;

    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {

        // speichern der Entities:
        entities.forEach(this::save);
        return entities;

    }

    @Override
    public Optional<Customer> findById(String s) {

        Customer customer = customers.get(s);
        // kein Customer gefunden:
        if(customer == null) {
            return Optional.empty();
        } else {
            // Customer gefunden:
            return Optional.of(customer);
        }

    }

    @Override
    public boolean existsById(String s) {

        // Abfrage Entity ungleich null:
        boolean customerExists = customers.get(s) != null;
        return customerExists;

    }

    @Override
    public Iterable<Customer> findAll() {

        // alle Werte der HashMap returnen:
        return customers.values();

    }

    @Override
    public Iterable<Customer> findAllById(Iterable<String> strings) {

        // neue Liste die finale Customer ausgibt deren IDs stimmen:
        LinkedList<Customer> finalCustomers = new LinkedList<Customer>();

        // checken ob Customer vorhanden:
        strings.forEach(string -> {
            Customer foundCustomer = customers.get(string);
            if(foundCustomer != null) {
                finalCustomers.add(foundCustomer);
            }
        });

        return finalCustomers;

    }

    @Override
    public long count() {
        return customers.size();
    }

    @Override
    public void deleteById(String s) {
        customers.remove(s);
    }

    @Override
    public void delete(Customer entity) {
        if(entity == null) {
            throw new IllegalArgumentException();
        }
        customers.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        strings.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        customers.clear();
    }
}
