<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" scope="request">Register</c:set>
<jsp:include page="header.jsp" />

<p>Please use the form below to register for this site.</p>

<form method="post">

	<c:if test="${not empty errors}">
	<c:forEach var="error" items="${errors}">
	${error}
	</c:forEach>
	</c:if>

	<p>
		<label for="username">Username</label> 
		<input type="text" id="username" name="username" max=32
				value="${param.username}">
	</p>
	
	<p>
		<label for="password">Password</label>
		<input type="password" id="password" name="password">
	</p>

	<p>
		<label for="name">Name</label> 
		<input type="text" id="name" name="name" value="${param.name}">
	</p>

	<p>
		<button type="submit" name="submit" value="register">Register</button>
	</p>

</form>

<jsp:include page="footer.jsp" />