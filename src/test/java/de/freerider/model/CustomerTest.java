package de.freerider.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerTest {

    // --- Attribute ---

    private Customer mats;
    private Customer thomas;

    @BeforeEach
    public void setUpEach() {
        // neue Customer anlegen:
        mats = new Customer("Schwarz", "Mats", "mats.schwarz@email.de");
        thomas = new Customer("Weiss", "Thomas", "thomas.weiss@email.de");
    }

    @Test
    void IdTests() {
        testIdNull();
        testSetId();
        testSetIdOnlyOnce();
        testResetId();
    }

    @Test
    void NameTests() {
        testNamesInitial();
        testNamesSetNull();
        testSetNames();
    }

    @Test
    void ContactTest() {
        testContactsInitial();
        testContactsSetNull();
        testSetContact();
    }

    @Test
    void StatusTest() {
        testStatusInitial();
        testSetStatus();
    }

    // --- Methoden ---

    // a. testIdNull() – prüft, dass das Id‐Attribut eines neu erzeugten Customer‐Objekts null ist.
    void testIdNull() {
        assertEquals(null, mats.getId());
        assertEquals(null, thomas.getId());
    }

    // b. testSetId() – prüft, dass Id mit einem nicht‐Null Wert gesetzt werden kann.
    void testSetId() {
        mats.setId("123");
        thomas.setId("456");
        assertEquals("123", mats.getId());
        assertEquals("456", thomas.getId());
    }

    // c. testSetIdOnlyOnce() – prüft, dass nach dem Setzen eines (nicht‐Null) Id, der Id nicht erneut mit einem neuen (nicht‐Null) Wert gesetzt werden kann.
    void testSetIdOnlyOnce() {
        mats.setId("123");
        thomas.setId("456");
        mats.setId("888");
        thomas.setId("888");
        assertEquals("123", mats.getId());
        assertEquals("456", thomas.getId());
    }

    // d. testResetId() – prüft, dass der Id mit setId(null); zurückgesetzt werden kann.
    void testResetId() {
        mats.setId("123");
        thomas.setId("456");
        mats.setId(null);
        thomas.setId(null);
        assertEquals(null, mats.getId());
        assertEquals(null, thomas.getId());
    }

    // e. testNamesInitial() – prüft, dass Vor‐ und Nachname initial mit ““ (nicht null) initialisiert sind.

    void testNamesInitial() {
        assertNotNull(mats.getFirstName());
        assertNotNull(mats.getFirstName());
        assertNotNull(thomas.getLastName());
        assertNotNull(thomas.getLastName());
    }

    // f. testNamesSetNull() – prüft, dass Vor‐ oder Nachnamen bei dem Versuch, diese auf Null zu setzen, die getter‐Methoden ““ (leerer String) zurückgeben.

    void testNamesSetNull() {
        mats.setFirstName(null);
        mats.setLastName(null);
        thomas.setFirstName(null);
        thomas.setLastName(null);
        assertEquals("", mats.getFirstName());
        assertEquals("", mats.getLastName());
        assertEquals("", thomas.getFirstName());
        assertEquals("", thomas.getLastName());
    }

    // g. testSetNames() – prüft das Setzen von Vor‐ und Nachnamen auf reguläre Werte (nicht null und nicht ““ ) anhand von Beispielnamen. Die getter‐Methoden geben diese Werte entspr. zurück.

    void testSetNames() {
        mats.setFirstName("Fabian");
        mats.setLastName("Hannes");
        thomas.setFirstName("Brigitte");
        thomas.setLastName("Becker");
        assertEquals("Fabian", mats.getFirstName());
        assertEquals("Hannes", mats.getLastName());
        assertEquals("Brigitte", thomas.getFirstName());
        assertEquals("Becker", thomas.getLastName());
    }

    // h. testContactsInitial(), testContactsSetNull(), testSetContact() mit denselben Annahmen wie für Namen.

    void testContactsInitial() {
        assertNotNull(mats.getContact());
        assertNotNull(thomas.getContact());
    }

    void testContactsSetNull() {
        mats.setContact(null);
        thomas.setContact(null);
        assertEquals("", mats.getContact());
        assertEquals("", thomas.getContact());
    }

    void testSetContact() {
        mats.setContact("hallo.welt@email.de");
        thomas.setContact("wer.da@email.de");
        assertEquals("hallo.welt@email.de", mats.getContact());
        assertEquals("wer.da@email.de", thomas.getContact());
    }

    // i. testStatusInitial() – prüft, dass der initiale Status eines Kunden New ist.

    void testStatusInitial() {
        assertEquals(Customer.Status.New, mats.getStatus());
        assertEquals(Customer.Status.New, thomas.getStatus());
    }

    // j. testSetStatus() – prüft, dass nach dem Setzen eines neuen Status, dieser mit der getter‐Methode zurückgegeben wird (für alle Werte in enum Status prüfen).

    void testSetStatus() {
        mats.setStatus(Customer.Status.InRegistration);
        thomas.setStatus(Customer.Status.InRegistration);
        assertEquals(Customer.Status.InRegistration, mats.getStatus());
        assertEquals(Customer.Status.InRegistration, thomas.getStatus());

        mats.setStatus(Customer.Status.Active);
        thomas.setStatus(Customer.Status.Active);
        assertEquals(Customer.Status.Active, mats.getStatus());
        assertEquals(Customer.Status.Active, thomas.getStatus());

        mats.setStatus(Customer.Status.Suspended);
        thomas.setStatus(Customer.Status.Suspended);
        assertEquals(Customer.Status.Suspended, mats.getStatus());
        assertEquals(Customer.Status.Suspended, thomas.getStatus());

        mats.setStatus(Customer.Status.Deleted);
        thomas.setStatus(Customer.Status.Deleted);
        assertEquals(Customer.Status.Deleted, mats.getStatus());
        assertEquals(Customer.Status.Deleted, thomas.getStatus());
    }

}
