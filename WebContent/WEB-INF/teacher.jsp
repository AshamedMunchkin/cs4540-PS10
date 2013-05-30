<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ps10" uri="/WEB-INF/ps10.tld"%>
<c:set var="title" scope="request">Grades</c:set>
<c:set var="script" scope="request">
<script>
var activeMenu = null; // Combo menu that is currently active (or null)
var isOverMenu = false; // Is the mouse over the menu?
var students;
var currentStudent;
var page;
		
// Called when the mouse button is pressed anywhere in the document.
// If the press happened anywhere other than in the active menu, hide
// the menu.
function mouseSelect (e) {
	if (activeMenu != null && !isOverMenu) {
		document.getElementById(activeMenu).style.display = "none";
		activeMenu = null;
	}
	return true;
}

// Register the function above as an event handler
document.onmousedown = mouseSelect;

// Make a menu whose ID is menuID visible if it isn't
// already. Align it with the element whose ID is editID.
function menuActivate (editID, menuID) {
	// If menu already active, nothing to do
	if (activeMenu == menuID) return;
	activeMenu = menuID;
	// Line up menuID with editID
	var oMenu = document.getElementById(menuID);
	var oEdit = document.getElementById(editID);
	var nTop = oEdit.offsetTop + oEdit.offsetHeight;
	var nLeft = oEdit.offsetLeft;
	while (oEdit.offsetParent != document.body) {
	oEdit = oEdit.offsetParent;
	nTop += oEdit.offsetTop;
	nLeft += oEdit.offsetLeft;
}
	oMenu.style.left = nLeft;
	oMenu.style.top = nTop;
	oMenu.style.display = "";
	// Make the menu visible
	document.getElementById(menuID).style.visibility = 'visible';
}

// Set the text box with ID editID, make the menu disappear,
// and give the text box the focus.
function textSet (editID, text, value) {
	document.getElementById(editID).value = text;
	isOverMenu = false;
	mouseSelect(0);
	document.getElementById(editID).focus();
	$.get('student_AJAX', {name: value}, showGrades);
	currentStudent = value;
	page = 0;
}

function showGrades(results) {
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
	} 
	if (results.isBeginning) {
		$('#previous').attr('disabled', 'disabled');
	} else {
		$('#previous').removeAttr('disabled');
	}
}

// Obtain up the students that begin with the prefix.
function refresh (text) {
	if($('#combodiv'))
		$.get('teacher_AJAX', {prefix: text}, showChoices);
}

// Show up to ten choices in a menu
function showChoices (result) {
	 // Obtain the array of choices
	students = result.students;
	// Empty out the selection menu
	while (document.getElementById('combosel').length > 0) {
		document.getElementById('combosel').remove(0);
	}
	// Repopulate the selection menu
	var i = 0;
	while (i < students.length) {
		var elOptNew = document.createElement('option');
		elOptNew.text = students[i]['name'];
		elOptNew.value = students[i]['username'];
		try {
			document.getElementById('combosel').add(elOptNew,null);
		}
		catch (e) {
			document.getElementById('combosel').add(elOptNew);
		}
		i++;
	}
}

//Go to next batch
function next () {
	$.get('student_AJAX', {name: currentStudent, page: ++page}, showGrades);
}

// Go to previous batch
function prev () {
	$.get('student_AJAX', {name: currentStudent, page: --page}, showGrades);
}
</script>
</c:set>
<jsp:include page="header.jsp" />
Enter Student:
<br/>
<input type="text"
id="combotext" style="width:200"
onkeyup="menuActivate('combotext', 'combodiv'); refresh(this.value);"/>
<div id="combodiv" onmouseover="isOverMenu=true;" onmouseout="isOverMenu=false;">
<select size="10"
id="combosel"
style="width:200px"
onchange="textSet('combotext', this.options[this.selectedIndex].text, this.value); refresh(this.value);">
</select>
</div>
<table id="gradeTable"></table>
<p><input type="button" value="Previous" id="previous" onclick="prev();"/>
	<input type="button" value="Next" id="next" onclick="next();"/>
</p> 
<jsp:include page="footer.jsp" />