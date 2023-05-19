<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <title>Chi siamo</title>
</head>
<body>

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