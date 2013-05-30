<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ps10" uri="/WEB-INF/ps10.tld"%>
<c:set var="title" scope="request">Login</c:set>
<jsp:include page="header.jsp" />

<form action="j_security_check" method="post">
<table>
 <tr><td>User name: <input type="text" name="j_username"></td><td>${message}</tr>
 <tr><td>Password: <input type="password" name="j_password"></td>
 <tr><th><input type="submit" value="Log In"></th></tr>
</table> 
</form>

<jsp:include page="footer.jsp" />