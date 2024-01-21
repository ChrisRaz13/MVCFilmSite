<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FilmLookUp</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

ul {
	list-style-type: none;
	padding: 0;
}

li {
	margin-bottom: 10px;
}

p {
	font-style: italic;
	color: #555;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${! empty film}">
			<h1>Film Details</h1>
			<ul>
				<li><strong>ID:</strong> ${film.id}</li>
				<li><strong>Title:</strong> ${film.title}</li>
				<li><strong>Description:</strong> ${film.description}</li>
				<li><strong>Rating:</strong> ${film.rating}</li>
				<li><strong>Language:</strong> ${film.language}</li>
				<li><strong>Actors:</strong>
					<ul>
						<c:forEach var="actor" items="${actors}">
							<li>${actor.firstName} ${actor.lastName}</li>
						</c:forEach>
					</ul></li>
			</ul>
		</c:when>
		<c:otherwise>
			<p>No film found</p>
		</c:otherwise>
	</c:choose>
</body>
</html>
