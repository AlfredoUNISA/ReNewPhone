<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<!-- /orders -->
	<title>Order List</title>
</head>
<body>
	<%@ include file="_header.html" %>
	
	<div class="content">
	    <h2>Lista Ordini</h2>
	    
	    <!-- TABELLA PRINCIPALE -->
	    <table>
	        <thead>
	            <tr>
	                <th>Id <a href="orders?sort=id">Sort</a></th>
					<th>Id Utente <a href="orders?sort=id_user">Sort</a></th>
					<th>Id Prodotto <a href="orders?sort=id_product">Sort</a></th>
					<th>Quantità Ordinata <a href="orders?sort=quantity">Sort</a></th>
					<th><i>Azione</i></th>
	            </tr>
	        </thead>
	        <tbody>
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> orders = (Collection<?>) request.getAttribute("orders");
	            
	            // ITERAZIONE
	            if (orders != null && orders.size() != 0) {
					Iterator<?> it = orders.iterator();
					while (it.hasNext()) {
						OrderBean order = (OrderBean) it.next();
	            %>
	            <tr>
					<td><%=order.getId()%></td>
					<td><%=order.getId_user()%></td>
					<td><%=order.getId_product()%></td>
					<td><%=order.getQuantity()%></td>
	                <td>
	                    <a href="orders?action=details&id=<%= order.getId() %>#Dettagli">Dettagli</a>
	                    <a href="orders?action=delete&id=<%= order.getId() %>">Elimina</a>
	                </td>
	            </tr>
	            <% 
	                }
	            } else {
	            %>
	            <tr>
	                <td colspan="5">No users found.</td>
	            </tr>
	            <% } %>
	        </tbody>
	    </table>
		
		<!-- TABELLA DETTAGLI -->
		<% 
			OrderBean orderDetails = (OrderBean) request.getAttribute("order-details");
			if (orderDetails != null) {
		%>
			<br>
			<h2 id="Dettagli">Dettagli</h2>
			<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Nome Acquirente</th>
						<th>Nome Prodotto</th>
						<th>Prezzo</th>
						<th>Quantità</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<!-- Id Ordine -->
						<td><%=orderDetails.getId()%></td>
				
						<!-- Nome Acquirente -->
						<% 
							UserDAODataSource userDAO = new UserDAODataSource();
							UserBean userDetails = userDAO.doRetrieveByKey(orderDetails.getId_user()); 
						%>
						<td><%=userDetails.getName()%></td>
				
						<!-- Nome Prodotto -->
						<% 
							ProductDAODataSource productDAO = new ProductDAODataSource();
							ProductBean productDetails = productDAO.doRetrieveByKey(orderDetails.getId_product()); 
						%>
						<td><%=productDetails.getName()%></td>
				
						<!-- Prezzo -->
						<td><%=productDetails.getPrice()%></td>
						
						<!-- Quantità -->
						<td><%=orderDetails.getQuantity()%></td>
					</tr>
				</tbody>
			</table>
		<%}%>
	
		<!-- PARTE DELL'INSERIMENTO -->
		<br>
	    <h2>Aggiungi un ordine</h2>
	    <form method="post" action="orders?action=add">
	    	
	        <label>ID Utente:</label> 
			<input name="id_user" type="number" min="1" required><br><br>
			
			<label>ID Prodotto:</label> 
			<input name="id_product" type="number" min="1" required><br><br> 
			
			<label>Quantità:</label>
			<input name="quantity" type="number" min="1" required><br><br> 
			        
	        <input type="submit" value="Add">
	    </form>
    </div>
    
    <%@ include file="_footer.html" %>
</body>
</html>