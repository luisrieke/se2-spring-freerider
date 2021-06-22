package de.freerider;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest; // TODO 1

// @SpringBootTest TODO 2
public class SampleTests {

	//
	private static int loglevel = 2;		// 0: silent; 1: @Test methods; 2: all methods

	// Constructor
	public SampleTests() {
		log( "Constructor()", "ABCTest() called" );
	}

	/**
	 * Set up test, runs ONCE before every test execution
	 */
	@BeforeAll
	public static void setUp() {
		log( "@BeforeAll", "setUp()" );
	}

	/**
	 * Set up test, runs before EVERY test execution
	 */
	@BeforeEach
	public void setUpEach() {
		log( "@BeforeEach", "setUpEach()" );
	}


	/**
	 * -------------------------------
	 * @Test functions: abcFirstTest(), abcSecondTest(), abcThirdTest(), ...
	 */
	@Test
	void abcFirstTest() {
		//
		log( "abcFirstTest()" );
		//
		String s1 = "abc";
		String s2 = "abc";
		String s3 = "def";
		String s4 = null;
		//
		// use of JUnit5-jupiter assertions, see:
		// https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
		// https://stackabuse.com/unit-testing-in-java-with-junit-5
		//
		assertEquals( s1, s2 );		// s1 and s2 are equal
		assertNotEquals( s1, s3 );
		assertTrue( s1 == s2 );
		assertFalse( s1 == s3 );
		assertNull( s4 );
		assertNotNull( s1 );	
	}

	@Test
	void abcSecondTest() {
		log( "abcSecondTest()" );
		
	}

	@Test
	void abcThirdTest() {
		log( "abcThirdTest()" );
	}


	/**
	 * -------------------------------
	 * Tear down test, runs after EVERY test execution
	 */
	@AfterEach
	public void tearDownEach() {
		log( "@AfterEach", "tearDownEach()" );
	}

	/**
	 * Tear down test, runs ONCE after execution of all tests
	 */
	@AfterAll
	public static void tearDown() {
		log( "@AfterAll", "tearDown()" );
	}


	/*
	 * private methods
	 */
	private void log( String meth ) {
		if( loglevel >= 1 ) {
			System.out.println( "@Test: " + this.getClass().getSimpleName() + "." + meth );
		}
	}

	private static void log( String label, String meth ) {
		if( loglevel >= 2 ) {
			if( label.equals( "@BeforeEach" ) ) {
				System.out.println();
			}
			java.io.PrintStream out_ = System.out;
			if( label.equals( "@BeforeAll" ) || label.equals( "@AfterAll" ) ) {
				System.out.println();
				out_ = System.err;	// print in red color
			}
			out_.println( label + ": " + SampleTests.class.getSimpleName() + "." + meth );
		}
	}

}
