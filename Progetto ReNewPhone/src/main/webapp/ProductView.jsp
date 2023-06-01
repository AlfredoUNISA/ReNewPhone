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
	<!-- /products -->
	<title>Product List</title>
	<meta charset="UTF-8">
</head>
<body>
	
	<div class="content">
	    <h2>Lista Prodotti</h2>
	    <div class="productsGrid">
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> products = (Collection<?>) request.getAttribute("products");
	            
	            // ITERAZIONE
	            if (products != null && products.size() != 0) {
	            	// Raggruppare i prodotti per nome e calcolare i valori minimi e massimi di ram, storage e price
	                Map<String, List<ProductBean>> groupedProducts = new HashMap<>();
	            	
	            	// Mapping Iniziale: nome / lista di prodotti con lo stesso nome
					Iterator<?> itMapping = products.iterator();
					while (itMapping.hasNext()) {
						ProductBean product = (ProductBean) itMapping.next();
						
						String productName = product.getName();
						List<ProductBean> productList = groupedProducts.getOrDefault(productName, new ArrayList<>());
						productList.add(product);
			            groupedProducts.put(productName, productList);
					}
					
					// Calcola e Mostra i valori minimi e massimi di ram, storage e price per ogni nome di prodotto
			        for (Map.Entry<String, List<ProductBean>> entry : groupedProducts.entrySet()) {
			            String productName = entry.getKey();
			            List<ProductBean> productList = entry.getValue();

			            int minRam = Integer.MAX_VALUE;
			            int maxRam = Integer.MIN_VALUE;
			            int minStorage = Integer.MAX_VALUE;
			            int maxStorage = Integer.MIN_VALUE;
			            int minPrice = Integer.MAX_VALUE;
			            int maxPrice = Integer.MIN_VALUE;

			            for (ProductBean product : productList) {
			                minRam = Math.min(minRam, product.getRam());
			                maxRam = Math.max(maxRam, product.getRam());
			                minStorage = Math.min(minStorage, product.getStorage());
			                maxStorage = Math.max(maxStorage, product.getStorage());
			                minPrice = Math.min(minPrice, product.getPrice());
			                maxPrice = Math.max(maxPrice, product.getPrice());
			            }
						
			            ProductBean productBean = productList.get(0);
	            %>
	        	<div id="Product">
	            	<img class="productImg" alt="<%=productBean.getModel()%>" src="resources/<%=productBean.getModel()%>.jpg" style="">
	            	<div class="productInfo">
		                <p> <%= productName %> </p>
		                
		                <% if(minRam == maxRam) { %>
		              		<p> RAM: <%= minRam %>GB </p>
		              	<% } else { %>
		              		<p> RAM (min-max): <%= minRam %>GB - <%= maxRam %>GB </p>
		              	<% } %>
		              	
						<p> Dimensioni: <%= productBean.getDisplay_size() %>'' </p>
						
						<% if(minStorage == maxStorage) { %>
							<p> Memoria: <%= minStorage %>GB </p>
						<% } else { %>
							<p> Memoria (min-max): <%= minStorage %>GB - <%= maxStorage %>GB </p>
						<% } %>
						
		                <p> Marca: <%= productBean.getBrand() %> </p>
		                
		                <p> Anno: <%= productBean.getYear() %> </p>
		                
		                <% if(minPrice == maxPrice) { %>
		                	<p id="price"> Prezzo: <%= minPrice %> € </p>
		                <% } else { %>
		                	<p id="price"> Prezzo (min-max): <%= minPrice %> € - <%= maxPrice %> € </p>
		                <% } %>
		                
		                <p> <a href="products?action=details&name=<%= productBean.getName() %>">Dettagli</a> </p>
		                <p> <a href="products?action=delete&id=<%= productBean.getId() %>">Elimina</a> </p>
					</div> 
				</div>
	            
	            <% 
	                }
	            } else {
	            %>

	               <h1>No products found. </h1>

	            <% } %>
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