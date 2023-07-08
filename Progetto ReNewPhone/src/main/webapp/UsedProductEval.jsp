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
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Homepage</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

	<div class="content">

		<h1>Valuta il tuo usato !</h1>

		<form id="inserimentoForm">
			<label for="brandSelect">Brand</label> 
			<select id="brandSelect" required>
				<option value="seleziona">SELEZIONA</option>
			</select> <br>
			
			<fieldset id="formAfterBrand" class="productEvalFieldSet">
				<label for="modelSelect">Modello:</label> <select id="modelSelect"
					required>
					<option value="seleziona">SELEZIONA</option>
				</select>

				<fieldset id="formAfterModel" class="productEvalFieldSet">
					<label for="storage">Spazio interno:</label> <input type="number"
						name="storage" id="storage" min="1" max="9999" required><br>

					<label for="condizione">Condizione:</label> <select
						name="condizione" id="condizione" required>

						<option value="ottima">Ottima</option>
						<option value="buona">Buona</option>
						<option value="accettabile">Accettabile</option>
					</select><br> <label for="colore">Colore:</label> <input type="text"
						name="colore" id="colore" size="30" required><br> <br>


				</fieldset>


			</fieldset>



			<input type="submit" id="submitButton" value="Inserisci">
		</form>

		<div id="risultato"></div>









	</div>
	<script type="text/javascript"><%@include file="js/ProductsEval.js" %></script>
	<%@ include file="_footer.html"%>
</body>
</html>