
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Results 2</title>
</head>
<body>
    <h2>Search Results 2</h2>
    <c:forEach var="film" items="${films}">
        <p>${film.title}</p>
 
    </c:forEach>
</body>
</html>
