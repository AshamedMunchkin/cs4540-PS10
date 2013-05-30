package ps10.school;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class School {
	
	Connection connection;
	
	DateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public School() throws SQLException {
		try {
			Context initialContext = new InitialContext();
			Context envContext = 
					(Context) initialContext.lookup("java:comp/env");
			DataSource dataSource = 
					(DataSource) envContext.lookup("jdbc/ps10-omarshal");
			connection = dataSource.getConnection();
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes an open database connection.
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Whether a user exists with the given username.
	 * 
	 * @param username
	 * @return whether the user exists
	 * @throws SQLException 
	 */
	public boolean isUser(String username) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"SELECT * FROM Users WHERE username = ?");
		statement.setString(1, username);
		
		ResultSet resultSet = statement.executeQuery();
		
		boolean result = resultSet.next();
		
		resultSet.close();
		
		statement.close();
		
		return result;
	}
	
	/**
	 * Registers a user as a student.
	 * 
	 * @param username
	 * @param password
	 * @param name
	 * @throws SQLException 
	 * @throws UnsupportedEncodingException 
	 */
	public void registerUser(String username, String password, String name)
			throws SQLException {
		
		String hex = "0123456789abcdef";
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		byte[] digest = messageDigest.digest();
		StringBuilder hashedPassword = new StringBuilder();
		for (byte digestByte: digest) {
			int i1 = (int) ((digestByte >> 4) & 0xf);
			int i2 = (int) (digestByte & 0xf);
			hashedPassword.append(hex.charAt(i1)).append(hex.charAt(i2));
		}
		
		// Begin a transaction.
		connection.setAutoCommit(false);
		
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO Users VALUES (?, ?, ?)");
		statement.setString(1, username);
		statement.setString(2, hashedPassword.toString());
		statement.setString(3, name);
		
		statement.executeUpdate();
		
		statement.close();
		
		statement = connection.prepareStatement(
				"INSERT INTO Roles VALUES (?, ?)");
		statement.setString(1, username);
		statement.setString(2, "student");
		
		statement.executeUpdate();
		
		statement.close();
		
		connection.commit();
	}
	
	/**
	 * Get all of the grades for the specified student. The returned Map is a
	 * map from assignmentId to grade.
	 * @param student
	 * @return the students grades
	 * @throws SQLException 
	 */
	public Map<Integer, Integer> getStudentGrades(String student)
			throws SQLException {
		
		// HashMap from assignmentId to grade.
		Map<Integer, Integer> grades = new HashMap<Integer, Integer>();
		PreparedStatement statement = connection.prepareStatement(
				"SELECT assignmentId, grade FROM Grades " +
				"WHERE username = ?");
		statement.setString(1, student);
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			grades.put(result.getInt(1), result.getInt(2));
		}
		
		return grades;
	}
	
	
	/**
	 * Get a list of the first 10 students with the matching name.
	 * @param filter
	 * @return
	 * @throws SQLException 
	 */
	public List<Student> matchingStudents(String filter) throws SQLException {
		
		ArrayList<Student> matching = new ArrayList<Student>();

		PreparedStatement statement = connection.prepareStatement(
				"SELECT username, name " +
				"FROM Users NATURAL JOIN Roles " +
				"WHERE role = 'student' AND name LIKE ?");
		statement.setString(1, "%" + filter + "%");
		ResultSet result = statement.executeQuery();
		
		while (result.next()) {
			matching.add(new Student(result.getString(1), result.getString(2),
					0));
		}
		
		// Clean up
		result.close();
		statement.close();
		

		return matching;
	}
	
	public Student getStudentFromUsername(String username) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"SELECT username, name FROM Users WHERE username = ?");
		statement.setString(1, username);
		ResultSet result = statement.executeQuery();
		
		result.next();
		
		Student student = new Student(result.getString(1), result.getString(2),
				0);
		
		result.close();
		statement.close();
		
		return student;
	}
	
	/**
	 * Get all assignments ordered by due date. The specified page is a 0
	 * indexed page number (e.g. the first page is 0).
	 * @param page
	 * @return
	 * @throws SQLException 
	 */
	public ResultPage<Assignment> getAssignments(int page) throws SQLException {
		LinkedList<Assignment> assignments = new LinkedList<Assignment>();
		boolean beginning = false;
		boolean end = false;
		
		PreparedStatement statement = connection.prepareStatement(
				"SELECT id, name, dueDate, AVG(grade), MIN(grade), " +
				"MAX(grade) FROM Assignments LEFT OUTER JOIN Grades " +
				"ON id = assignmentId GROUP BY id, name, dueDate " +
				"ORDER BY dueDate ASC LIMIT 11 OFFSET ?");
		statement.setInt(1, page * 10);
		ResultSet result = statement.executeQuery();
		
		int i = 0;
		while (i++ < 10 && result.next()) {
			assignments.add(new Assignment(result.getInt(1),
					result.getString(2), result.getDate(3),
					result.getInt(4), result.getInt(5), result.getInt(6)));
		}
		
		beginning = page == 0;
		end = !result.next();
		
		// Clean up.
		result.close();
		statement.close();
		
		return new ResultPage<Assignment>(assignments, page, beginning, end);
	}
	
	public void addAssignment(String name, Date dueDate) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO Assignments (name, dueDate) VALUES (?, ?)");
		statement.setString(1, name);
		statement.setString(2, sqlDateFormat.format(dueDate));
		
		statement.executeUpdate();
		
		statement.close();
	}
	
	public void addGrade(String username, int assignmentId, int grade)
			throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO Grades VALUES (?, ?, ?)");
		statement.setString(1, username);
		statement.setInt(2, assignmentId);
		statement.setInt(3, grade);
		
		statement.executeUpdate();
		
		statement.close();
	}
	
	public void changeGrade(String username, int assignmentId, int grade)
			throws SQLException {
		PreparedStatement statement = connection.prepareStatement(
				"UPDATE Grades SET grade = ? " +
				"WHERE username = ? AND assignmentId = ?");
		statement.setInt(1, grade);
		statement.setString(2, username);
		statement.setInt(3, assignmentId);
		
		statement.executeUpdate();
		
		statement.close();
	}
	
}

