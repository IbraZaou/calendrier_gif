
<%
/* JSP: Java Server Page (1999) Une JSP débute par les directives */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendrier Gif</title>
<link href="style/theme1.css" rel="stylesheet">
</head>
<body>
	<h1>Calendrier Gif</h1>
	<c:if test="${param.notification ne null}">
		<h2>${param.notification}</h2>
	</c:if>
	<form action="login" method="post">
		<input type="text" name="username" placeHolder="username" required><br>
		<input type="password" name="password" placeHolder="Mot de Passe"
			required><br> <input type="submit" value="connexion">
	</form>
	<a href="inscription">S'inscrire</a>
	<h2>Utilisateurs ayant réagi au moins 5 fois</h2>
	<ul>
		<c:forEach items="${utilisateurs}" var="utilisateur">
			<li>${utilisateur.prenom}</li>
		</c:forEach>
	</ul>
	<h2>Nb d'inscrits par année et par mois</h2>
	<c:forEach items="${nbInscrits}" var="nbInscrit">
        ${nbInscrit.annee}/${nbInscrit.mois} : ${nbInscrit.nbInscrits}<br>
	</c:forEach>
	Nombre d'inscrits : ${nbTotalInscrits}
	<br>
	<%-- <jsp:include page="piedDePage.jsp"></jsp:include> --%>
</body>
</html>