
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Search Results 2</title>
</head>
<body>
	<h2>Search Results 2</h2>
	<c:forEach var="film" items="${films}">
		<p>${film.title}</p>
	</c:forEach>
	<ul>
		<c:forEach var="actor" items="${film.actors}">
			<li>${actor.firstName}${actor.lastName}</li>
		</c:forEach>
	</ul>

</body>
</html>





