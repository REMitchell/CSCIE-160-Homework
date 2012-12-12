package cscie160.hw6;
import java.util.ArrayList;

import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.lang.Thread;

public class Server 
{
	//Server creates a collection (ArrayList) of ATMThreads
	public ArrayList<ATMRunnable> requestQueue;
	public ATM atmImplementation;
    private ServerSocket serverSocket;
    private BufferedReader bufferedReader;
    ArrayList<ATMRunnable> requestQ;
    static ATMThread[] pool;
    
    /**
     * The server 
     * @param port
     * @throws java.io.IOException
     */
    public Server(int port) throws java.io.IOException 
	{
    	requestQ = new ArrayList<ATMRunnable>();
    	atmImplementation = new ATMImplementation();
    	//Creates a pool of 5 threads 
    	pool = new ATMThread[5];
    	
    	for(int i = 0; i < 5; i++){
    		//requestQ is a reference to a list of commands that all threads share
    		pool[i] = new ATMThread(requestQ);
    		pool[i].start();
    		System.out.println(pool[i].getName());
    	}
		serverSocket = new ServerSocket(port);
    }
	
	public static void main(String argv[]) 
	{
		int port = 1099;
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
				//ATMRunnable tmpRunnable = new ATMRunnable(commandLine, printStream, atmImplementation);
				//tmpRunnable.run();

				//Rather than printing the command object to the stream, as in previous assignments, 
				//We create a new ATMRunnable object and pass the command stream to ATMRunnable
				//It is added to the queue so that threads can find it
				//addRunnable(new ATMRunnable(commandLine), requestQ);
				try{
					synchronized(requestQ){

						requestQ.add(new ATMRunnable(commandLine, printStream, atmImplementation));
						requestQ.notify();
						}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		catch (SocketException sException)
		{
			// client has stopped sending commands.  Exit gracefully.
			System.out.println("done");
		}
	}
	
}