package cscie160.hw2;

public class Floor {

	/**
	 * @param passengers Number of passengers waiting on the floor to board the elevator
	 * @param floorNumber The floor level (frequently used as a reference by the elevator)
	 */

	public int passengers;
	public int floorNumber;
	public Floor(int passengers, int floorNumber){
		this.passengers = passengers;
		this.floorNumber = floorNumber;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Unloads all passengers onto the elevator, space permitting
	 * @param anElevator the elevator object the passenger is using
	 * @param destFloor the floor the passenger wants to get off on
	 * @return boolean successful in loading all passengers or not. Not currently used
	 */
	public boolean unloadPassengers(Elevator anElevator, int destFloor){
		//Boards a passenger, resets floors passengers if successful
		while(passengers > 0){
			try{
				anElevator.boardPassenger(destFloor);
			} catch(ElevatorFullException e){
				//Elevator is full. Floor holds any remaining passengers
				//and waits for elevator to return
				System.out.println("Elevator is full! Leaving "+String.valueOf(passengers)+" behind");
				//Registers request so that the elevator will be sure to stop there next time
				anElevator.registerRequest(floorNumber);
				//Lets the door close, elevator continues
				return false;
			}
			//On success...
			passengers--;
			System.out.println("Passenger boarding elevator. There are "+String.valueOf(anElevator.passengers)+" passengers on the elevator and "+String.valueOf(passengers)+" on floor");
		}
		//Passengers should be equal to zero at this point
		//Elevator no longer needs to stop on this floor
		//Lets the door close, elevator continues
		return true;
	}
	
	/**
	 * represents a passenger walking up the elevator and wanting to board
	 * @param anElevator the elevator object the passenger is being loaded onto
	 */
	public void addPassenger(Elevator anElevator){
		passengers++;
		anElevator.registerRequest(floorNumber);
	}
}
