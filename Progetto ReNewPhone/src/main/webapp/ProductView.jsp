<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*,rnp.ProductBean"%>

<%
	Collection<?> products = (Collection<?>) request.getAttribute("products");

	if(products == null) {
		response.sendRedirect("./product");	
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
		<h2>Products</h2>

		<button id="refresh">
			<a href="product">List</a>
		</button>
		<table border="1">
			<tr>
				<th>Code <a href="product?sort=code">Sort</a></th>
				<th>Name <a href="product?sort=name">Sort</a></th>
				<th>Description <a href="product?sort=description">Sort</a></th>
				<th>Action</th>
			</tr>
			<%
				if (products != null && products.size() != 0) {
					Iterator<?> it = products.iterator();
					while (it.hasNext()) {
						ProductBean bean = (ProductBean) it.next();
			%>
			<tr>
				<td><%=bean.getCode()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDescription()%></td>
				<td><a href="product?action=delete&id=<%=bean.getCode()%>">Delete</a><br>
					<a href="product?action=read&id=<%=bean.getCode()%>">Details</a></td>
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">No products available</td>
			</tr>
			<%
				}
			%>
		</table>

		<h2>Details</h2>
		<%
			if (product != null) {
		%>
		<table border="1">
			<tr>
				<th>Code</th>
				<th>Name</th>
				<th>Description</th>
				<th>Price</th>
				<th>Quantity</th>
			</tr>
			<tr>
				<td><%=product.getCode()%></td>
				<td><%=product.getName()%></td>
				<td><%=product.getDescription()%></td>
				<td><%=product.getPrice()%></td>
				<td><%=product.getQuantity()%></td>
			</tr>
		</table>
		<%
			}
		%>
		<h2>Insert</h2>
		<form action="product" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="name">Name:</label><br> 
			<input name="name" type="text" maxlength="20" required placeholder="enter name"><br> 

			<label for="description">Description:</label><br>
			<textarea name="description" maxlength="100" rows="3" required placeholder="enter description"></textarea><br>

			<label for="price">Price:</label><br> 
			<input name="price" type="number" min="0" value="0" required><br>

			<label for="quantity">Quantity:</label><br> 
			<input name="quantity" type="number" min="1" value="1" required><br>

			<input type="submit" value="Add">
			<input type="reset" value="Reset">

		</form>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>