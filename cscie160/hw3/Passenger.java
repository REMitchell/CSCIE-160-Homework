package cscie160.hw3;

import cscie160.hw3.Elevator.Direction;

public class Passenger {
	private int currentFloor;
	private int destFloor;
	private Direction direction;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	Passenger(int currentFloor, int destFloor){
		this.currentFloor = currentFloor;
		this.destFloor = destFloor;
		if(destFloor >= currentFloor){
			this.direction = Direction.UP;
		}else{
			this.direction = Direction.DOWN;
		}
	}
	
	public int getDestination(){
		return destFloor;
	}
	
	public int getCurrentFloor(){
		return currentFloor;
	}

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
