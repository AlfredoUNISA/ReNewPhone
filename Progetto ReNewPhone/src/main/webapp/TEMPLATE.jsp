<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" /> 
	<title>Homepage</title>
</head>
<body>
	<%@ include file="_header.jsp" %>

	<div class="content">
		<h1>TEMPLATE ---- TEMPLATE ---- TEMPLATE</h1>
		<p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Excepturi repellendus veniam illo explicabo repellat veritatis, quis eius. Distinctio mollitia eos eaque rem facere incidunt placeat pariatur, nobis ut deleniti consectetur.</p>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>