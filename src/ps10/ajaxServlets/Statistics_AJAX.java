package ps10.ajaxServlets;

import java.io.IOException;
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
 * Servlet implementation class Statistics_AJAX
 */
@WebServlet("/statistics_AJAX")
public class Statistics_AJAX extends HttpServlet {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 0;
		if(request.getParameterMap().containsKey("page"))
			page = Integer.parseInt(request.getParameter("page"));
		
		JSONObject result = new JSONObject();
		
		JSONArray assignments = new JSONArray();
		ResultPage<Assignment> results;
		try {
			School school = new School();
			results = school.getAssignments(page);
			school.close();
			
			for (Assignment assignment : results.getResults()) {
				assignments.put(assignment.toJSON());
			}
			
			result.put("assignments", assignments);
			result.put("pageNumber", results.getPageNumber());
			result.put("isBeginning", results.getIsBeginning());
			result.put("isEnd", results.getIsEnd());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Send back the result as an HTTP response
		response.setContentType("application/json");
		response.getWriter().print(result);
		response.getWriter().close();
		
	}

}
