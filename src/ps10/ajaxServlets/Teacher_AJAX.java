package ps10.ajaxServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import ps10.school.School;
import ps10.school.Student;

/**
 * Servlet implementation class Teacher_AJAX
 */
@WebServlet("/teacher_AJAX")
public class Teacher_AJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse rsp) 
			throws ServletException, IOException {
		
		// Turn off caching and grab the incoming prefix parameter
		//rsp.setHeader("Cache-Control", "no-cache");
		//rsp.setHeader("Pragma", "no-cache");
		String prefix = "";
		
		if(req.getParameterMap().containsKey("prefix"))
			prefix = req.getParameter("prefix");
		
		
		
		School school;
		List<Student> matchingStudents = null;
		
		try {
			school = new School();
			
			// Find the first ten matches for the prefix.  If there's an exact match
			matchingStudents = school.matchingStudents(prefix);

			school.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		JSONObject result = new JSONObject();
		JSONArray students = new JSONArray();
		
		try {
			for (Student student : matchingStudents) {
				students.put(student.toJSON());
			}
			result.put("students", students);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		// Send back the result as an HTTP response
		rsp.setContentType("application/json");
		rsp.getWriter().print(result);
		rsp.getWriter().close();
	}

}
