package de.freerider.repository;

import org.springframework.stereotype.Component;


@Component
class CustomerRepository { //implements CrudRepository<Customer, String> {
	//
	private final IDGenerator idGen = new IDGenerator( "C", IDGenerator.IDTYPE.NUM, 6 );


	
}
