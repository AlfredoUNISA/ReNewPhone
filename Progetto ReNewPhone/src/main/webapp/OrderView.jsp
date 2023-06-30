<%@ page import="rnpDAO.ItemsOrderDAODataSource"%>
<%@ page import="rnpDAO.OrderDAODataSource,rnpDAO.UserDAODataSource,rnpDAO.ProductDAODataSource"%>
<%@ page import="rnpBean.ItemOrderBean,rnpBean.OrderBean,rnpBean.UserBean,rnpBean.ProductBean"%>
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
<%
	if(!IS_CURRENT_USER_REGISTRED)
		response.sendRedirect("login.jsp");
%>

<!DOCTYPE html>
<html>
<head>
	<!-- /orders -->
	<title>Order List</title>
</head>
<body>
	
	<div class="content">
	    <h2>Lista Ordini</h2>
	    
	    <!-- TABELLA PRINCIPALE -->
	    <table>
	        <thead>
	            <tr>
	            	<!-- TODO: Sorting -->
	                <th>Id <a href="orders?sort=id">Sort</a></th>
					<th>Email Utente <a href="orders?sort=">Sort</a></th> 
					<th>Totale Ordine <a href="orders?sort=">Sort</a></th>
					<th><i>Azione</i></th>
	            </tr>
	        </thead>
	        <tbody>
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> orders = (Collection<?>) request.getAttribute("orders");
				UserDAODataSource userDAO = new UserDAODataSource();
				ItemsOrderDAODataSource itemsOrderDAO = new ItemsOrderDAODataSource();
	            
	            // ITERAZIONE
	            if (orders != null && orders.size() != 0) {
					Iterator<?> it = orders.iterator();
					while (it.hasNext()) {
						OrderBean order = (OrderBean) it.next();
						if(CURRENT_USER_BEAN.getId()==order.getId_user() || IS_CURRENT_USER_ADMIN==true){
	            %>
		            <tr>
		            	<!-- Id Ordine -->
						<td><%=order.getId()%></td>
						
						<!-- Email Acquirente -->
						<% 
							UserBean user = userDAO.doRetrieveByKey(order.getId_user()); 
						%>
						<td><%=user.getEmail()%></td>
						
						<!-- Totale Ordine -->
						<td><%=order.getTotal()%></td>
						
		                <td>
		                    <a href="orders?action=details&id=<%= order.getId() %>#Dettagli">Dettagli</a>
		                    <a href="orders?action=delete&id=<%= order.getId() %>">Elimina</a>
		                </td>
		            </tr>
	            <% 
	                }
					}
	            } else {
	            %>
	            <tr>
	                <td colspan="4">Nessun ordine trovato.</td>
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
			
			<h3>Dettagli Utente</h3>
			<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Email</th>
						<th>Indirizzo</th>
						<th>Città</th>
						<th>Cap</th>
						<th>Telefono</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<% UserBean userDetails = userDAO.doRetrieveByKey(orderDetails.getId_user()); %>
						<td><%=userDetails.getId()%></td>
						<td><%=userDetails.getName()%></td>
						<td><%=userDetails.getSurname()%></td>
						<td><%=userDetails.getEmail()%></td>
						<td><%=userDetails.getAddress()%></td>
						<td><%=userDetails.getCity()%></td>
						<td><%=userDetails.getCap()%></td>
						<td><%=userDetails.getPhone()%></td>
					</tr>
				</tbody>
			</table>
			
			<h3>Dettagli Prodotti</h3>
			<table>
				<thead>
					<tr>
						<th>ID</th>
		                <th>Nome</th>
		                <th>Ram</th>
		                <th>Dimensione Display</th>
		                <th>Memoria Interna</th>
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
						<% 
						Collection<?> itemsInsideOrder = (Collection<?>) itemsOrderDAO.doRetrieveByOrder(orderDetails.getId());
			            ProductDAODataSource productDAO = new ProductDAODataSource();
						
			            // ITERAZIONE
			            if (itemsInsideOrder != null && itemsInsideOrder.size() != 0) {
							Iterator<?> it = itemsInsideOrder.iterator();
							while (it.hasNext()) {
								ItemOrderBean item = (ItemOrderBean) it.next();
								ProductBean productDetails = productDAO.doRetrieveByKey(item.getId_product());
						%>
						<tr>
							<td><%= productDetails.getId() %></td>
	                		<td><%= productDetails.getName() %></td>
	                		<td><%=	productDetails.getRam()%></td>
							<td><%=	productDetails.getDisplay_size()%></td>
							<td><%=	productDetails.getStorage()%></td>
	                		<td><%= productDetails.getPrice() %></td>
	                		<td><%= productDetails.getQuantity() %></td>
	                		<td><%= productDetails.getColor() %></td>
	                		<td><%= productDetails.getBrand() %></td>
	                		<td><%= productDetails.getYear() %></td>
							<td><%= productDetails.getCategory() %></td>
							<td><%= productDetails.getState() %></td>
						</tr>
						<%	}
			            }
						%>
				</tbody>
			</table>
		<%}%>
    </div>
    
    <%@ include file="_footer.html" %>
</body>
</html>