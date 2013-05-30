<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" scope="request">Bad Role</c:set>
<jsp:include page="header.jsp" />

<p style="color:red">You are not authorized to visit this page!</p>

<jsp:include page="footer.jsp" />
