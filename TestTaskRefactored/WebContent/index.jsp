<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<p><a href='<c:url value="/Create" />'>Create new</a></p>
<table border=1>
	<c:forEach var="department" items="${departments}">
 	<tr>
 		<td><a href='<c:url value="/employees?d_id=${department.id}" />'>${department.name}</a></td>
 		<td>
 			 <form method="get" action='<c:url value="/Edit" />'>
        		<input type="hidden" name="id" value="${department.id}">
        		<input type="hidden" name="name" value="${department.name}" />
        		<input type="submit" value="Edit">
    		</form>
    	<td/>
    	<td>
 			<form method="post" action='<c:url value="/Delete" />'>
        		<input type="hidden" name="id" value="${department.id}">
        		<input type="submit" value="Delete">
    		</form>
    	</td>
    </tr>
</c:forEach>
</table>
</body>
</html>