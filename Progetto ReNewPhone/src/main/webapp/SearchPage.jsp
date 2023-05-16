<%@ page import="rnp.ProductDAODataSource"%>
<%@ page import="rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page contentType="text/html; charset=ISO-8859-1"%>

<%
String query = request.getParameter("q");
if (query == null)
	query = "";
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
			<input type="text" name="q" value="<%=query%>">
			<button type="submit">Cerca</button>
		</form>

		<%
		Collection<?> searchResults = (Collection<?>) request.getAttribute("searchResults");
		%>
		<%
		if (searchResults != null && !searchResults.isEmpty()) {
		%>
		<table>
			<tr>
				<th>Codice</th>
				<th>Nome</th>
				<th>Descrizione</th>
				<th>Prezzo</th>
			</tr>
			<%
			System.out.println("NELLA JSP:");
			Iterator<?> it = searchResults.iterator();
			while (it.hasNext()) {
				ProductBean bean = (ProductBean) it.next();
				System.out.println("------");
			    System.out.println(bean);
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getDescription()%></td>
				<td><%=bean.getPrice()%></td>
			</tr>
			<%
			}
			%>
		</table>
		<%
		} else {
		%>
		<p>Nessun risultato trovato.</p>
		<%
		}
		%>

	</div>

	<%@ include file="_footer.html"%>
</body>
</html>