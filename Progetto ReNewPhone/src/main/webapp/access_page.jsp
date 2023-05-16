<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="css/content.css" /> 
	<title>Homepage</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		 <h2>Accedi al tuo account</h2>
  
  <form action="UserControl.java" method="post">
    <input type="email" name="username" required placeholder="Username" > <br>
    <input type="password" name="password" required placeholder="Password"> <br>
    <input type="submit" value="Login">
  	Non sei ancora registrato? <a href="register.jsp">Registrati qui</a>
  </form>
	</div>
	
	<%@ include file="_footer.html" %>
</body>
</html>