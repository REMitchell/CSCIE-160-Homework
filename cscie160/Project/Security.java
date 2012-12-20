package cscie160.Project;

/**
 * The Security component is a remote object that has 
 * methods for authenticating AccountInfo objects and 
 * for authorizing specific operations on individual 
 * Accounts.
 * @author ryanmitchell
 *
 */
//REMOTE. Represents the client
public class Security {
	Map<Integer accountNum, Integer pin> authValues;
	
	public Security() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
