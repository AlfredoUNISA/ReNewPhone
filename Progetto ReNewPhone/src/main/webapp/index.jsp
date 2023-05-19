<%@page import="rnp.UserDAODataSource"%>
<%@page import="rnp.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/style.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Homepage</title>
</head>
<body>
	<%@ include file="_header.jsp" %>

	<div class="content">
		<h1>Home Page</h1>
		<% if (CURRENT_USER_ID > 0){ %>
			<p>Benvenuto nel sito di ReNewPhone <b><%=CURRENT_USER_BEAN.getName()%> <%=CURRENT_USER_BEAN.getSurname()%></b>, il sito di riferimento per la vendita di smartphone ricondizionati.</p>

		<%} else {%>
			<p>Benvenuto nel sito di ReNewPhone, il sito di riferimento per la vendita di smartphone ricondizionati.</p>
		<%} %>
		
		<hr>
		
		<a href="products">TEST DATABASE - Prodotti</a><br>
		<a href="users">TEST DATABASE - Utenti</a><br>
		<a href="orders">TEST DATABASE - Ordini</a><br>
		<a href="SearchPage.jsp">TEST RICERCA</a><br><br>
		
		<hr>
		<p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Accusamus aperiam iusto quibusdam ducimus adipisci, delectus fugiat, eum neque autem quidem a nam. Libero sed voluptates incidunt dignissimos doloribus id voluptatibus?</p>
	</div>
	

	<%@ include file="_footer.html" %>
</body>
</html>