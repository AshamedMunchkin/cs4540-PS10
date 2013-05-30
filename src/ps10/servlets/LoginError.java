package ps10.servlets;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login-error")
public class LoginError extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Defers to getPost
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Called if there is an authentication problem with an automatically
	 * triggered. Sets an error message and tries again.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LinkedList<String> errors = new LinkedList<String>();
		errors.add("Authentication failed. Please try again.");
		request.setAttribute("errors", errors);
		request.getRequestDispatcher("/WEB-INF/login.jsp")
		.forward(request, response);
	}

}
