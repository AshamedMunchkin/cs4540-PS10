<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ps10" uri="/WEB-INF/ps10.tld"%>

<c:set var="title" scope="request">Grades</c:set>
<c:set var="script" scope="request">
<script>
	var page = 0;

	$('document').ready(function() {
		getGrades(0);
	});

	function getGrades(page) {
		$.get("student_AJAX", {page: page}, refresh);
	}
	
function refresh(results) {
	//iterate through both lists and build table
	var contents = "<tr><th>Assignment</th><th>Grade</th></tr>";
	for(var i = 0; i < results.assignments.length; i++) {
		contents += "<tr><td>" + results.assignments[i] + "</td>";
		i++;
		contents += "<td>" + results.assignments[i] + "</td></tr>";
	}
	
	$('#gradeTable').html(contents);
	
	
	if (results.isEnd) {
		$('#next').attr('disabled', 'disabled');
	} else {
		$('#next').removeAttr('disabled');
	} if (results.isBeginning) {
		$('#previous').attr('disabled', 'disabled');
	} else {
		$('#previous').removeAttr('disabled');
	}
}
	
// Go to next batch
function next () {
	getGrades(++page);
}

// Go to previous batch
function prev () {
	getGrades(--page);
} 
</script>
</c:set>
<jsp:include page="header.jsp" />
	<table border="1" id="gradeTable"></table>
<p><input type="button" value="Previous" id="previous" onclick="prev();"/>
	<input type="button" value="Next" id="next" onclick="next();"/>
</p>
<jsp:include page="footer.jsp" />