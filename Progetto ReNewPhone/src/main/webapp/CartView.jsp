<%@ page import="rnp.DAO.OrderDAODataSource,rnp.DAO.UserDAODataSource,rnp.DAO.ProductDAODataSource"%>
<%@ page import="rnp.Bean.CartBean,rnp.Bean.OrderBean,rnp.Bean.UserBean,rnp.Bean.ProductBean"%>
<%@ page import="java.util.*"%>

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
	if(IS_CURRENT_USER_ADMIN)
		response.sendRedirect("index.jsp");
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<!-- /my-cart -->
	<link rel="stylesheet" href="css/style.css">
	<title>Carrello</title>
</head>
<body>
	
	<div class="content">
	    
	    <h2>Utente: "<%= CURRENT_USER_BEAN.getName() %> <%= CURRENT_USER_BEAN.getSurname() %>" (id = <%= CURRENT_USER_ID %>)</h2>
	    
	    <div class="tableCart"></div>
		
		<div class="sumCart"></div>
		
		
    	<input id="submitBtn" type="submit" value="Finalizza ordine">
	
    </div>
    
    <script type="text/javascript"><%@include file="js/Cart.js" %></script>
    <%@ include file="_footer.html" %>
</body>
</html>