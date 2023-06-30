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
	if(!IS_CURRENT_USER_ADMIN){
		// Manda alla pagina di errore 404 per motivi di sicurezza
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script src="js/uploadImage.js"></script>
	<title>Admin</title>
</head>
<body>

	<div class="content">
		
		<h2>Aggiungi un prodotto</h2>
		<form action="AdminInsert" method="post" enctype="multipart/form-data">
			<input type="file" name="file" />
			
		    <label>Nome:</label>
		    <input type="text" name="name" maxlength="150" placeholder="Inserisci nome" required><br><br>
	
			<label>Ram:</label>
		    <input type="number" name="ram" min="0" value="0" required><br><br>
	
			<label>Display:</label>
		    <input type="number" name="display_size" min="0" value="0" required><br><br>
		    
		    <label>Storage:</label>
		    <input type="number" name="storage" min="0" value="0" required><br><br>
	
		    <label>Prezzo:</label>
		    <input type="number" name="price" min="0" value="0" required><br><br>
		
		    <label>Quantità:</label>
		    <input type="number" name="quantity" min="1" value="1" required><br><br>
		
		    <label>Colore:</label>
		    <input type="text" name="color" maxlength="20" placeholder="Inserisci colore" required><br><br>
		
		    <label>Marca:</label>
		    <input type="text" name="brand" maxlength="25" placeholder="Inserisci marca" required><br><br>
		
		    <label>Anno:</label>
		    <input type="number" name="year" min="2000" required><br><br>
		
		    <label>Categoria:</label>
		    <select name="category" required>
				<option value="Smartphone">Smartphone</option>
		    	<option value="Tablet">Tablet</option>
			</select><br><br>
		
		    <label>Condizione:</label>
			<select name="state" required>
				<option value="Accettabile">Accettabile</option>
				<option value="Buono">Buono</option>
		  		<option value="Ottimo">Ottimo</option>
			</select><br><br>
		
		    <input type="submit" value="Add">
		</form>


	</div>

	<%@ include file="_footer.html" %>
</body>
</html>