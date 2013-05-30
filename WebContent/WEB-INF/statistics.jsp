<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="title" scope="request">Statistics</c:set>
<c:set var="script" scope="request">
<script>
var page = 0;

function getGrades(page) {
	$.get("statistics_AJAX", {page: page}, refresh);
}
	
function refresh(results) {
	var contents = "";
	
	//iterate through both lists and build table
	for(var i = 0; i < results.assignments.length; i++) {
		contents += "<tr><td>" + results.assignments[i].name + "</td>";
		contents += "<td>" + results.assignments[i].dueDate + "</td>";
		contents +=	"<td>" + results.assignments[i].mean + "</td>";
		contents += "<td>" + results.assignments[i].high + "</td>";
		contents += "<td>" + results.assignments[i].low + "</td></tr>";
	}
	
	$('#assignments').html(contents);
	
	
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
<table>
<thead>
<tr>
<th>Assignment Name</th>
<th>Due Date</th>
<th>Mean</th>
<th>High</th>
<th>Low</th>
</tr>
</thead>
<tbody id="assignments">
<c:forEach var="assignment" items="${assignments.results}">
<tr>
<td>${assignment.name}</td>
<td>${assignment.dueDate}</td>
<td>${assignment.mean}</td>
<td>${assignment.high}</td>
<td>${assignment.low}</td>
</tr>
</c:forEach>
</tbody>
</table>
<p>
<input type="button" value="Previous" id="previous" onclick="prev();"
<c:if test="${assignments.isBeginning}">disabled</c:if>/>
<input type="button" value="Next" id="next" onclick="next();"
<c:if test="${assignments.isEnd}">disabled</c:if>/>
</p>
<jsp:include page="footer.jsp" />