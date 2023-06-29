<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
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

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript"><%@include file="js/loadDetails.js" %></script>
	<title>Homepage</title>
</head>
<body>
	<% String name = request.getParameter("name"); %>

	<div class="content">
		<h1>Dettagli</h1><br>
		<img class="detailImage" alt="<%=name%>" src="resources/<%=name%>.jpg">
		<div class="productDetails">
			<div id="description">
				<h2><%=name%></h2>
				<div id="categoryContainer"></div>
				<div id="brandContainer"></div>
				<div id="yearContainer"></div>
			</div>

			<div id="ramContainer"></div>
			<div id="displaySizeContainer"></div>
			<div id="storageContainer"></div>
			<div id="colorContainer"></div>
			<div id="colorContainer"></div>
			<div id="stateContainer"></div>
			<br><br>
			<div id="priceContainer"></div>
			<div id="quantityContainer"></div>
			<div id="idContainer"></div>
		</div>  


	</div>

	<%@ include file="_footer.html" %>
</body>
</html>