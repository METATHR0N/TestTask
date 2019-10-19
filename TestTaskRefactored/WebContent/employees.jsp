<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<p><a href='<c:url value="/CreateEmployee?d_id=${d_id}" />'>New Employee</a></p>
<table border=1>
	<c:if test="${not empty employees}">
    <tr>
 		<td>Mail</td>
 		<td>Name</td>
 		<td>Phone</td>
 		<td>Birthday</td>
 	<tr/>
 	<c:forEach var="employee" items="${employees}">
 	<tr>
 		<td>${employee.mail}</td>
 		<td>${employee.name}</td>
 		<td>${employee.phone}</td>
 		<td>${employee.birthday}</td>
 		<td>
 			 <form method="get" action='<c:url value="/EditEmployee" />'>
        		<input type="hidden" name="id" value="${employee.id}">
        		<input type="submit" value="Edit">
    		</form>
    	<td/>
    	<td>
 			<form method="post" action='<c:url value="/DeleteEmployee" />'>
 				<input type="hidden" name="d_id" value="${d_id}">
        		<input type="hidden" name="e_id" value="${employee.id}">
        		<input type="submit" value="Delete">
    		</form>
    	</td>
    </tr>
	</c:forEach>
	</c:if>
</table>
	<form method="get" action='<c:url value="/Index" />'>
       	<input type="submit" value="Back">
   	</form>
</body>
</html>