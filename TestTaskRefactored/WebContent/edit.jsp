<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit product</title>
</head>
<body>
<h3>Edit product</h3>
<form method="post" action="Edit">
<input type="hidden" value="${department.id}" name="id" />
<label>Name</label><br>
<input name="name" value="${department.name}" /><br>
	<c:if test="${not empty error}">
    	<label>${error}</label><br>
	</c:if>
<input type="submit" value="Send" />
</form>
<form method="get" action='<c:url value="/Index" />'>
<input type="submit" value="Cancel" />
</form>
</body>
</html>