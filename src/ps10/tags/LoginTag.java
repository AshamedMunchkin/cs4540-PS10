package ps10.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.IOException;


/**
 * Implements a new JSP tag for displaying a login/logout link.  It looks like 
 * this in practice:  <gsc:login/>
 * See also /WEB-INF/cart.tld, which is the configuration file.
 */
public class LoginTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	// When a tag is encountered, this is used to create a CartTag object
	public LoginTag () {
	}

	
	// This is called to actually interpret the tag.  Its job is to output the proper HTML.
	public int doStartTag() throws javax.servlet.jsp.JspException {	
		
		try {
			
			// Get writer and request
			JspWriter out = pageContext.getOut();
			HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();

			// Output link	
			if (req.getRemoteUser() != null) {
				out.println("<a href='logout'>Logout</a>");
			}
			else {
				out.println("<a href='login'>Login</a>");
			}
		
		}
		catch (IOException e) {
		}
    	return Tag.SKIP_BODY;
  	}
	
}
