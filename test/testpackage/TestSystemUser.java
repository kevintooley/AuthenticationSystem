package testpackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import authenticationsystem.SystemUser;

class TestSystemUser {

	/**
	 * This tests the the mutator method of the SystemUser Class.
	 * First the SystemUser object is initialized.  The userName is set.
	 * The assertFalse verifies the no-go path, while the assertTrue verifies
	 * the setUserName method accomplishes its assigned task.
	 */
	@Test
	void testSetUserName() {
		SystemUser su = new SystemUser();  	// Initialize
		su.setUserName("New.Username"); 	// Set the userName
		assertFalse(su.getUserName().equals("none"));
		assertTrue(su.getUserName().equals("New.Username"));
	}

	/**
	 * This tests the accessor method of the SystemUser class.
	 * First the SystemUser is initialized.  Since the constructor
	 * sets the userName field, all that is needed is to verify
	 * the go and no-go paths.  
	 */
	@Test
	void testGetUserName() {
		SystemUser su = new SystemUser();
		assertFalse(su.getUserName().equals("something_wrong"));
		assertFalse(su.getUserName().equals("thisisnotit"));
		assertTrue(su.getUserName().equals("none"));
	}

	/**
	 * This tests the the mutator method of the SystemUser Class.
	 * First the SystemUser object is initialized.  The myRole field is set.
	 * The assertFalse verifies the no-go path, while the assertTrue verifies
	 * the getMyRole method accomplishes its assigned task.
	 */
	@Test
	void testSetMyRole() {
		SystemUser su = new SystemUser();  	// Initialize
		su.setMyRole("New.Role"); 			// Set the myRole
		assertFalse(su.getMyRole().equals("none"));
		assertTrue(su.getMyRole().equals("New.Role"));
	}

	/**
	 * This tests the accessor method of the SystemUser class.
	 * First the SystemUser is initialized.  Since the constructor
	 * sets the myRole field, all that is needed is to verify
	 * the go and no-go paths.
	 */
	@Test
	void testGetMyRole() {
		SystemUser su = new SystemUser();
		assertFalse(su.getMyRole().equals("something_wrong"));
		assertFalse(su.getMyRole().equals("thisisnotit"));
		assertTrue(su.getMyRole().equals("none"));
	}

}
