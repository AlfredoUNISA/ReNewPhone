<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<title>Product List</title>
</head>
<body>
	<%@ include file="_header.html" %>
	
	<div class="content">
	    <h2>Lista Prodotti</h2>
	    
	    <!-- TABELLA PRINCIPALE -->
	    <table>
	        <thead>
	            <tr>
	                <th>ID</th>
	                <th>Nome</th>
	                <th>Descrizione</th>
	                <th>Prezzo</th>
	                <th>Quantità</th>
	                <th>Colore</th>
	                <th>Marca</th>
	                <th>Anno</th>
	                <th>Categoria</th>
	                <th>Condizioni</th>
	                <th><i>Azioni</i></th>
	            </tr>
	        </thead>
	        <tbody>
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> products = (Collection<?>) request.getAttribute("products");
	            
	            // ITERAZIONE
	            if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ProductBean product = (ProductBean) it.next();
	            %>
	            <tr>
	                <td><%= product.getId() %></td>
	                <td><%= product.getName() %></td>
	                <td><%= product.getDescription() %></td>
	                <td><%= product.getPrice() %></td>
	                <td><%= product.getQuantity() %></td>
	                <td><%= product.getColor() %></td>
	                <td><%= product.getBrand() %></td>
	                <td><%= product.getYear() %></td>
	                <td><%= product.getCategory() %></td>
	                <td><%= product.getState() %></td>
	                <td>
	                    <a href="products?action=details&id=<%= product.getId() %>#Dettagli">Dettagli</a>
	                    <a href="products?action=delete&id=<%= product.getId() %>">Elimina</a>
	                </td>
	            </tr>
	            <% 
	                }
	            } else {
	            %>
	            <tr>
	                <td colspan="11">No products found.</td>
	            </tr>
	            <% } %>
	        </tbody>
	    </table>
		
		<!-- TABELLA DETTAGLI -->
		<% 
			ProductBean productDetails = (ProductBean) request.getAttribute("product-details");
			if (productDetails != null) {
		%>
			<h2 id="Dettagli">Dettagli</h2>
			<table>
				<thead>
					<tr>
						<th>ID</th>
		                <th>Nome</th>
		                <th>Descrizione</th>
		                <th>Prezzo</th>
		                <th>Quantità</th>
		                <th>Colore</th>
		                <th>Marca</th>
		                <th>Anno</th>
		                <th>Categoria</th>
		                <th>Condizioni</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><%= productDetails.getId() %></td>
	                	<td><%= productDetails.getName() %></td>
	                	<td><%= productDetails.getDescription() %></td>
	                	<td><%= productDetails.getPrice() %></td>
	                	<td><%= productDetails.getQuantity() %></td>
	                	<td><%= productDetails.getColor() %></td>
	                	<td><%= productDetails.getBrand() %></td>
	                	<td><%= productDetails.getYear() %></td>
						<td><%= productDetails.getCategory() %></td>
						<td><%= productDetails.getState() %></td>
					</tr>
				</tbody>
			</table>
		<%}%>
	
		
		<!-- PARTE DELL'INSERIMENTO -->
	    <h2>Aggiungi un prodotto</h2>
	    <form method="post" action="products?action=add">
	        <label>Nome:</label>
	        <input type="text" name="name" required><br><br>
	        
	        <label>Descrizione:</label>
	        <input type="text" name="description" required><br><br>
	        
	        <label>Prezzo:</label>
	        <input type="number" name="price" required><br><br>
	        
	        <label>Quantità:</label>
	        <input type="number" name="quantity" required><br><br>
	        
	        <label>Colore:</label>
	        <input type="text" name="color" required><br><br>
	        
	        <label>Marca:</label>
	        <input type="text" name="brand" required><br><br>
	        
	        <label>Anno:</label>
	        <input type="number" name="year" required><br><br>
	        
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