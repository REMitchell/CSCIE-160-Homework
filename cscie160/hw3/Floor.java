package cscie160.hw3;
import java.util.HashSet;
import java.util.ArrayList;

import cscie160.hw3.Elevator.Direction;
import cscie160.hw3.Elevator;
import cscie160.hw3.ElevatorFullException;

/**
 * Represents a single Floor among a collection of Floors that is traversed by Elevator.
 * A floor contains three collections of Passengers -- upPassengers, downPassengers, and unqueuedPassengers
 * As well as a floor number
 * @author ryanmitchell
 *
 */

public class Floor {

	/**
	 * @param upPassengers Passengers queued for the Elevator, with a destination floor greater than floorNumber
	 * @param downPassengers Passengers queued for the Elevator, with a destination floor less than floorNumber 
	 * @param unqueuedPassengers Passengers that are on the floor, but not currently waiting for the elevator
	 * @param floorNumber The floor level (frequently used as a collection index by Elevator)
	 */

	public int floorNumber;
	
	//Hash set -- order does not matter
	private HashSet<Passenger> unqueuedPassengers = new HashSet<Passenger>();
	
	//ArrayList -- order matters
	private ArrayList<Passenger> upPassengers = new ArrayList<Passenger>();
	private ArrayList<Passenger> downPassengers = new ArrayList<Passenger>();
	
	/**
	 * Sets all collections passed when initializing Floor
	 * @param floorNumber
	 * @param upPassengers
	 * @param downPassengers
	 * @param unqueuedPassengers
	 */
	public Floor(int floorNumber, ArrayList<Passenger> upPassengers, ArrayList<Passenger> downPassengers, HashSet<Passenger> unqueuedPassengers){
		this.upPassengers = upPassengers;
		this.downPassengers = downPassengers;
		this.unqueuedPassengers = unqueuedPassengers;
		this.floorNumber = floorNumber;
	}
	
	
	/**
	 * Unloads all passengers onto the elevator, space permitting.
	 * Contains two main loops, one for processing passengers when elevator is headed up, another
	 * for when it's headed down
	 * @param anElevator the elevator object the passenger is using
	 * @param destFloor the floor the passenger wants to get off on
	 * @return boolean successful in loading all passengers or not. Not currently used
	 */

	public boolean unloadPassengers(Elevator anElevator){
		if(anElevator.direction == Direction.UP){
			while(upPassengers.size() > 0){
				try{
					anElevator.boardPassenger(upPassengers.get(0));
				} catch(ElevatorFullException e){
					//Elevator is full. Floor holds any remaining passengers
					//and waits for elevator to return
					System.out.println("Elevator is full! Leaving "+String.valueOf(upPassengers.size())+" behind");
					//Registers request so that the elevator will be sure to stop there next time
					anElevator.registerRequest(floorNumber, Direction.UP);
					//Lets the door close, elevator continues
					return false;
				}
				//Boarded successfully
				upPassengers.remove(0);
			}
		}
		
		if(anElevator.direction == Direction.DOWN){
			while(downPassengers.size() > 0){
				try{
					anElevator.boardPassenger(downPassengers.get(0));
				} catch(ElevatorFullException e){
					//Elevator is full. Floor holds any remaining passengers
					//and waits for elevator to return
					System.out.println("Elevator is full! Leaving "+String.valueOf(downPassengers.size())+" behind");
					//Registers request so that the elevator will be sure to stop there next time
					anElevator.registerRequest(floorNumber, Direction.DOWN);
					//Lets the door close, elevator continues
					return false;
				}
				//Boarded successfully
				downPassengers.remove(0);
			}
		}

		//Passengers should be equal to zero at this point
		//Elevator no longer needs to stop on this floor
		//Lets the door close, elevator continues
		return true;
	}
	
	/**
	 * Accessor method for unqueuedPassengers, adds all passengers passed by Elevator to 
	 * unqueuedPassengers collection for this floor
	 * @param newPassengers
	 */
	public void acceptPassengers(HashSet<Passenger> newPassengers){
		//All passengers bound for this floor exit
		unqueuedPassengers.addAll(newPassengers);
	}
	
	/**
	 * Accessor method for unqueuedPassengers, adds a single passenger from Elevator to
	 * unqueuedPassengers collection for this floor
	 * @param newPassenger
	 */
	public void acceptPassenger(Passenger newPassenger){
		unqueuedPassengers.add(newPassenger);
	}
	
	/**
	 * represents a passenger walking up the elevator and wanting to board
	 * @param anElevator the elevator object the passenger is being loaded onto
	 */
	public void startWaiting(Elevator anElevator, Passenger waitingPass){
		unqueuedPassengers.remove(waitingPass);
		if(waitingPass.getDestination() > floorNumber){
			//Passenger is going up

			upPassengers.add(waitingPass);
			anElevator.registerRequest(floorNumber, Direction.UP);
		}else{
			System.out.println(waitingPass.toString()+" started waiting");
			//Passenger is going down
			downPassengers.add(waitingPass);
			anElevator.registerRequest(floorNumber, Direction.DOWN);
		}
	}
	public int getNumWaiting(){
		return upPassengers.size()+downPassengers.size();
	}
	
	public ArrayList<Passenger> getUpPassengers(){
		return upPassengers;
	}
	public ArrayList<Passenger> getDownPassengers(){
		return downPassengers;
	}

	
	public String toString(){
		String returnString = "Floor "+String.valueOf(floorNumber)+"\nThere are "+String.valueOf(upPassengers.size())+" Up passengers\n"+"There are "+String.valueOf(downPassengers.size())+" Down passengers\n"+"There are "+String.valueOf(unqueuedPassengers.size())+" unqueued passengers";
		return returnString;
	}
}
