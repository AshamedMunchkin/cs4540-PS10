/**
 * 
 */
package ps10.school;

/**
 * @author Oscar
 *
 */
public class User {
	
	protected final String username;
	protected final String name;
	
	/**
	 * @param username
	 * @param name
	 */
	public User(String username, String name) {
		super();
		this.username = username;
		this.name = name;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
