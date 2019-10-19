<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit product</title>
</head>
<body>
<h3>Edit product</h3>
<form method="post" action="EditEmployee">

<input type="hidden" value="${employee.id}" name="e_id" />
<input type="hidden" value="${employee.d_id}" name="d_id" />
	<label>Mail</label><br>
	<input name="mail" value="${employee.mail}"/><br>
	<label>Name</label><br>
	<input name="name" value="${employee.name}"/><br>
	<label>Phone</label><br>
	<input name="phone" value="${employee.phone}"/><br>
	<label>Birthday</label><br>
	<input name="birthday" type="date" value="${employee.birthday}"/><br>
	
	<c:if test="${not empty error}">
    	<label>${error}</label><br>
	</c:if>
	
<input type="submit" value="Send" />
</form>
<form method="get" action='<c:url value="/employees" />'>
<input type="hidden" name="d_id" value="${employee.d_id}">
<input type="submit" value="Cancel">
</form>
</body>
</html>