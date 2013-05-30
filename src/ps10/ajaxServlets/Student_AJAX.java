package ps10.ajaxServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import ps10.school.Assignment;
import ps10.school.ResultPage;
import ps10.school.School;

/**
 * Servlet implementation class Student_AJAX
 */
@WebServlet("/student_AJAX")
public class Student_AJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Student_AJAX() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		int page = 0;
		 
		if(req.getParameterMap().containsKey("page"))
			page = Integer.parseInt(req.getParameter("page"));
		
		
		School school;
		ResultPage<Assignment> resultset = null;	
		Map<Integer, Integer> studentGrades = null;
		String username;
		if(req.getParameterMap().containsKey("name"))
			username = req.getParameter("name");
		else
			username = req.getRemoteUser();
		
		try {
			school = new School();
			
			studentGrades = school.getStudentGrades(username);
			resultset = school.getAssignments(page);

			school.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JSONArray studentAssignments = new JSONArray();
		try {
			for(Assignment assignment : resultset.getResults()) {
				studentAssignments.put(assignment.getName());
				if(studentGrades.get(assignment.getId()) == null)
					studentAssignments.put("-");
				else
					studentAssignments.put(studentGrades.get(assignment.getId()).toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
				
		JSONObject result = new JSONObject();
		try {
			result.put("assignments", studentAssignments);
			result.put("isBeginning", resultset.getIsBeginning());
			result.put("isEnd", resultset.getIsEnd());
			result.put("page", resultset.getPageNumber());
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
