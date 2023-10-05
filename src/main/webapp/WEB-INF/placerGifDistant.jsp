<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Placer un gif distant</title>
<link href="/style/${sessionScope.utilisateur.theme.nom.toLowerCase()}.css" rel="stylesheet">
</head>
<body>
<h1>Placer un gif distant sur le ${gifDistant.jour.date} (nombre de points: ${gifDistant.jour.nbPoints})</h1>
<form:form modelAttribute="gifDistant" action="placerGifDistant" method="post">
<form:label path="url">URL</form:label><form:input path="url" placeHolder="http://clelia.fr/images/1.gif"/>
<form:errors path="url" cssClass="erreur"/>
<form:hidden path="jour.date"/><br>
<form:label path="legende">Légende</form:label><form:input path="legende"/><br>
<form:button>Placer</form:button>
</form:form>
</body>
</html>