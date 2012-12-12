package cscie160.hw5;

import java.net.*;
import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.StringTokenizer;


/**
 * The server process starts up, creates an ATMFactoryImpl instance, and registers it with 
 * the RMI. After it is registered, it can accept and execute commands from the client
 * @author ryanmitchell
 *
 */

public class Server 
{
	
    private ServerSocket serverSocket;
    private ATM atmImplementation;
    private BufferedReader bufferedReader;
    private ATMFactoryImpl myFactory;
    
    public Server(int port) throws java.io.IOException 
	{
    	
    	myFactory = new ATMFactoryImpl();
    	
		try{
			Naming.rebind("//localhost/atmfactory", myFactory);
			System.out.println("myATMFactory is bound in registry");
		} catch (Exception e) {
			System.out.println("ATMFactoryImpl error: "+e.getMessage());
			e.printStackTrace();
		}

		serverSocket = new ServerSocket(port);
		atmImplementation = new ATMImpl();
    }
	
    /** serviceClient accepts a client connection and reads lines from the socket.
     *  Each line is handed to executeCommand for parsing and execution.
     */
    public void serviceClient() throws java.io.IOException 
	{
		System.out.println("Accepting clients now");
		Socket clientConnection = serverSocket.accept();
		
		// Arrange to read input from the Socket	
		InputStream inputStream = clientConnection.getInputStream();
		bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		// Arrange to write result across Socket back to client
		OutputStream outputStream = clientConnection.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
		
		System.out.println("Client acquired on port #" + serverSocket.getLocalPort() + ", reading from socket");
		
		try
		{
			String commandLine;
			while ((commandLine = bufferedReader.readLine()) != null) 
			{
				try 
				{
					Float result = executeCommand(commandLine);
					// Only BALANCE command returns non-null
					if (result != null) 
					{ 
						printStream.println(result);  // Write it back to the client
					}
				} 
				catch (ATMException atmex) 
				{
					System.out.println("ERROR: " + atmex);
				} 
				
			}
		}
		catch (SocketException sException)
		{
			// client has stopped sending commands.  Exit gracefully.
			System.out.println("done");
		}
	}
	
	/** The logic here is specific to our protocol.  We parse the string
	 *  according to that protocol.
	 */
	private float executeCommand(String commandLine) throws ATMException 
	{
		// Break out the command line into String[]
		StringTokenizer tokenizer = new StringTokenizer(commandLine);
		String commandAndParam[] = new String[tokenizer.countTokens()];
		int index = 0;
		while (tokenizer.hasMoreTokens()) 
		{
			commandAndParam[index++] = tokenizer.nextToken();
		}
		String command = commandAndParam[0];
		//Gets the account number as the second argument e.g. BALANCE 0000001 or DEPOSIT 0000003 54.00
		Integer accountNum = Integer.parseInt(commandAndParam[1]);

		// Dispatch BALANCE request without further ado.
		if (command.equalsIgnoreCase(Commands.BALANCE.toString())) 
		{
			try{
				return atmImplementation.getBalance(accountNum);
			}catch(RemoteException e){
	        	 System.out.println("RemoteException");
	        	 e.printStackTrace();
			}
		}
		// Must have 2nd arg for amount when processing DEPOSIT/WITHDRAW commands
		if (commandAndParam.length < 2) 
		{
			throw new ATMException("Missing amount for command \"" + command + "\"");
		}
		try 
		{
			float amount = Float.parseFloat(commandAndParam[2]);
			if (command.equalsIgnoreCase(Commands.DEPOSIT.toString())) 
			{
				atmImplementation.deposit(accountNum, amount);	      
			}
			else if (command.equalsIgnoreCase(Commands.WITHDRAW.toString())) 
			{
				atmImplementation.withdraw(accountNum, amount);
			} else 
			{
				throw new ATMException("Unrecognized command: " + command);
			}
		} 
		catch (NumberFormatException nfe) 
		{
			throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
		}
		catch (RemoteException e)
		{
       	 System.out.println("RemoteException");
       	 e.printStackTrace();
		}
		// BALANCE command returned result above.  Other commands return null;
		return (float) 0.00;
	}
	
	public static void main(String argv[]) 
	{
		int port = 1098;
		if(argv.length > 0) 
		{
			try 
			{
				port = Integer.parseInt(argv[0]);
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace(); 
			}
		}
		try 
		{
			Server server = new Server(port);
			server.serviceClient();
			System.out.println("Client serviced");
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
}
