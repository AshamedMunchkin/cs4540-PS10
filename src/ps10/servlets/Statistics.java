package ps10.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ps10.school.Assignment;
import ps10.school.School;

/**
 * Servlet implementation class Statistics
 */
@WebServlet("/statistics")
public class Statistics extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// List of error messages to display.
		LinkedList<String> errors = new LinkedList<String>();
		
		try {
			School school = new School();
			
			request.setAttribute("assignments", school.getAssignments(0));
			
			school.close();
		} catch (SQLException e) {
			// Add the exception message to errors and set it in the request.
			errors.add(e.getMessage());
			request.setAttribute("errors", errors);
			
			// Print the stack for debugging purposes.
			e.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/statistics.jsp")
		.forward(request, response);
	}

}
