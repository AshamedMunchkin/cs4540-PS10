package ps10.tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.http.*;


/**
 * Implements a new JSP tag for displaying a ShoppingCart.  It looks like this in practice:
 * <gsc:cart cart="cart goes here" cookies="array of CookieType goes here" />
 * See also /WEB-INF/cart.tld, which is the configuration file.
 */
public class RoleTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	
	private String role;
	
	// When a tag is encountered, this is used to create a CartTag object
	public RoleTag () {
	}
	
	
	public String getRole() {
		return role;
	}

	// The setters are used to set the fields of the object when a tag is encountered. 
	public void setRole(String role) {
		this.role = role;
	}

	// This is called to actually interpret the tag.  Its job is to output the proper HTML.
	public int doStartTag() throws javax.servlet.jsp.JspException {	
		HttpServletRequest req = 
				(HttpServletRequest) pageContext.getRequest();
		if (req.isUserInRole(role)) {
			return Tag.EVAL_BODY_INCLUDE;
		}
		else {
			return Tag.SKIP_BODY;
		}
  	}
	
}