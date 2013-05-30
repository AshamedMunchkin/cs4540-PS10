<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ps10" uri="/WEB-INF/ps10.tld"%>
<!doctype html>
<html>
<head>
<meta charset="ISO-8859-1">
<title><c:if test="${not empty title}">${title} - </c:if>PS10</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" >
</script> 
<c:if test="${not empty script}">${script}</c:if>
</head>
<body>
<header>
<ps10:menu />
</header>
<div>
<c:if test="${not empty successes}">
<c:forEach var="success" items="${successes}">
${success}
</c:forEach>
</c:if>
</div>
<div>
<c:if test="${not empty errors}">
<c:forEach var="error" items="${errors}">
${error}
</c:forEach>
</c:if>
</div>
<h2>${title}</h2>