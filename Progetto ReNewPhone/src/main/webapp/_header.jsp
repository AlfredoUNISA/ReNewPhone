<%@page import="rnp.UserDAODataSource"%>
<%@page import="rnp.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	// Ottenimento dell'utente attuale (funziona su tutte le pagine che utilizzano l'header)
	Object session_obj = request.getSession().getAttribute("user");
	int CURRENT_USER_ID = -1;	
	if(session_obj != null)
		CURRENT_USER_ID = (int) session_obj;
	
	UserBean CURRENT_USER_BEAN = null;
	if(CURRENT_USER_ID > 0){
		UserDAODataSource CURRENT_USER_DAO = new UserDAODataSource();
		CURRENT_USER_BEAN = CURRENT_USER_DAO.doRetrieveByKey(CURRENT_USER_ID);
	}
%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="header-body">
	<nav>
		<ul class="pulsanti-sinistra">
			<li><a href="index.jsp">Homepage</a></li>
			<li><a href="about.jsp">Chi siamo</a></li>
			<li><a href="contact.jsp">Contatti</a></li>
		</ul>
		<form class="cerca" action="search" method="get">
			<input type="search" name="q" placeholder="Cerca...">
			<button type="submit">Cerca</button>
		</form>
		<ul class="pulsanti-destra">
			<li><a href="my-cart">Carrello</a></li>
			
			<%if(CURRENT_USER_ID > 0) {%>
				<li><a href="user_page.jsp">Account</a></li>
			<%} else {%>
				<li><a href="access_page.jsp">Accedi</a></li>
			<%} %>
		</ul>
	</nav>
</body>
</html>