<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form:form modelAttribute="utilisateur" action="inscription" method="post">
	
	<form:label path="nom">Nom</form:label>
	<form:input path="nom"/>
	<form:errors path="nom" cssClass="erreur" /><br>
	
	<form:label path="prenom">Prénom</form:label>
	<form:input path="prenom"/>
	<form:errors path="prenom" cssClass="erreur" /><br>
	
	<form:label path="email">Email</form:label>
	<form:input path="email"/>
	<form:errors path="email" cssClass="erreur" /><br>
	
	<form:label path="motDePasse">Mot de passe</form:label>
	<form:password path="motDePasse"/>
	<form:errors path="motDePasse" cssClass="erreur" /><br>
	
	<form:label path="theme">Thème</form:label>
	<form:select path="theme">
		<form:option value="0">Merci de choisir un thème</form:option>
		<form:options items="${ themes }" itemValue="id" itemLabel="nom"/>
	</form:select>

<form:errors path="theme" cssClss="erreur" /><br>
<form:button>Inscription</form:button>
</form:form>

</body>
</html>