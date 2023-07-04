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
		<h2>Lista Prodotti</h2>

		<input type="button" class="slide-toggle" value="Apri Filtri"
			id="myButton">

		<div class="filterBox">
			<div class="box-inner">
				<h5>Filtri:</h5>
				<fieldset id="priceRange">
					<input type="number" placeholder="Min" min="0" max="9999">
					Prezzo <input type="number" placeholder="Max" min="0" max="9999">
				</fieldset>
				<fieldset id="memoryRange">
					<input type="number" placeholder="Min" min="0" max="9999">Gb
					Spazio interno <input type="number" placeholder="Max" min="0"
						max="9999">Gb
				</fieldset>
				<fieldset>
					<label for="brandSelect">Brand</label> <select id="brandSelect">
						<option value="Seleziona">Seleziona</option>
						<option value="Apple">Apple</option>
						<option value="Google">Google</option>
						<option value="Samsung">Samsung</option>
						<option value="Xiaomi">Xiaomi</option>
					</select>
				</fieldset>
				<fieldset id="ramRange">
					<input type="number" placeholder="Min" min="0" max="99">Gb
					RAM <input type="number" placeholder="Max" min="0" max="99">Gb
				</fieldset>
				<input type="button" class="processButton" value="Conferma"
					id="myButton1">
			</div>
		</div>


		<div class="productsGrid">



			<script type="text/javascript"><%@include file="js/productsFilter.js" %></script>

			<script type="text/javascript"><%@include file="js/productsAjax.js" %></script>
		</div>

		<button id="loadMoreButton">Carica Altro</button>
		<button id="loadMoreFilteredButton" style="display: none">Carica
			Altro</button>

	</div>

	<%@ include file="_footer.html"%>
</body>
</html>