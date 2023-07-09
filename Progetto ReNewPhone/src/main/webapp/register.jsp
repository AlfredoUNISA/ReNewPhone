<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,rnp.Bean.UserBean,rnp.DAO.UserDAODataSource"%>
		
<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" /> 
	<title>Registrazione nuovo utente</title>
</head>
<body>

	<div class="content">
		
		
		<h2>Registrati</h2>
		<form action="users" method="post">
			<input type="hidden" name="action" value="add"> 

			<label for="name">Nome:</label><br> 
			<input name="name" type="text" maxlength="25" required placeholder="Inserisci nome"><br> 
			
			<label for="surname">Cognome:</label><br> 
			<input name="surname" type="text" maxlength="25" required placeholder="Inserisci cognome"><br> 
			
			<label for="email">Email:</label><br> 
			<input name="email" type="email" maxlength="100" required placeholder="Inserisci email"><br> 
					
			<label for="password">Password:</label><br> 
			<input name="password" type="password" maxlength="50" required placeholder="Inserisci password"><br> 
			
			<label for="address">Indirizzo:</label><br> 
			<textarea name="address" maxlength="60" required placeholder="Inserisci indirizzo"></textarea><br> 
			
			<label for="city">Città:</label><br> 
			<input name="city" type="text" maxlength="35" required placeholder="Inserisci città"><br> 
			
			<label for="cap">CAP:</label><br> 
			<input name="cap" type="text" maxlength="7" required placeholder="Inserisci CAP"><br> 
			
			<label for="phone">Telefono:</label><br> 
			<input name="phone" type="text" maxlength="20" required placeholder="Inserisci numero di telefono"><br>
		
			<input type="submit" value="Add">
			<input type="reset" value="Reset">
	
		</form>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>