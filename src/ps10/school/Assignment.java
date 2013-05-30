/**
 * 
 */
package ps10.school;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Oscar
 *
 */
public class Assignment {
	
	private final int id;
	private final String name;
	private final Date dueDate;
	private final int mean;
	private final int low;
	private final int high;
	
	/**
	 * @param id
	 * @param name
	 * @param mean
	 * @param low
	 * @param high
	 */
	public Assignment(int id, String name, Date dueDate, int mean, int low,
			int high) {
		super();
		this.id = id;
		this.name = name;
		this.dueDate = dueDate;
		this.mean = mean;
		this.low = low;
		this.high = high;
	}
	
	public JSONObject toJSON() {
		JSONObject result = new JSONObject();
		try {
			result.put("id", id);
			result.put("name", name);
			result.put("dueDate", dueDate);
			result.put("mean", mean);
			result.put("low", low);
			result.put("high", high);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @return the mean
	 */
	public int getMean() {
		return mean;
	}

	/**
	 * @return the low
	 */
	public int getLow() {
		return low;
	}

	/**
	 * @return the high
	 */
	public int getHigh() {
		return high;
	}

}
