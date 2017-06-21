import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * System class with associated depots
 * Also contains a main entry point for the campervan booking system
 */
public class VanRentalSystem {
	private ArrayList<Depot> depots= new ArrayList<Depot>();

	/**
	 * Entry point for the booking system
	 * Splits string from an input file and routes them accordingly to the
	 * booking system's methods
	 * @param args command line argument
	 * @throws FileNotFoundException, IOException if fails to read given input file
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		VanRentalSystem CoreSystem = new VanRentalSystem();
		
		try(BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
				// match the string using split
				String[] command=line.split("\\s");
				if (command[0].equals("Location")){
					CoreSystem.addDepotAndCampervan(command, CoreSystem);
				}
				
				else if(command[0].equals("Request")){
					String output = "Booking ";
					if(CoreSystem.requestBooking(command, CoreSystem, output)==false){
						System.out.println("Booking rejected");
					}
				}
				
				else if(command[0].equals("Change")){
					String output = "Change ";
					if(CoreSystem.changeBooking(command, CoreSystem, output)==false){
						System.out.println("Change rejected");
					}
					
				}
				
				else if(command[0].equals("Cancel")){
					if(CoreSystem.cancelBooking(Integer.parseInt(command[1]), CoreSystem)==true){
						System.out.println("Cancel "+Integer.parseInt(command[1]));
					} else {
						System.out.println("Cancel rejected");
					}
				}
				
				else if (command[0].equals("Print")){
					CoreSystem.printBookings(command, CoreSystem);
				}
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		}
	}
	
	/**
	 * Gets the list of depots for system
	 */
	public ArrayList<Depot> getDepots() {
		return depots;
	}
	
	/**
	 * Adds a new depot to the list of depots
	 * @param d new depot
	 */
	public void addDepot(Depot d){
		this.depots.add(d);
	}
	
	/**
	 * Checks availability in depot for each campervans
	 * @param CoreSystem
	 * @param freeCampervans, arraylist of free campervans, initially it is empty
	 * @param numAuto, number of automatic system requesets
	 * @param numManual, number of manual system requesets
	 * @param startDateBooking
	 * @param endDateBooking
	 * @return arraylist of free campervans
	 */
	public ArrayList<Campervan> checkAvailability(VanRentalSystem CoreSystem, ArrayList<Campervan> freeCampervans, int numAuto, int numManual, Date startDateBooking, Date endDateBooking) {	
		for (Depot d:CoreSystem.getDepots()){
    		for(Campervan c:d.getCampervans()){
    			
    			// if the campervan has no booking, it implies that it is available 
    			if(c.getBookings().isEmpty()) {
    				if(c.getType().equals("Automatic")&& numAuto>0){
    					numAuto--;
    					freeCampervans.add(c);
    				}
    				if(c.getType().equals("Manual")&& numManual>0){
    					numManual--;
    					freeCampervans.add(c);
    				}
    			} else {
    				
    				// boolean flag to compare date
    				boolean flag = true;
    				for (Booking b:c.getBookings()){
    					if(!endDateBooking.before(b.getStartDate())&&!startDateBooking.after(b.getEndDate())){
    						flag=false;
    					}
    				}
    				if(flag == true&&c.getType().equals("Automatic")&& numAuto>0){
    					numAuto--;
    					freeCampervans.add(c);
    				}
    				if(flag == true&&c.getType().equals("Manual")&& numManual>0){
    					numManual--;
    					freeCampervans.add(c);
    				}
    			}
    		}
    	}
    	return freeCampervans;
	}

	/**
	 * Adds depot and campervan accordingly,
	 * using the command for example "Location CBD Wicked Automatic"
	 * @param command array of input string 
	 * @param CoreSystem
	 */
	public void addDepotAndCampervan(String[] command, VanRentalSystem CoreSystem){
		Campervan c = new Campervan(command[2],command[3]);
		Depot result = null;
		boolean found = false;
		for (Depot d:CoreSystem.getDepots()){
			if(command[1].equals(d.getName())){
				result = d;
				found = true;
				break;
			}
		}
		
		// if depot is not found then we create a new one
		if(found==false){
			result = new Depot(command[1]);
			CoreSystem.addDepot(result);
		}
		result.addCampervan(c);
		
	}
	
