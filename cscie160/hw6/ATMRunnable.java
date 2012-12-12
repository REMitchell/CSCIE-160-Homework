package cscie160.hw6;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import cscie160.hw6.ATMException;
import cscie160.hw6.Commands;

/**
 * Parses the request string to determine which operation
 *  (deposit, withdraw, balance) is required and the amount 
 *  for deposit/withdraw.
 *  Invokes the appropriate method through the ATM interface.
 *  Writes a message back to the client, reporting the result.
 * @author ryanmitchell
 *
 */

public class ATMRunnable implements Runnable{
	public static ATM atmImplementation;
	private String commandLine;
	public static PrintStream printStream;
	
	/**
	 * Initializes commandLine, atmImplementation, and printStream, which are provided by Server
	 * atmImplementation and printStream are static and shared among all threads and server-side classes
	 * commandLine is unique to this instance of ATMRunnable
	 */
	public ATMRunnable(String myCommandLine, PrintStream myPrintstream, ATM myAtmImplementation) throws UnknownHostException, java.io.IOException{
		commandLine = myCommandLine;
		atmImplementation = myAtmImplementation;
		printStream = myPrintstream;
	}

	/**
	 * This is invoked by an ATMThread. It parses the commandLine into the command and the amount argument (or 0.00, in 
	 * the case of BALANCE) Because run cannot throw errors, we call executeCommand to execute the commands
	 */
	public void run(){
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		String commandAndParam[] = new String[tokenizer.countTokens()];
		int index = 0;
		while (tokenizer.hasMoreTokens()) 
		{
			commandAndParam[index++] = tokenizer.nextToken();
		}
		String command = commandAndParam[0];
		
		float amount;
		if(commandAndParam.length > 1){
			amount = Float.parseFloat(commandAndParam[1]);
		}else{
			amount = (float) 0.00;
		}
		
		try{
			executeCommand(command, amount);
		}catch(ATMException ex){
			System.out.println("Problem executing command");
		}
	}
	
	/**
	 * Executes the command to atmImplementation, passing the argument amount, 
	 * where appropriate. Throws an error on failure
	 */
	public void executeCommand(String command, float amount) throws ATMException{
		// Dispatch BALANCE request without further ado.
		try 
		{
			if (command.equalsIgnoreCase(Commands.BALANCE.toString())) 
			{
				float balance = atmImplementation.getBalance();
				//Should print back to the client
				printStream.println(balance);
			}
			else if (command.equalsIgnoreCase(Commands.DEPOSIT.toString())) 
			{

				atmImplementation.deposit(amount);	      
			}
			else if (command.equalsIgnoreCase(Commands.WITHDRAW.toString())) 
			{				

				atmImplementation.withdraw(amount);
			} else 
			{

				throw new ATMException("Unrecognized command: " + command);
			}
		} 
		catch (NumberFormatException nfe) 
		{
			throw new ATMException("Unable to make float from input: " + amount);
		}
	}	
}
