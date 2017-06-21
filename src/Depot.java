import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Depot class with its name, associated campervans and booked campervans
 */
public class Depot {
	private String name;
	private ArrayList<Campervan> campervans = new ArrayList<Campervan>();
	private ArrayList<Campervan> bookedCampervans = new ArrayList<Campervan>();
	
	/**
	 * constructor that takes name to create a new depot
	 */
	public Depot(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of depot
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the list of campervans for the specific depot
	 */
	public ArrayList<Campervan> getCampervans() {
		return campervans;
	}
	
	/**
	 * Gets the list of booked campervans for the specific depot
	 */
	public ArrayList<Campervan> getBookedCampervans() {
		return bookedCampervans;
	}
	
	/**
	 * Adds a new campervan to the list of campervans
	 * @param c new campervan
	 */
	public void addCampervan(Campervan c){
		campervans.add(c);
	}
	
	/**
	 * Adds a booked campervan to the list of booked campervans
	 * @param c booked campervan
	 */
	public void addBookedCampervan(Campervan c){
		bookedCampervans.add(c);
	}
	
	/**
	 * Prints bookings in order of campervan declarations, then date/time of each booking
	 * That being said, sort the booking based date before output
	 */
	public void printDepot(){
		for(Campervan c:campervans) {
			if(!c.getBookings().isEmpty()){
				c.sortBookings();
				for(Booking b:c.getBookings()){
					// 11:00 Mar 25 09:00 Mar 26
					String date1 = new SimpleDateFormat("HH:mm MMM dd").format(b.getStartDate());
					String date2 = new SimpleDateFormat("HH:mm MMM dd").format(b.getEndDate());
					System.out.println(this.name + " " + c.getName()+" "+date1 +" "+ date2);
				}
			}
			
        }
	}

	/**
	 * Deassigns bookings in a depot for each campervans 
	 * @param cancelId that needs to be canceled
	 * @param canceled, cancellation status before checking the depot
	 * @return boolean type to identify cancellation status
	 */
	public boolean cancelBookingInDepot(int cancelId, boolean canceled){	
		for(Campervan c:this.getCampervans()){
			for (Iterator<Booking> iterator = c.getBookings().iterator(); iterator.hasNext(); ) {
			    if(c.getBookings().isEmpty())	break;
				Booking b = iterator.next();					
				if(b.getId()==cancelId){
					canceled = true;
					int index=c.getBookings().indexOf(b);
					c.getBookings().remove(index);
					break;
				}
			}
		}
		return canceled;
	}
	
}
