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
				<i><h2><%=name%></h2></i>
				<div id="categoryContainer"></div>
				<div id="brandContainer"></div>
				<div id="yearContainer"></div>
			</div>
			<h2>Specifiche</h2>
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
		<br>  
		<div class="buyProduct">
			Seleziona la quantità: 
			<select id="quantitySelect">
				<option id="quantityOption1" value="1">1</option>
				<option id="quantityOption2" value="2">2</option>
				<option id="quantityOption3" value="3">3</option>
				<option id="quantityOption4" value="4">4</option>
				<option id="quantityOption5" value="5">5</option>
			</select>
			<button id="addToCartBtn">Aggiungi al carrello</button>
		</div>

	</div>

	<%@ include file="_footer.html" %>
</body>
</html>