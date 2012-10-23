package cscie160.hw3;

public class Passenger {
	private int currentFloor;
	private int destFloor;
	private int direction;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	Passenger(int currentFloor, int destFloor){
		this.currentFloor = currentFloor;
		this.destFloor = destFloor;
		if(destFloor >= currentFloor){
			this.direction = 1;
		}else{
			this.direction = -1;
		}
	}
	
	public int getDestination(){
		return destFloor;
	}
	
	public int getCurrentFloor(){
		return currentFloor;
	}
	/**
	 * Used to create a new destination for passenger who wishes to
	 * take a particular elevator
	 * @param anElevator Elevator the passenger would like to ride
	 */
	private void newDestination(Elevator anElevator){
		//Look up randInt stuff, use anElevator.numFloors
		this.destFloor = 4;
		if(destFloor >= currentFloor){
			this.direction = 1;
		}else{
			this.direction = -1;
		}
	}
	
	public boolean arrive(Elevator anElevator){
		if(anElevator.currentFloor == destFloor){
			this.currentFloor = this.destFloor;
			this.direction = 0;
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Overrides the toString method. 
	 */
	
	public String toString(){
		return "Passenger heading "+direction+" for "+destFloor+"\n";
	}
}
