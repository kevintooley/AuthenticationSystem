package authenticationsystem;

/**
 *This public class holds the userName and role of authenticated users
 * @author Kevin Tooley
 */
public class SystemUser {
    
    //public enum UserRole {ZOOKEEPER, VETERINARIAN, ADMIN}
    
    private static String userName;
    private static String myRole;
    
    public SystemUser () {
        
        userName = "none";
        myRole = "none";
        
    }
    
    /**
     * Mutator method to set the private member userName
     * @param thisUserName pass in userName
     */
    public void setUserName(String thisUserName) {
        userName = thisUserName;
    }
    
    /**
     * Accessor method to get the private userName
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * Mutator method to set the private myRole
     * @param thisRole pass in user role
     */
    public void setMyRole(String thisRole) {
        myRole = thisRole;
    }
    
    /**
     * Accessor method to get myRole
     * @return myRole
     */
    public String getMyRole() {
        return myRole;
    }
}
