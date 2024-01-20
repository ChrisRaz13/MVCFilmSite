<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>States</title>
</head>
<body>
<c:choose>
    <c:when test="${! empty film}">
        <ul>
            <li>${film.id}</li>
            <li>${film.title}</li>
            <li>${film.description}</li>
                <c:forEach var="actor" items="${film.actors}">
                    <li>${actor.firstName} ${actor.lastName}</li>
                </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
        <p>No film found</p>
    </c:otherwise>
</c:choose>

</body>
</html>