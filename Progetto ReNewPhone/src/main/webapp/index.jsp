<%@ page import="rnp.UserDAODataSource"%>
<%@ page import="rnp.UserBean"%>
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
	<link rel="stylesheet" href="css/style.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Homepage</title>
</head>
<body>

	<div class="content">
		<h1>Home Page</h1>
		<% if (IS_CURRENT_USER_REGISTRED){ %>
			<p>Benvenuto nel sito di ReNewPhone <b><%=CURRENT_USER_BEAN.getName()%> <%=CURRENT_USER_BEAN.getSurname()%></b>, il sito di riferimento per la vendita di smartphone ricondizionati.</p>

		<%} else {%>
			<p>Benvenuto nel sito di ReNewPhone, il sito di riferimento per la vendita di smartphone ricondizionati.</p>
		<%} %>
		
		
		<% if (IS_CURRENT_USER_ADMIN){ %>
		<hr>
			<a href="admin.jsp">Vai alla pagina per admin</a><br>
		<hr>
		<%}%>
		
		<p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Accusamus aperiam iusto quibusdam ducimus adipisci, delectus fugiat, eum neque autem quidem a nam. Libero sed voluptates incidunt dignissimos doloribus id voluptatibus?</p>
	</div>
	
	<h2>I nostri prodotti di punta</h2>
	<div class ="slider">
		<div class="slide-track">
		
			<div class="slide"><a href="search?q=+iPhone">
			<img src="resources/iphone.png" alt="iPhone">  </a></div>
			<div class="slide"><a href="search?q=+Samsung+Galaxy">
			<img src="resources/samsung_galaxy.png" alt="Samsung Galaxy">  </a></div>
			<div class="slide"><a href="search?q=+Google+Pixel">
			<img src="resources/google_pixel.jpg" alt="Samsung Galaxy">  </a></div>
			
			<div class="slide"><a href="search?q=+iPhone">
			<img src="resources/iphone.png" alt="iPhone">  </a></div>
			<div class="slide"><a href="search?q=+Samsung+Galaxy">
			<img src="resources/samsung_galaxy.png" alt="Samsung Galaxy">  </a></div>
			<div class="slide"><a href="search?q=+Google+Pixel">
			<img src="resources/google_pixel.jpg" alt="Samsung Galaxy">  </a></div>
			
			<div class="slide"><a href="search?q=+iPhone">
			<img src="resources/iphone.png" alt="iPhone">  </a></div>
			<div class="slide"><a href="search?q=+Samsung+Galaxy">
			<img src="resources/samsung_galaxy.png" alt="Samsung Galaxy">  </a></div>
			<div class="slide"><a href="search?q=+Google+Pixel">
			<img src="resources/google_pixel.jpg" alt="Samsung Galaxy">  </a></div>
		</div>
	
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>