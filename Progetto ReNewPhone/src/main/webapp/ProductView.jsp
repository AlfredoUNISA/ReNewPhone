<%@ page contentType="text/html; charset=UTF-8" import="java.util.*,rnp.ProductBean"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");

	if(products == null) {
		response.sendRedirect("./products");	
		return;
	}
	
	ProductBean product = (ProductBean) request.getAttribute("product");
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
		<h2>Prodotti Disponibili</h2>

		<button id="refresh">
			<a href="products">Ricarica</a>
		</button>
		<table border="1">
			<tr>
				<th>Id <a href="products?sort=id">Sort</a></th>
				<th>Nome <a href="products?sort=name">Sort</a></th>
				<th>Descrizione <a href="products?sort=description">Sort</a></th>
				<th>Azione</th>
			</tr>
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ProductBean bean = (ProductBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDescription()%></td>
				<td><a href="products?action=delete&id=<%=bean.getId()%>">Elimina</a><br>
					<a href="products?action=read&id=<%=bean.getId()%>#Dettagli">Dettagli</a></td>
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">Non ci sono prodotti</td>
			</tr>
			<%
				}
			%>
		</table>

		<h2 id="Dettagli">Dettagli</h2>
		<%
			if (product != null) {
		%>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Descrizione</th>
				<th>Prezzo</th>
				<th>Quantit�</th>
				<th>Colore</th>
				<th>Brand</th>
				<th>Categoria</th>
				<th>Condizione</th>
			</tr>
			<tr>
				<td><%=product.getId()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getDescription()%></td>
				<td><%=product.getPrice()%></td>
				<td><%=product.getQuantity()%></td>
				<td><%=product.getColor()%></td>
				<td><%=product.getBrand()%></td>
				<td><%=product.getCategory()%></td>
				<td><%=product.getState()%></td>
			</tr>
		</table>
		<%
			}
		%>
		<h2>Inserisci</h2>
		<form action="products" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="name">Nome:</label><br> 
			<input name="name" type="text" maxlength="20" required placeholder="Inserisci nome"><br> 

			<label for="description">Descrizione:</label><br>
			<textarea name="description" maxlength="100" rows="3" required placeholder="Inserisci descrizione"></textarea><br>

			<label for="price">Prezzo:</label><br> 
			<input name="price" type="number" min="0" value="0" required><br>

			<label for="quantity">Quantit�:</label><br> 
			<input name="quantity" type="number" min="1" value="1" required><br>
			
			<label for="color">Colore:</label><br>
			<textarea name="color" maxlength="20" required placeholder="Inserisci colore"></textarea><br>
			
			<label for="brand">Brand:</label><br>
			<textarea name="brand" maxlength="20" required placeholder="Inserisci brand"></textarea><br>
			
			<label for="category">Categoria:</label><br>
			<textarea name="category" maxlength="20" required placeholder="Inserisci categoria"></textarea><br>
			
			<label for="state">Condizione:</label><br>
			<textarea name="state" maxlength="20" required placeholder="Inserisci condizione"></textarea><br>

			<input type="submit" value="Add">
			<input type="reset" value="Reset">

		</form>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>