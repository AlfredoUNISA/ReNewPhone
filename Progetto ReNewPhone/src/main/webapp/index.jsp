<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/style.css">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Homepage</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		<h1>Home Page</h1>
		<p>Benvenuto nel sito di ReNewPhone, il sito di riferimento per la vendita di smartphone ricondizionati.</p>
		
		<a href="products">TEST DATABASE - Prodotti</a><br>
		<a href="users">TEST DATABASE - Utenti</a><br>
		<a href="orders">TEST DATABASE - Ordini</a><br>
		<a href="SearchPage.jsp">TEST RICERCA</a><br><br>
		
		<label>Controlla il carrello dell'utente con id:</label>
		<form action="my-cart" method="get">
			<input name="user" type="number" min="1">
			<br>
			<input type="submit">
		</form>
		
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