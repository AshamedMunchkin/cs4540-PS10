package ps10.school;

import org.json.JSONException;
import org.json.JSONObject;

public class Student extends User {
	
	private final int grade; 

	/**
	 * @param username
	 * @param name
	 * @param grade
	 */
	public Student(String username, String name, int grade) {
		super(username, name);
		this.grade = grade;
	}

	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("username", username);
		result.put("name", name);
		result.put("grade", 0);
		
		return result;
	}

}
