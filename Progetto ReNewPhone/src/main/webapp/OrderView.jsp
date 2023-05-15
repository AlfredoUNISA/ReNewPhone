<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page contentType="text/html; charset=ISO-8859-1"%>

<%
	Collection<?> orders = (Collection<?>) request.getAttribute("orders");

	if(orders == null) {
		response.sendRedirect("./orders");	
		return;
	}
	
	OrderBean order = (OrderBean) request.getAttribute("order");
	
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/content.css">
	<title>Ordini</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		<h2>Ordini Effettuati</h2>
		
		<table border="1">
			<tr>
				<th>Id <a href="orders?sort=id">Sort</a></th>
				<th>Id Utente <a href="orders?sort=id_user">Sort</a></th>
				<th>Id Prodotto <a href="orders?sort=id_product">Sort</a></th>
				<th>Quantità Ordinata <a href="orders?sort=quantity">Sort</a></th>
				<th>Azione</th>
			</tr>
			<%
				if (orders != null && orders.size() != 0) {
					Iterator<?> it = orders.iterator();
					while (it.hasNext()) {
						OrderBean iter_order_bean = (OrderBean) it.next();
			%>
			<tr>
				<td><%=iter_order_bean.getId()%></td>
				<td><%=iter_order_bean.getId_user()%></td>
				<td><%=iter_order_bean.getId_product()%></td>
				<td><%=iter_order_bean.getQuantity()%></td>
				<td><a href="orders?action=delete&id=<%=iter_order_bean.getId()%>">Elimina</a><br>
					<a href="orders?action=read&id=<%=iter_order_bean.getId()%>#Dettagli">Dettagli</a></td>
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">Non ci sono ordini</td>
			</tr>
			<%
				}
			%>
		</table>

		<h2 id="Dettagli">Dettagli</h2>
		<%
			if (order != null) {
		%>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Nome Acquirente</th>
				<th>Nome Prodotto</th>
				<th>Prezzo</th>
				<th>Quantità</th>
			</tr>
			<tr>
				<!-- Id Ordine -->
				<td><%=order.getId()%></td>
				
				<!-- Nome Acquirente -->
				<% 
					UserDAODataSource userDAO = new UserDAODataSource();
					UserBean user_bean = userDAO.doRetrieveByKey(order.getId_user()); 
				%>
				<td><%=user_bean.getName()%></td>
				
				<!-- Nome Prodotto -->
				<% 
					ProductDAODataSource productDAO = new ProductDAODataSource();
					ProductBean product_bean = productDAO.doRetrieveByKey(order.getId_product()); 
				%>
				<td><%=product_bean.getName()%></td>
				
				<!-- Prezzo -->
				<td><%=product_bean.getPrice()%></td>
				
				<!-- Quantità -->
				<td><%=order.getQuantity()%></td>
			</tr>
		</table>
		<%
			}
		%>
		
		<!-- TODO: fixare (null parsing) -->
		<h2>Inserisci</h2>
		<form action="orders" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="id_user">ID Utente:</label><br>
    		<input name="id_user" type="number" required><br>

    		<label for="id_product">ID Prodotto:</label><br>
    		<input name="id_product" type="number" required><br>

    		<label for="quantity">Quantità:</label><br>
   			<input name="quantity" type="number" min="1" required><br>

			<input type="submit" value="Add">
			<input type="reset" value="Reset">

		</form>
		
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>