	/**
	 * Requests a booking using the command for example,
	 * "Request 1 23 Mar 25 12 Mar 26 3 Automatic 1 Manual"
	 * @param command array of input string, each request can ask for vehicles of a given type only once
	 * @param CoreSystem
	 * @param output which is a string contains booking info
	 * @return boolean type request status
	 */
	public boolean requestBooking(String[] command, VanRentalSystem CoreSystem, String output){
		SimpleDateFormat format = new SimpleDateFormat("HH MMM dd yyyy");
		String startDateString = command[2] + " " + command[3] + " " + command[4] + " 2017";
		String endDateString = command[5] + " " + command[6] + " " + command[7] + " 2017";
	    try {
	    	Date startDateBooking = format.parse(startDateString);
	    	Date endDateBooking = format.parse(endDateString);
	    	int numAuto = 0;
	    	int numManual = 0;
	    	if (command[9].equals("Automatic")){
	    		numAuto = Integer.parseInt(command[8]);
	    	} else if (command[9].equals("Manual")){
	    		numManual = Integer.parseInt(command[8]);
	    	}
	    	
	    	// if command length is greater than 10 which means two types of campervans are requested
	    	if (command.length>10){
	    		if (command[11].equals("Automatic")){
		    		numAuto = Integer.parseInt(command[10]);
		    	} else if (command[11].equals("Manual")){
		    		numManual = Integer.parseInt(command[10]);
		    	}
	    	}
	    	
	    	// create an arraylist to store all the available campervans
	    	ArrayList<Campervan> freeCampervans= new ArrayList<Campervan>();
	    	int total = numAuto + numManual;
	    	freeCampervans = CoreSystem.checkAvailability(CoreSystem, freeCampervans, numAuto, numManual, startDateBooking, endDateBooking);
	    	
	    	// make booked array empty first in case it has been used previously
	    	for(Depot d:CoreSystem.depots){
				d.getBookedCampervans().clear();
			}
	    	// if availability meets the demand, request succeeds, now we can make the booking
	    	if (total == freeCampervans.size()){
	    		Booking b = new Booking(Integer.parseInt(command[1]),startDateBooking,endDateBooking);
	    		for(Campervan fc:freeCampervans){
	    			for(Depot d:CoreSystem.depots){
	    				if(d.getCampervans().contains(fc)){
	    					d.addBookedCampervan(fc);
	    				}
	    			}

	    		}
	    		
	    		// use concatenation to print the output
	    		output = output.concat(command[1] +" ");
	    		for(Depot d:CoreSystem.depots){
    				if(!d.getBookedCampervans().isEmpty()){
    					output = output.concat(d.getName() + " ");
    					for(Campervan bc:d.getBookedCampervans()){
    						output = output.concat(bc.getName());
    						if(d.getBookedCampervans().indexOf(bc)!=d.getBookedCampervans().size()-1){
    							output = output.concat(", ");
    						}
    						bc.addBookings(b);
    					}
    					if(CoreSystem.depots.indexOf(d)!=CoreSystem.depots.size()-1){
    						output = output.concat("; ");
						}
    				}
    			}
	    		
	    		// semi-colon should be omitted at the last
	    		if (output.endsWith("; ")){
	    			output = output.substring(0, output.length()-2);
	    		}
	    		System.out.println(output);
	    		return true;
	    	}     	
	    	
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Change an existing booking using the command for example
	 * "Change 1 23 Mar 27 23 Mar 29 3 Manual 2 Automatic"
	 * Make a duplicate of booking first then try to cancel and request
	 * if either cancel or request fails, old booking is kept
	 * @param command array of input string 
	 * @param CoreSystem
	 * @param output which is a string contains the new booking info
	 * @return boolean type change status
	 */
	public boolean changeBooking(String[] command, VanRentalSystem CoreSystem, String output){
		for (Depot d:CoreSystem.getDepots()){
			for (Campervan c:d.getCampervans()){
				c.copyBooking(Integer.parseInt(command[1]));
			}
		}
		if(CoreSystem.cancelBooking(Integer.parseInt(command[1]), CoreSystem)==true){
			if(CoreSystem.requestBooking(command, CoreSystem, output)==false){
				for (Depot d:CoreSystem.getDepots()){
					for (Campervan c:d.getCampervans()){
						c.recoverBooking();
					}
				}
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Deassigns bookings with a specific id
	 * @param id that needs to be canceled
	 * @param CoreSystem
	 * @return boolean type to identify cancellation status
	 */
	public boolean cancelBooking(int cancelId, VanRentalSystem CoreSystem){
		boolean canceled = false;
		for(Depot d:CoreSystem.getDepots()){
			canceled = d.cancelBookingInDepot(cancelId,canceled);
		}
		return canceled;
	}
	
	/**
	 * Prints all bookings made in the specific depot
	 * @param command array of input string 
	 * @param CoreSystem
	 */
	public void printBookings(String[] command, VanRentalSystem CoreSystem){
		Depot result = null;
		for (Depot d:CoreSystem.getDepots()){
			if(command[1].equals(d.getName())){
				result = d;
				break;
			}
		}
		if (result != null){
			result.printDepot();
		}
	}

}
