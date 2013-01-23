package cscie160.Project;
import java.util.HashMap;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * Implements Security
 * Security is a remote object that has methods for authenticating AccountInfo objects and for authorizing 
 * specific operations on individual Accounts.
 * @author ryanmitchell
 */
public class SecurityImpl extends UnicastRemoteObject implements Security{
	private static final long serialVersionUID = 1L;
	private final HashMap<String, Integer> authValues;
	//The permission values indicate the ability to deposit, withdraw, and get balance, in that order
	private final HashMap<String, boolean[]> permissionValues;
	
	/**
	 * Security is initialized and sets hardcoded authentication information and permission values
	 * @throws RemoteException
	 */
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
	
	/**
	 * Takes an AccountInfo object and compares its pin to the pin it has on record for the given
	 * account number. Returns true if the pin is correct, or throws an authorization exception if the pin
	 * is incorrect or the account number does not exist in its records.
	 */
	public boolean authenticatePin(AccountInfo account) throws AuthorizationException, RemoteException{
		Integer pinOnRecord  = authValues.get(account.getAccountNum());
		if(pinOnRecord == null){
			throw new AuthorizationException("No such account exists");
		}
		if(pinOnRecord.equals(account.getPin())){
			return true;
		}
		throw new AuthorizationException("Incorrect PIN");
	}
	
	/**
	 * Takes an account number and looks up the deposit permissions for that particular account. Returns an 
	 * AuthorizationException if deposits are not allowed, or if the account does not exist, returns true if 
	 * the deposit is allowed on that account
	 */
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
	
	/**
	 * Takes an account number and looks up the withdraw permissions for that particular account. Returns an 
	 * AuthorizationException if withdrawals are not allowed, or if the account does not exist, returns true if 
	 * the withdrawal is allowed on that account
	 */
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
	
	/**
	 * Takes an account number and looks up the "getBalance" permissions for that particular account. Returns an 
	 * AuthorizationException if the balance is not allowed, or if the account does not exist, returns true if 
	 * the balance is allowed to be seen on that account
	 */
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
	
	/**
	 * Takes two account numbers and looks up the withdraw permissions from the first ("from") account,
	 * and the deposit permissions on the second ("to") account. If both of them are true (the transfer
	 * is allowed), it returns true. Throws an AuthorizationExeption if the transfer is not allowed, or
	 * if one or both of the accounts does not exist.
	 */
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
