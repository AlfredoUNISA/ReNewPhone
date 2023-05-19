<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Accedi</title>
</head>
<body>
	<%@ include file="_header.jsp"%>

	<div class="content">
		<h2>Accedi al tuo account</h2>

		<form action="login" method="post">
			<input type="email" name="email" required placeholder="Username"><br>
			<input type="password" name="password" required placeholder="Password"><br> 
			<input type="submit" value="Login"><br>
		</form><br>
		<p>
			Non sei ancora registrato? <a href="register.jsp">Registrati qui</a>
		</p>
		
		<hr>
		
		<h2>Accedi come:</h2>
		<form action="login" method="post">
			<input type="hidden" name="email" value="d@owner.com">
			<input type="hidden" name="password" value="password_davide"> 
			<input type="submit" value="ADMIN">
		</form>
		<form action="login" method="post">
			<input type="hidden" name="email" value="johndoe@example.com">
			<input type="hidden" name="password" value="password123"> 
			<input type="submit" value="John Doe">
		</form>
		<form action="login" method="post">
			<input type="hidden" name="email" value="janesmith@example.com">
			<input type="hidden" name="password" value="password456"> 
			<input type="submit" value="Jane Smith">
		</form>
		<form action="login" method="post">
			<input type="hidden" name="email" value="bobjohnson@example.com">
			<input type="hidden" name="password" value="password789"> 
			<input type="submit" value="Bob Johnson">
		</form>
		<form action="login" method="post">
			<input type="hidden" name="email" value="alicelee@example.com">
			<input type="hidden" name="password" value="passwordabc"> 
			<input type="submit" value="Alice Lee">
		</form>
		<form action="login" method="post">
			<input type="hidden" name="email" value="davidchen@example.com">
			<input type="hidden" name="password" value="passworddef"> 
			<input type="submit" value="David Chen">
		</form>
		
	</div>

	<%@ include file="_footer.html"%>
</body>
</html>