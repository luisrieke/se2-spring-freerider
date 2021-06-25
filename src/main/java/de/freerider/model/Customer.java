package de.freerider.model;

public class Customer {
	String id;
	String lastName;
	String firstName;
	String contact;
	Status status;

	public Customer() {
		this.lastName = "";
		this.firstName = "";
		this.contact = "";
		this.id = null;
		this.status = Status.New;
	}

	public Customer(String lastName, String firstName, String contact) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.contact = contact;
		this.id = null;
		this.status = Status.New;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(this.id == null || id == null) {
			this.id = id;
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName == null) {
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName == null) {
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		if(contact == null) {
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
