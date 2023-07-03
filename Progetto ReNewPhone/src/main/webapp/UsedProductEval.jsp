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
	<title>Homepage</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

	<div class="content">
    
    <h1>Inserimento dinamico con Ajax</h1>

    <form id="inserimentoForm">
		<label for="brandSelect">Brand</label> 
		<select id="brandSelect">
		<option value="seleziona"> SELEZIONA </option>
		</select>

        <label for="modello">Modello:</label>
        <select id="modello"></select>

        <label for="anno">Anno di produzione:</label>
        <input type="number" name="anno" id="anno" required><br>

        <label for="condizione">Condizione:</label>
        <select name="condizione" id="condizione" required>
            <option value="ottima">Ottima</option>
            <option value="buona">Buona</option>
            <option value="accettabile">Accettabile</option>
        </select><br>

        <input type="submit" id="submitButton" value="Inserisci">
    </form>

    <div id="risultato"></div>
	
	
	
	
	
	
	
	
	
	</div>
	<script type="text/javascript"><%@include file="js/productsEval.js" %></script>
	<%@ include file="_footer.html" %>
</body>
</html>