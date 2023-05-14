<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*,rnp.OrderBean"%>

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
	<title>Storage</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		<h2>Ordini Effettuati</h2>
		
		<table border="1">
			<tr>
				<th>Id | <a href="orders?sort=id">Sort</a></th>
				<th>Id Utente | <a href="orders?sort=id_user">Sort</a></th>
				<th>Id Prodotto | <a href="orders?sort=id_product">Sort</a></th>
				<th>Quantità Ordinata | <a href="orders?sort=quantity">Sort</a></th>
				<th>Azione</th>
			</tr>
			<%
				if (orders != null && orders.size() != 0) {
					Iterator<?> it = orders.iterator();
					while (it.hasNext()) {
						OrderBean bean = (OrderBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getId_user()%></td>
				<td><%=bean.getId_product()%></td>
				<td><%=bean.getQuantity()%></td>
				<td><a href="orders?action=delete&id=<%=bean.getId()%>">Elimina</a><br>
					<a href="orders?action=read&id=<%=bean.getId()%>#Dettagli">Dettagli</a></td>
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
			if (orders != null) {
		%>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Descrizione</th>
				<th>Prezzo</th>
				<th>Quantità </th>
				<th>Colore</th>
				<th>Brand</th>
				<th>Categoria</th>
				<th>Condizione</th>
			</tr>
			<tr>
			<!-- 
				<td><%=product.getId()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getDescription()%></td>
				<td><%=product.getPrice()%></td>
				<td><%=product.getQuantity()%></td>
				<td><%=product.getColor()%></td>
				<td><%=product.getBrand()%></td>
				<td><%=product.getCategory()%></td>
				<td><%=product.getState()%></td>
			 -->
			</tr>
		</table>
		<%
			}
		%>
		<h2>Inserisci</h2>
		<form action="products" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="name">Nome:</label><br> 
			<input name="name" type="text" maxlength="150" required placeholder="Inserisci nome"><br> 

			<label for="description">Descrizione:</label><br>
			<textarea name="description" maxlength="255" rows="3" required placeholder="Inserisci descrizione"></textarea><br>

			<label for="price">Prezzo:</label><br> 
			<input name="price" type="number" min="0" value="0" required><br>

			<label for="quantity">Quantità:</label><br> 
			<input name="quantity" type="number" min="1" value="1" required><br>
			
			<label for="color">Colore:</label><br>
			<textarea name="color" maxlength="20" required placeholder="Inserisci colore"></textarea><br>
			
			<label for="brand">Brand:</label><br>
			<textarea name="brand" maxlength="25" required placeholder="Inserisci brand"></textarea><br>
			
			<label for="category">Categoria:</label><br>
			<textarea name="category" maxlength="15" required placeholder="Inserisci categoria"></textarea><br>
			
			<label for="state">Condizione:</label><br>
			<textarea name="state" maxlength="12" required placeholder="Inserisci condizione"></textarea><br>

			<input type="submit" value="Add">
			<input type="reset" value="Reset">

		</form>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>