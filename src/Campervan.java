import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Campervan class with its name, type, and associated bookings
 */
public class Campervan {
	private String name;
	private String type;
	private ArrayList<Booking> bookings = new ArrayList<Booking>();
	private Booking backupBooking = null;
	
	/**
	 * constructor that takes name and type to create a new campervan
	 */
	public Campervan(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Gets the name of campervan
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the type of campervan
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the list of bookings for the specific campervan 
	 */
	public ArrayList<Booking> getBookings() {
		return bookings;
	}
	
	/**
	 * Adds a new booking to the list of bookings
	 * @param b new booking
	 */
	public void addBookings(Booking b){
		bookings.add(b);
	}
	
	/**
	 * Makes a duplicate of booking for a specific id 
	 * @param id of the booking that needs to be copied
	 */
	public void copyBooking(int id){
		for(Booking b:bookings){
			if(id == b.getId()){
				backupBooking = b;
				break;
			}
		}
	}
	
	/**
	 * Adds old booking back to arraylist, this method is called when change is rejected
	 */
	public void recoverBooking(){
		if(backupBooking != null){
			bookings.add(backupBooking);
		}
	}
	
	/**
	 * Sorts bookings based on booking date
	 */
	public void sortBookings() {
		Collections.sort(bookings, new BookingDateComparator());
	}
	
}

/**
 * Overrides the compare method to sort arraylist of object by property
 */
class BookingDateComparator implements Comparator<Booking> {
	@Override
	public int compare(Booking b1, Booking b2) {
		return b1.getStartDate().compareTo(b2.getStartDate());
    }
}
