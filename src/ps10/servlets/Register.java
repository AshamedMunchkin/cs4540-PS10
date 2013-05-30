package ps10.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ps10.school.School;

/**
 * Servlet implementation class Register
 */
@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		// Serve the page.
		request.getRequestDispatcher("/WEB-INF/register.jsp")
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		LinkedList<String> errors = new LinkedList<String>();

		// If this wasn't a submission, then just serve the page.
		if (request.getParameter("submit").isEmpty()) {
			request.getRequestDispatcher("/WEB-INF/register.jsp")
			.forward(request, response);
			return;
		}
		
		// Check to see if there are any errors.
		if (request.getParameter("username").isEmpty()) {
			errors.add("Please enter a username");
		}
		if (request.getParameter("password").isEmpty()) {
			errors.add("Please enter a password");
		}
		if (request.getParameter("name").isEmpty()) {
			errors.add("Please enter your name");
		}
		
		// If there are errors, set the list of errors as the "errors" attribute
		// in the request scope. Then return.
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/register.jsp")
			.forward(request, response);
			return;
		}
			
		// Try to register the user
		School school;
		try {
		school = new School();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		try {
			
			if (school.isUser(request.getParameter("username"))) {
				school.close();
				
				errors.add("Username is unavailable");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/WEB-INF/register.jsp")
				.forward(request, response);
				return;
			}
			
			school.registerUser(request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("name"));
			school.close();
			
			request.getRequestDispatcher("/WEB-INF/registered.jsp")
			.forward(request, response);
			return;
		}
		catch (SQLException e) {
			school.close();
			
			e.printStackTrace();
			
			errors.add(e.getMessage());
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/register.jsp")
			.forward(request, response);
			return;
		}
	}

}
