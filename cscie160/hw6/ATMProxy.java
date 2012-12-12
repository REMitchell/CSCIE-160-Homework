package cscie160.hw6;

import java.net.*;
import java.io.*;


/**
 * ATM Proxy is a client-side class which manages the connection to the server and 
 * forwards the client's requests to the server by writing the text of the requests
 * to a stream in a socket established at creation (usually localhost, 7777)
 * @author ryanmitchell
 *
 */
public class ATMProxy implements ATM 
{
    private Socket socket;
    private PrintStream  printStream;
    BufferedReader inputReader;
    
    /**
     * Creates new input and output sockets, an input reader and an output stream
     */
    public ATMProxy(String host, int port) throws UnknownHostException, java.io.IOException 
	{
		socket = new Socket(host, port);
		OutputStream outputStream = socket.getOutputStream();
		printStream = new PrintStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		inputReader = new BufferedReader(inputStreamReader);
    }
	
    /**
     * Prints the command on the client side, and sends command and deposit amount to the server
     */
    public void deposit(float amount) throws ATMException 
	{
		// Commands is an enum in this package
		System.out.println("ATMProxy writing command to server: " + Commands.DEPOSIT);
		printStream.println(Commands.DEPOSIT + " " + amount);
    }
	
    /**
     * Prints the command on the client side, and sends command and withdrawal amount to the server
     */
    public void withdraw(float amount) throws ATMException 
	{
		System.out.println("ATMProxy writing command to server: " + Commands.WITHDRAW);
		printStream.println(Commands.WITHDRAW +  " " +  amount);
    }
	
    /**
     * Prints the command on the client side, sends the balance request to the server, 
     * and attempts to receive a response through inputReader. Either prints the response
     * on the client side, or throws an error if the response is not received
     */
    public float getBalance() throws ATMException 
	{
		System.out.println("ATMProxy writing command to server: " + Commands.BALANCE);
		printStream.println(Commands.BALANCE);
		try
		{
			String response = inputReader.readLine();
			if (response != null)
			{
			System.out.println("Server returned: " + response);
			return Float.parseFloat(response.trim());
			} 
			else 
			{
				throw new ATMException("ATMProxy: Unexpected end of stream reading commands in getBalance()");
			}
				
		} 
		catch (Exception ex) 
		{
			throw new ATMException(ex.toString());
		}
    }
}
