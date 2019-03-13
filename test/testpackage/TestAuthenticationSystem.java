package testpackage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import authenticationsystem.AuthenticationSystem;

class TestAuthenticationSystem {

	@Test
	void testAuthenticateUser() {
		
		AuthenticationSystem as = new AuthenticationSystem();
		
		Scanner scnr = new Scanner(System.in);
		String userName = "bruce.grizzlybear";
		String userPassword = "letmein";
		
		assertTrue(as.authenticateUser(scnr, userName, userPassword, true));

	}

	@Test
	void testVerifyCredentials() {
		AuthenticationSystem as = new AuthenticationSystem();
		
		String userName = "bruce.grizzlybear";
		String userPassword = "letmein";
		
		assertTrue(as.verifyCredentials(userName, userPassword));

	}

	/*@Test
	void testGetRoleFile() {
		fail("Not yet implemented");
	}*/

}
