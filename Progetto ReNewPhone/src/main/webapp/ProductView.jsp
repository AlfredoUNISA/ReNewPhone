<%@page import="java.util.stream.Collectors"%>
<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

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
	<meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<!-- /products -->
	<title>Product List</title>
</head>
<body>
	
	<div class="content">
	    <h2>Lista Prodotti</h2>
	    <div class="productsGrid">

<!-- Aggiungi il seguente script JavaScript -->
<script>
	$(document).ready(function(){
	var productsJson = '${productsJson}';
	
	// Analizza la stringa JSON in un oggetto JavaScript
	var product = JSON.parse(productsJson);
	//create a map to organize data in product by name
	var productMap = new Map();
	//TODO: IMPLEMENTARE IL COSTO MINIMO E IL COSTO MASSIMO
	var minRam=Number.MAX_SAFE_INTEGER;
	var  maxRam=Number.MIN_SAFE_INTEGER;
	var  minPrice=Number.MAX_SAFE_INTEGER; 
	var  maxPrice=Number.MIN_SAFE_INTEGER;
	var  minStorage=Number.MAX_SAFE_INTEGER; 
	var  maxStorage=Number.MIN_SAFE_INTEGER;
	
	for(var i=0; product[i+1]!=null; i++){
		
		productMap.set(product[i].name, product[i]);
	}
	
	 // Itera sui prodotti e visualizzali
		productMap.forEach(function(productMap) {
		  // Crea il markup HTML per un singolo prodotto
		  var html = '<div id="Product">' +
		    '<img class="productImg" alt="' + productMap.model + '" src="resources/' + productMap.model + '.jpg">' +
		    '<div class="productInfo">' +
		    '<p>' + productMap.name + '</p>' +
		    '<p>RAM: ' + productMap.ram + 'GB</p>' +
		    '<p>Dimensioni: ' + productMap.display_size + '""</p>' +
		    '<p>Memoria: ' + productMap.storage + 'GB</p>' +
		    '<p>Marca: ' + productMap.brand + '</p>' +
		    '<p>Anno: ' + productMap.year + '</p>' +
		    '<p>Prezzo: ' + productMap.price + '€</p>' +
		    '<p><a href="products?action=details&name=' + productMap.name + '">Dettagli</a></p>' +
		    '</div>' +
		    '</div>';
		
		  // Aggiungi il markup HTML al contenitore dei prodotti
		  $('.productsGrid').append(html);
		}); 
	});
</script>
		</div>
			
	
		<!-- PARTE DELL'INSERIMENTO -->
		<br>
	    <h2>Aggiungi un prodotto</h2>
	    <form method="post" action="products?action=add">
	        <label>Nome:</label>
	        <input type="text" name="name" maxlength="150" placeholder="Inserisci nome" required><br><br>
	        
	        <label>Descrizione:</label>
	        <input type="text" name="description" maxlength="255" placeholder="Inserisci descrizione" required><br><br>
	        
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