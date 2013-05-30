package ps10.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;

import ps10.school.School;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Implements a new JSP tag for displaying a login/logout link.  It looks like 
 * this in practice:  <gsc:login/>
 * See also /WEB-INF/cart.tld, which is the configuration file.
 */
public class MenuTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	// When a tag is encountered, this is used to create a CartTag object
	public MenuTag() {
	}

	
	// This is called to actually interpret the tag.  Its job is to output the proper HTML.
	public int doStartTag() throws javax.servlet.jsp.JspException {	
		
		try {
			
			// Get writer and request
			JspWriter out = pageContext.getOut();
			HttpServletRequest request =
					(HttpServletRequest) pageContext.getRequest();
			
			LinkedHashMap<String, String> left =
					new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> right =
					new LinkedHashMap<String, String>();
			
			left.put("Statistics", "statistics");
			
			if (request.getUserPrincipal() != null) {
				if (request.isUserInRole("teacher")) {
					left.put("Grades", "teacher");
				}
				if (request.isUserInRole("student")) {
					left.put("Grades", "student");
				}
				School school = new School();
				right.put(
						school.getStudentFromUsername(request.getRemoteUser())
						.getName(),
						"");
				school.close();
				right.put("Logout", "logout");
			} else {
				right.put("Login", "login");
				right.put("Register", "register");
			}
			

			out.println("<nav>");
			out.println("<ul>");
			Iterator<Map.Entry<String, String>> iterator =
					left.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry =
						(Map.Entry<String, String>)iterator.next();
				out.println("<li>");
				out.println("<a href=\"" + entry.getValue() + "\">" +
				entry.getKey() + "</a>");
				out.println("</li>");
			}
			out.println("</ul>");
			
			out.println("<ul>");
			iterator = right.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry =
						(Map.Entry<String, String>)iterator.next();
				out.println("<li>");
				if (entry.getValue().isEmpty()) {
					out.println(entry.getKey());
				} else {
					out.println("<a href=\"" + entry.getValue() + "\">" +
					entry.getKey() + "</a>");
				}
				out.println("</li>");
			}
			out.println("</ul>");
			out.println("</nav>");
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	return Tag.SKIP_BODY;
  	}
	
}