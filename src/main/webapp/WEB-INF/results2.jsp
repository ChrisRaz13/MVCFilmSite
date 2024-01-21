
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Search Results 2</title>
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
	<h2>Search Results 2</h2>
	<c:forEach var="film" items="${films}">
		<p>${film.title}</p>
	</c:forEach>
	<ul>
		<c:forEach var="actor" items="${actors}">
			<li>${actor.firstName} ${actor.lastName}</li>
		</c:forEach>
	</ul>

</body>
</html>





