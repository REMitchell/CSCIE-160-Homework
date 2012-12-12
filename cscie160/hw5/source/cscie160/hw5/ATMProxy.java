package cscie160.hw5;

import java.net.*;
import java.io.*;

/** Client-side proxy class which manages the connection to the
 *  server and forwards the client's requests to the server by writing
 *  the text of requests to the stream on top of the socket established
 *  at creation time when the constructor is called.
 */
public class ATMProxy implements ATM 
{
    private Socket socket;
    private PrintStream  printStream;
    BufferedReader inputReader;
	//do not modify
    public ATMProxy(String host, int port) throws UnknownHostException, java.io.IOException 
	{
		socket = new Socket(host, port);
		OutputStream outputStream = socket.getOutputStream();
		printStream = new PrintStream(outputStream);
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		inputReader = new BufferedReader(inputStreamReader);
    }
	
    public boolean deposit(Integer accountNum, float amount) throws ATMException
	{
		// Commands is an enum in this package
		System.out.println("ATMProxy writing command to server: " + Commands.DEPOSIT);
		printStream.println(Commands.DEPOSIT + " " + accountNum.toString() + " "+amount);
		try 
		{
			String response = inputReader.readLine();
			if (response != null)
			{
				if(response == "true"){
					//completed successfully
					return true;
				}else{
					//Account attempted to be overdrawn
					return false;
				}
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
	
    public boolean withdraw(Integer accountNum, float amount) throws ATMException 
	{
		System.out.println("ATMProxy writing command to server: " + Commands.WITHDRAW);
		printStream.println(Commands.WITHDRAW +  " " + accountNum.toString() + " "+amount);
		try 
		{
			String response = inputReader.readLine();
			if (response != null)
			{
				if(response == "true"){
					//completed successfully
					return true;
				}else{
					//Account attempted to be overdrawn
					return false;
				}
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
    
    public float getBalance(Integer accountNum) throws ATMException 
	{
		System.out.println("ATMProxy writing command to server: " + Commands.BALANCE);
		printStream.println(Commands.BALANCE+" "+accountNum.toString());
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
