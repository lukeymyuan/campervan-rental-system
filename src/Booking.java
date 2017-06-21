import java.util.Date;

/**
 * Booking class with its id, start date and end date
 */
public class Booking{
	private int id;
	private Date startDate;
	private Date endDate;
	
	/**
	 * constructor that takes id, start date and end date to create a new booking
	 */
	public Booking(int id, Date startDate, Date endDate) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Gets the start date of booking 
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * Gets the end date of booking 
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * Gets the id of booking 
	 */
	public int getId() {
		return id;
	}
	
}
