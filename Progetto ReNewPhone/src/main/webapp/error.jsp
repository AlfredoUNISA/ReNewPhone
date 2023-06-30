<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp" %>

<%
	int errorCode = (int) request.getAttribute("javax.servlet.error.status_code");
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" /> 
	<title><%= errorCode %></title>
</head>
<body>

	<div class="content">
		<h1>ERRORE <%= errorCode %></h1>
		
		<%
			if(errorCode == 404){
		%>
			<p>Risorsa non trovata</p>
    	<%
			} else if (errorCode == 500) {
		%>
			<p>Problema con il server (controlla sysout)</p>
		<%
			}
			if(request.getAttribute("javax.servlet.error.exception") != null)
    			System.out.println(request.getAttribute("javax.servlet.error.exception"));
    	%>
    	
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>