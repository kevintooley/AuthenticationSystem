package authenticationsystem;

import java.io.BufferedReader;
import java.security.MessageDigest;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.NoSuchElementException;

/**
 * This system will authenticate users.  After authenticated, the user will be given
 * authority to perform certain actions based on their assigned role.
 * @author Kevin Tooley
 */
public class AuthenticationSystem {

    static SystemUser user = new SystemUser();  //Initialize SystemUser class
    static final String USER_DIR = System.getProperty("user.dir");  //Directory path for support files
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner scnr = new Scanner(System.in);  //System.in scanner

        String userName = "";  //Holds the scanner input
        String userPassword = "";  //Holds the scanner input
        
        boolean remainOnline = true;  //Setting to false will log off user
    
        if (authenticateUser(scnr, userName, userPassword, false)) {  //If authentication is successful
            
        	/*
        	 * Now that the user is authenticated, pass the user role to the pickRoleFile
        	 * method which will retrieve the role config file and print it
        	 */
        	System.out.println(getRoleFile(user.getMyRole()));
            
        	/*
        	 * Loop through the menu while the remainOnline flag is set.  The printMenu method will 
        	 * return false when the operator enters "q" for quit.
        	 */
            while (remainOnline) { //Menu Loops while user selects anything other than q (quit)
                
            	// Print the menu and evaluate remainOnline
            	remainOnline = printMenu(scnr);
            	
            }
            
        }
        
    }
    
    /**
     * Users must login to the system, and this method provides the user authentication.  
     * This function calls validateCredentials().
     * @param thisScanner Pass scnr from main class
     * @param userName pass userName from main class
     * @param userPassword pass userPassword from main class
     * @return passwordMatch
     * @see verifyCredentials
     */
    public static boolean authenticateUser(Scanner thisScanner, String userName, String userPassword, boolean isUnitTest) {
        
        //boolean userNameMatch = false;
        boolean passwordMatch = false;  //set to true when user is authenticated
        int loginAttempts = 0;  //Counts the number of login attempts
        
        while (!passwordMatch) { //While not authenticated...
            if (loginAttempts >= 3) {  //Only allow 3 login attempts
                System.out.println("Too many login attempts.  Goodbye.");
                System.exit(0);
            }
            else {
                
                if (!isUnitTest) {
                	System.out.print("User Name: ");
                	userName = thisScanner.next();  //next() vice nextLine(): no spaces in username
                	thisScanner.nextLine(); //consume the hanging /n
                }
                
                if (!isUnitTest) {
                	System.out.print("User Password: ");
                	userPassword = thisScanner.nextLine();
                }
                
                //Convert the password to MD5 hash for evaluation against Credentials File
                try {
                    String original = userPassword;
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    md.update(original.getBytes());
                    byte[] digest = md.digest();
                    StringBuffer sb = new StringBuffer();
                    for (byte b : digest) {
                    	sb.append(String.format("%02x", b & 0xff));
                    }

                    if (verifyCredentials(userName, sb.toString())) {  //if userName and password(hash) are valid
                        
                        passwordMatch = true;
                        
                        break;
                    }
                    
                    else {  //triggered if userName is valid but password fails
                        loginAttempts++;
                        System.out.println("Invalid credentials...");
                        System.out.println("");
                    }
                    
                } catch (NoSuchElementException nElement) {  //triggered if userName is invalid
                    loginAttempts++;
                    System.out.println("Invalid credentials...");
                    System.out.println("");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                
            } 
        }
        
        return passwordMatch;
    }
    
    /**
     * This function verifies that the user entered data matches the credentials on file.
     * @param thisUserName userName string passed from authenticateUser
     * @param thisUserPassword  userPassword string passed from authenticateUser
     * @return boolean isValid
     * @see authenticateUser
     */
    public static boolean verifyCredentials(String thisUserName, String thisUserPassword) {
        
        boolean foundUserName = false;  //Condition for while loop
        boolean isValid = false;  //Return value; true if validated credentials
        String[] userCredentials = new String[4];  //holds credentials of validated userName
        
        FileInputStream fileByteStream = null; // Initialize file input stream
        Scanner inFS = null;  //Initialize an input file stream scanner
        
        try {
        	
            fileByteStream = new FileInputStream(USER_DIR + "/src/CredentialsFile");
            inFS = new Scanner(fileByteStream);
            
        } catch (Exception ex) {
            
            System.out.println(ex);
            
        }
        
        //System.out.println("Parsing file...");
        while (!foundUserName) {  //Loop through the credential file and search for userName
            
            String currentLine = "";  //Initialize a temporary string for the scanner
            currentLine = inFS.nextLine();  //copy the scanner input
            
            if (currentLine.contains(thisUserName)) { //if userName is in credential file
                
                foundUserName = true;
                
                if (currentLine.contains(thisUserPassword)) {  //if password(hash) is correct for valid userName
                    
                    isValid = true;
                    userCredentials = currentLine.split(";");  //credential file delimiter
                    user.setUserName(thisUserName);
                    user.setMyRole(userCredentials[3]);
                    System.out.println("");
                    
                }
                
            }
        }
        
        try {
            
            fileByteStream.close();  //Close the credential file
            
        } catch (Exception ex) {
            
            System.out.println(ex);
            
        }

        return isValid;  //user is authenticated
    }
    
    /**
     * This function initializes a BufferedReader (reader) and reads each line of the
     * configuration file associated with the user role.  
     * Each line is passed into the returnValue string, eventually returned
     * @param role String
     * @return returnValue String
     */
    public static String getRoleFile(String role) {
        
    	String returnValue = "";
    	
        //Initialize bufferedReader for the file
        try {
            
            BufferedReader reader = new BufferedReader(new FileReader(USER_DIR + "/src/" + role));
            String line = null;
            while ((line = reader.readLine()) != null) { //Loop until end of file
            	returnValue = returnValue + line + "\n";
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        System.out.println("");
        
        return returnValue;
        
    }
    
    public static boolean printMenu(Scanner scnr) {
    	
    	boolean stayOnline = true;
    	
    	String prompt = "";
        System.out.println("*** SYSTEM MENU ***");
        System.out.println("1 - Perform Task A");
        System.out.println("2 - Perform Task B");
        System.out.println("q - Quit");
        System.out.println("Select and option: ");
        prompt = scnr.nextLine();
        switch (prompt) {
            case "1":
            case "2":  //Both Case 1 and 2 are stubbed out for future development
                System.out.println("");
                System.out.println("TODO - Finish Tasks");
                System.out.println("");
                break;
            case "q":
                stayOnline = false;
                System.out.println("");
                System.out.println("Quit.  Goodbye.");
                break;
            default:
                System.out.println("");
                System.out.println("Please enter a valid option...");
        }
        
        return stayOnline;
    	
    }

}
