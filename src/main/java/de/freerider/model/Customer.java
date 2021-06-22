package de.freerider.model;

public class Customer {

    // --- Attribute ---

    private String id;
    private String lastName;
    private String firstName;
    private String contact;
    private Status status;

    // --- Konstruktor ---

    public Customer(String lastName, String firstName, String contact) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.contact = contact;
        this.id = null;
        this.status = Status.New;
    }

    // --- getter und setter ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (this.id == null) {
            this.id = id;
        }
        if (id == null) {
            this.id = null;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            this.lastName = "";
        } else {
            this.lastName = lastName;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            this.firstName = "";
        } else {
            this.firstName = firstName;
        }
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        if (contact == null) {
            this.contact = "";
        } else {
            this.contact = contact;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status {
        New,
        InRegistration,
        Active,
        Suspended,
        Deleted
    }
}
