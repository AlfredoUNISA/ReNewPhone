<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*,rnp.UserBean,rnp.UserDAODataSource"%>
		


<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" /> 
	<title>Registrazione nuovo utente</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		
		
		<h2>Inserisci</h2>
		<form action="users" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="name">Nome:</label><br> 
			<input name="name" type="text" maxlength="25" required placeholder="Inserisci nome"><br> 
			
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