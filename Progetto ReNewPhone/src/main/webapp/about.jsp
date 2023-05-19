<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <title>Chi siamo</title>
</head>
<body>
    <%@ include file="_header.jsp" %>

    <div class="content">
        <h1>Chi siamo</h1>
        <img src="resources/chisiamo.png" alt="Chi siamo" width="300" height="200">
        <p>
        Siamo tre studenti di Unisa con la passione per l'informatica
        </p>
    </div>
    
    <%@ include file="_footer.html" %>
</body>
</html>