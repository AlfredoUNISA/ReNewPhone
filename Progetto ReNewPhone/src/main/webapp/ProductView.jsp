<%@ page import="java.util.stream.Collectors"%>
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
	<!-- /products -->
	<meta charset="UTF-8">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<title>Product List</title>
</head>
<body>
	<div class="content">
	   <h2>Lista Prodotti</h2>
	    
	    
	    <div class="box" style="float:left; display:none">
            <div class="box-inner">
       		 <h5>Filtri:</h5>
       		 	<fieldset id="priceRange">
	                <input type="number" placeholder="Min" min="0" max="9999">  
	                 Prezzo
	                <input type="number" placeholder="Max" min="0" max="9999">
               	</fieldset>
                <fieldset id="memoryRange">
	                <input type="number" placeholder="Min" min="0" max="9999">Gb  
	                 Spazio interno
	                <input type="number" placeholder="Max" min="0" max="9999">Gb
               	</fieldset>
                <fieldset>
               	<label for="brandSelect">Brand</label>             
	                <select id="brandSelect">
	                	<option value="Seleziona"> Seleziona </option>
	                	<option value="Apple">Apple</option>
	                	<option value="Google">Google</option>
	                	<option value="Samsung">Samsung</option>
	                	<option value="Xiaomi">Xiaomi</option>
	                </select>
               	</fieldset>
				<fieldset id="ramRange">
	                <input type="number" placeholder="Min" min="0" max="9999">Gb  
	                 RAM
	                <input type="number" placeholder="Max" min="0" max="9999">Gb
               	</fieldset>
                <input type="button" 
               	class="processButton"
               	value="Conferma" 
               	id="myButton1">
            </div>
        </div>
        <input	type="button" 
               	class="slide-toggle"
               	value="Apri Filtri" 
               	id="myButton">
    
		 	
			    <div class="productsGrid">
	    
	    
	    
	    	<script type="text/javascript"><%@include file="js/productFilterScript.js" %></script>
		
			<script type="text/javascript"><%@include file="js/ajaxScript.js" %></script>
		</div>
		
		<button id="loadMoreButton">Carica Altro</button>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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