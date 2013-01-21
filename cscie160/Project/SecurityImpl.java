package cscie160.Project;
import java.util.HashMap;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


public class SecurityImpl extends UnicastRemoteObject implements Security{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private final HashMap<String, Integer> authValues;
	//The first element in the array indicates ability to deposit, then withdraw, then balance
	private final HashMap<String, boolean[]> permissionValues;
	
	public SecurityImpl() throws RemoteException{
		authValues = new HashMap<String, Integer>();
		authValues.put("0000001", 1234);
		authValues.put("0000002", 2345);
		authValues.put("0000003", 3456);
		permissionValues = new HashMap<String, boolean[]>();
		permissionValues.put("0000001", new boolean[]{true, true, true});
		permissionValues.put("0000002", new boolean[]{true, false, true});
		permissionValues.put("0000003", new boolean[]{false, true, true});
	}
		// TODO Auto-generated method stub
	public boolean authenticatePin(String accountNum, Integer Pin) throws AuthorizationException, RemoteException{
		System.out.println("Authenticating!");
		Integer pinOnRecord  = authValues.get(accountNum);
		System.out.println("The pin on record is "+pinOnRecord+"And the pin I've been given is "+Pin);
		if(pinOnRecord == null){
			throw new AuthorizationException("No such account exists");
		}
		if(pinOnRecord.equals(Pin)){
			System.out.println("Pin is correct!");
			return true;
		}
		System.out.println("Pin is horribly wrong!");
		throw new AuthorizationException("Incorrect PIN");
	}
	
	public boolean authorizeDeposit(String accountNum) throws AuthorizationException, RemoteException{
		boolean[] accountPermissions = permissionValues.get(accountNum);
		if(accountPermissions == null){
			throw new AuthorizationException("No such account exists");
		}
		if(accountPermissions[0] == true){
			return true;
		}
		throw new AuthorizationException("Deposit not allowed on this account");
	}
	
	public boolean authorizeWithdraw(String accountNum) throws AuthorizationException, RemoteException{
		boolean[] accountPermissions = permissionValues.get(accountNum);
		if(accountPermissions == null){
			throw new AuthorizationException("No such account exists");
		}
		if(accountPermissions[1] == true){
			return true;
		}
		throw new AuthorizationException("Withdrawal not allowed on this account");
	}
	
	public boolean authorizeBalance(String accountNum) throws AuthorizationException, RemoteException{
		boolean[] accountPermissions = permissionValues.get(accountNum);
		if(accountPermissions == null){
			throw new AuthorizationException("No such account exists");
		}
		if(accountPermissions[2] == true){
			System.out.println("Balance IS allowed on this account!");
			return true;
		}
		throw new AuthorizationException("Balance not allowed on this account");
	}
	
	public boolean authorizeTransfer(String fromAccountNum, String toAccountNum) throws AuthorizationException, RemoteException{
			boolean[] withdrawPermission = permissionValues.get(fromAccountNum);
			boolean[] depositPermission = permissionValues.get(toAccountNum);
			if(withdrawPermission == null){
				throw new AuthorizationException("Cannot withdraw from this account");
			}
			if(depositPermission == null){
				throw new AuthorizationException("Cannot deposit to this account");
			}
			if(withdrawPermission[1] == true && depositPermission[0]== true){
				//Transfer is allowed
				return true;
			}
			return false;
	}

}
