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
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Valutazione Usato</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

	<div class="content">
		
		<h1>Valuta il tuo usato !</h1>

		<form id="inserimentoForm">
			<label for="brandSelect">Brand
			<select id="brandSelect" required>
				<option value="seleziona">SELEZIONA</option>
			</select> </label> 
			
<fieldset id="formAfterBrand" class="fieldset">
				<label for="modelSelect">Modello:<select id="modelSelect"
					required>
					<option value="seleziona">SELEZIONA</option>
				</select></label> 

	<fieldset id="formAfterModel" class="fieldset">
						<label for="storage">Spazio interno: <select
							name="storage" id="storage" required>
	
							<option value="selezionaStorage">SELEZIONA</option>
							<option value="64">64 Gb</option>
							<option value="128">128 Gb</option>
							<option value="256">256 Gb</option>
						</select></label> 

			<fieldset id="formAfterStorage" class="fieldset">
								<label for="condizione">Condizione: <select
									name="condizione" id="condizione" required>
									
									<option value="ottima">Ottima</option>
									<option value="buona">Buona</option>
									<option value="accettabile">Accettabile</option>
									
								</select></label> 
			
			</fieldset>
	</fieldset>			
</fieldset>	

			<input type="submit" id="submitButton" value="Inserisci">
		</form>

		<div id="risultato"></div>



	</div>

	<script type="text/javascript"><%@include file="js/ProductEval.js" %></script>
	<%@ include file="_footer.html"%>
</body>
</html>