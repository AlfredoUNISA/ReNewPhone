<%@ page import="rnp.ProductDAODataSource"%>
<%@ page import="rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page contentType="text/html; charset=ISO-8859-1"%>

<%
	Collection<?> productsColl = (Collection<?>) request.getAttribute("products");
	
	if (productsColl == null) {
		response.sendRedirect("./search");
		return;
	}
	
	List<ProductBean> productBeans = new ArrayList<>();
	for (Object product : productsColl) {
		productBeans.add((ProductBean) product);
	}
	
	List<ProductBean> searchResults = (List<ProductBean>) request.getAttribute("searchResults");
	
	String searchTerm = request.getParameter("searchTerm");
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/content.css">
<title>Ricerca</title>
</head>
<body>
	<%@ include file="_header.html"%>

	<div class="content">
		<h1>Ricerca prodotti</h1>
		<form action="search" method="get">
			<input type="hidden" name="action" value="search">
			<input type="text" name="searchTerm" value="<%=searchTerm%>">
			<button type="submit">Cerca</button>
		</form>

		<%
		if (searchTerm != null && searchTerm.trim().length() > 0) {
		%>
		<h2>Risultati della ricerca:</h2>
		<%
		if (searchResults.isEmpty()) {
		%>
		<p>
			Nessun risultato trovato per "<%=searchTerm%>."
		</p>
		<%
		} else {
		%>
		<ul>
			<%
			for (ProductBean result : searchResults) {
			%>
			<li><%=result.getName()%> - <%=result.getDescription()%></li>
			<%
			}
			%>
		</ul>
		<%
		}
		%>
		<%
		}
		%>

		<h2>Tutti i prodotti:</h2>
		<ul>
			<%
			for (ProductBean product : productBeans) {
			%>
			<li><%=product.getName()%> - <%=product.getDescription()%></li>
			<%
			}
			%>
		</ul>




	</div>

	<%@ include file="_footer.html"%>
</body>
</html>