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
	<link rel="stylesheet" type="text/css" href="css/style.css" /> 
	<title>Homepage</title>
</head>
<body>

	<div class="content">
		<h1>Dettagli</h1>
			<br>
			<table class="DetailsTable">
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
		<%
		// OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
        Collection<?> products = (Collection<?>) request.getAttribute("product-details");
        
        // ITERAZIONE
        if (products != null && products.size() != 0) {
			Iterator<?> it = products.iterator();
			while (it.hasNext()) {
				ProductBean productDetails = (ProductBean) it.next();
       
		%>
		<!-- TABELLA DETTAGLI --> 
				<tbody>
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
		<%}  }
        	else {%>
		<h1> Impossibile trovare i dettagli del prodotto</h1>
		<%} %>
				</tbody>
			</table>
				<p style="color:darkred; font-weight:bold;"> ATTENZIONE le immagini sono solo a scopo illustrativo. il colore potrebbe variare</p>
		
		<p>Lorem ipsum dolor, sit amet consectetur adipisicing elit. Excepturi repellendus veniam illo explicabo repellat veritatis, quis eius. Distinctio mollitia eos eaque rem facere incidunt placeat pariatur, nobis ut deleniti consectetur.</p>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>