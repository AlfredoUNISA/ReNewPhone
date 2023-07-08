<%@ page import="java.util.stream.Collectors"%>
<%@ page import="rnp.DAO.OrderDAODataSource,rnp.DAO.UserDAODataSource,rnp.DAO.ProductDAODataSource"%>
<%@ page import="rnp.Bean.OrderBean,rnp.Bean.UserBean,rnp.Bean.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<!-- /products -->
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Product List</title>
</head>
<body>
	<div class="content">
		<h1>Lista Prodotti</h1>


		

		<div class="filterBox">
				<h2>Filtri:</h2><br>
			<div class="box-inner">
				

				<fieldset id="priceRange">
				<h3>
					<input type="number" placeholder="Min" min="0" max="9999">
					Prezzo  <input type="number" placeholder="Max" min="0" max="9999">
					</h3>
				</fieldset>

				
				
				<fieldset id="memoryRange">
					<h3><input type="number" placeholder="Min" min="0" max="9999">
					Memoria Interna   <input type="number" placeholder="Max" min="0"
						max="9999"></h3>
				</fieldset>

				
				
				<fieldset>
					<h3><label for="brandSelect">Brand</label> <select id="brandSelect">
						<option value="Seleziona">Seleziona</option>
						<option value="Apple">Apple</option>
						<option value="Google">Google</option>
						<option value="Samsung">Samsung</option>
						<option value="Xiaomi">Xiaomi</option>
					</select> </h3>
				</fieldset>

				
				
				<fieldset id="ramRange">
					<h3><input type="number" placeholder="Min" min="0" max="99">
					 RAM(Gb) <input type="number" placeholder="Max" min="0" max="99"> </h3>
				</fieldset>

				
				<fieldset id="yearRange">
				<h3>
					<input type="number" placeholder="Min" min="0" max="9999">
					Anno(yyyy) <input type="number" placeholder="Max" min="0" max="9999">
					</h3>
				</fieldset>
				
				
				</div>
				<br>
				<div class="content">
				<input type="button" class="processButton" value="Conferma"
					id="myButton1">
				</div>
		</div>
		<br>
		<input type="button" class="slide-toggle" value="Apri Filtri"
			id="myButton">

		<div class="productsGrid">


			<script type="text/javascript"><%@include file="js/ProductFilter.js" %></script>

			<script type="text/javascript"><%@include file="js/Products.js" %></script>
		</div>

		<input type="button" id="loadMoreButton" value="Carica Altro">
		<a href="#"> <input type="button"  value="Torna all'inizio">   </a>
	

	</div>

	<%@ include file="_footer.html"%>
</body>
</html>