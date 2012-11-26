package cscie160.hw3;

import cscie160.hw3.Elevator.Direction;
/**
 * Represents a passenger with a current floor, destination, and direction (calculated from the current floor
 * and the destination floor). Used by Floor and Elevator
 * @author ryanmitchell
 *
 */

public class Passenger {
	/**
	 * @param currentFloor The floor that the passenger is on
	 * @param destFloor The floor the passenger wants to go to. If passenger is in a floor's unqueuedPassengers list, destFloor
	 * should be equal to currentFloor
	 **/
	private int currentFloor;
	private int destFloor;
	private Direction direction;
	
	/**
	 * Creates a new passenger with a currentFloor and destFloor defined, calculates the direction the passenger
	 * needs to go in. If currentFloor == destFloor, Direction.UP is used as a default
	 * @param currentFloor
	 * @param destFloor
	 */
	Passenger(int currentFloor, int destFloor){
		this.currentFloor = currentFloor;
		this.destFloor = destFloor;
		if(destFloor >= currentFloor){
			this.direction = Direction.UP;
		}else{
			this.direction = Direction.DOWN;
		}
	}
	/**
	 * Getter method for destFloor
	 * @return desired destination floor
	 */
	public int getDestination(){
		return destFloor;
	}
	
	/**
	 * Getter method for currentFloor
	 * @return the floor the passenger is currently on
	 */
	public int getCurrentFloor(){
		return currentFloor;
	}

	/**
	 * Called so the passenger can check if the elevator they are in is on the floor they want to 
	 * get off on. 
	 * @param anElevator the elevator the passenger is riding in
	 * @return true if passenger wants to get off here, false otherwise
	 */
	public boolean arrive(Elevator anElevator){
		if(anElevator.currentFloor == destFloor){
			this.currentFloor = this.destFloor;
			this.direction = null;
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Overrides the toString method.
	 */
	
	public String toString(){
		return "Passenger on floor "+currentFloor+" heading "+direction+" for floor "+destFloor;
	}
}
