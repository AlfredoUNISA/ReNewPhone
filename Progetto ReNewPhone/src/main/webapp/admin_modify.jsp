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
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Admin - Modify</title>
</head>
<body>

	<div class="content">
		<h1>Seleziona il prodotto da modificare</h1>

		<form id="ricercaForm">
			<label for="brandSelect">Brand:</label> 
			<select id="brandSelect" required></select><br><br>
			
			<div id="formAfterBrand">
				<label for="deviceSelect">Dispositivo:</label> 
				<select id="deviceSelect" required>
					<option value="0">Seleziona prima un brand</option>
				</select><br><br>

				<div id="formAfterDevice">
					<label for="storageSelect">Spazio interno:</label>
					<select id="storageSelect" required>
						<option value="0">Seleziona prima un dispositivo</option>
					</select><br><br>

					<div id="formAfterStorage">
						<label for="ramSelect">RAM:</label>
						<select id="ramSelect" required>
							<option value="0">Seleziona prima uno spazio interno</option>
						</select><br><br>

						<div id="formAfterRam">
							<label for="displaySizesSelect">Schermo:</label> 
							<select id="displaySizesSelect" required>
								<option value="0">Seleziona prima la ram</option>
							</select><br><br>
							
							<div id="formAfterDisplaySize">
								<label for="colorSelect">Colore:</label> 
								<select id="colorSelect" required>
									<option value="0">Seleziona prima uno schermo</option>
								</select><br><br>
								
								<div id="formAfterColor">
									<label for="stateSelect">Condizione:</label> 
									<select id="stateSelect" required>
										<option value="0">Seleziona prima un colore</option>
									</select><br><br>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<input type="submit" id="submitButton" value="Cerca">
		</form>
		<p id="result"></p>


	</div>
	<script type="text/javascript"><%@include file="js/AdminProductModify.js" %></script>
	<%@ include file="_footer.html"%>
</body>
</html>