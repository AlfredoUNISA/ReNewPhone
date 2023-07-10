<%@page import="rnp.DAO.ProductDAODataSource"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp"%>
<%
if (!IS_CURRENT_USER_ADMIN) {
	// Manda alla pagina di errore 404 per motivi di sicurezza
	response.sendError(HttpServletResponse.SC_NOT_FOUND);
}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Admin - Modify</title>
</head>
<body>

	<div class="content">
		<h1>Seleziona il prodotto da modificare</h1>

		<div id="ricerca">
			<label for="brandSelect">Brand:</label> 
			<select id="brandSelect" required></select><br><br> 
			
			<label for="deviceSelect">Dispositivo:</label>
			<select id="deviceSelect" required>
				<option value="0">Seleziona prima un brand</option>
			</select><br><br> 
			
			<label for="storageSelect">Spazio interno:</label> 
			<select id="storageSelect" required>
				<option value="0">Seleziona prima un dispositivo</option>
			</select><br><br> 
			
			<label for="ramSelect">RAM:</label> 
			<select id="ramSelect" required>
				<option value="0">Seleziona prima uno spazio interno</option>
			</select><br><br> 
			
			<label for="displaySizesSelect">Schermo:</label>
			<select id="displaySizesSelect" required>
				<option value="0">Seleziona prima la ram</option>
			</select><br><br> 
			
			<label for="colorSelect">Colore:</label> 
			<select	id="colorSelect" required>
				<option value="0">Seleziona prima uno schermo</option>
			</select><br><br> 
			
			<label for="stateSelect">Condizione:</label> 
			<select id="stateSelect" required>
				<option value="0">Seleziona prima un colore</option>
			</select><br><br>
		</div>
		
		<div id="searchId">
			<h2>Oppure</h2>
			<input type="checkbox" id="searchIdCheck">Ricerca tramite id
			<b>ID: </b> <input type="number" id="searchIdSelect" min="1" value="1"><br><br>
			<button id="searchButton">Cerca</button>
		</div>
		

		<div id="modifyForm">
			<hr>
			<h2>Modifica</h2>
			<p id="result"></p>
			RAM: <input type="number" id="modifyRam" min="1" required><br>
			<br> Storage: <input type="number" id="modifyStorage" min="1"
				required><br>
			<br> Display: <input type="number" id="modifyDisplay" min="1"
				required><br>
			<br> Price: <input type="number" id="modifyPrice" min="1"
				required><br>
			<br> Quantity: <input type="number" id="modifyQuantity" min="1"
				required><br>
			<br>
			<button id="modifyButton">Modifica</button>
		</div>

	</div>
	<script type="text/javascript"><%@include file="js/AdminProductModify.js" %></script>
	<%@ include file="_footer.html"%>
</body>
</html>