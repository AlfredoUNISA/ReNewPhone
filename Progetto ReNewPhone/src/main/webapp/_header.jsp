<%@ page import="rnpSupport.Login"%>
<%@page import="rnpDAO.UserDAODataSource"%>
<%@page import="rnpBean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	// Ottenimento dell'utente attuale (funziona su tutte le pagine che utilizzano l'header)
	Object session_obj = request.getSession().getAttribute("user");
	//System.out.println("[header] session_obj: " + session_obj);
	int CURRENT_USER_ID = -1;
	if(session_obj != null)
		CURRENT_USER_ID = (int) session_obj;

	/*
		ID:
	   -10 = Admin
		-1 = Utente non registrato
		>0 = Utente registrato
	*/
	UserBean CURRENT_USER_BEAN = new UserBean();
	Boolean IS_CURRENT_USER_ADMIN = false;
	Boolean IS_CURRENT_USER_REGISTRED = false;
	
	if(CURRENT_USER_ID != -1){
		// Utente registrato o admin
		UserDAODataSource dao = new UserDAODataSource();
		CURRENT_USER_BEAN = dao.doRetrieveByKey(CURRENT_USER_ID);
		
		IS_CURRENT_USER_REGISTRED = true;
		IS_CURRENT_USER_ADMIN = Login.isAdmin(CURRENT_USER_ID);
	} else {
		// Utente non registrato o uscito, controlla il cookie "userCookie"
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("userCookie")) {
		        	// Ottieni l'id dal cookie
		        	CURRENT_USER_ID = Integer.parseInt(cookie.getValue());
		        	
		        	UserDAODataSource dao = new UserDAODataSource();
		    		CURRENT_USER_BEAN = dao.doRetrieveByKey(CURRENT_USER_ID);
		        	
		    		IS_CURRENT_USER_REGISTRED = true;
		        	IS_CURRENT_USER_ADMIN = Login.isAdmin(CURRENT_USER_ID);
		        }
		    }
		}
	}
	//System.out.println("[header] CURRENT_USER_ID: " + CURRENT_USER_ID);
	
%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="css/style.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="header-body">
	<nav>
		<ul class="pulsanti-sinistra">
			<li><a href="index.jsp">Homepage</a></li>
			<li><a href="about.jsp">Chi siamo</a></li>
			<li><a href="contact.jsp">Contatti</a></li>
			<li><a href="products">Prodotti</a></li>
		</ul>
		<form class="cerca" action="search" method="get">
			<input type="search" name="q" placeholder="Cerca...">
			<button type="submit">Cerca</button>
		</form>
		<ul class="pulsanti-destra">
			
			<%if(IS_CURRENT_USER_ADMIN) {%>
				<li><a href="admin.jsp">Pannello Admin</a>
			<%} else {%>
				<li><a href="UsedProductEval.jsp">Valuta il tuo usato</a></li>
				<li><a href="my-cart">Carrello</a></li>
			<%} %>
			
			
			
			<%if(IS_CURRENT_USER_REGISTRED) {%>
				<li><a href="user_page.jsp">Account</a></li>
			<%} else {%>
				<li><a href="login.jsp">Accedi</a></li>
			<%} %>
		</ul>
	</nav>
</body>
</html>