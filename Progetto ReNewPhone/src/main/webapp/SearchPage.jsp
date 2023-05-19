<%@ page import="rnp.ProductDAODataSource"%>
<%@ page import="rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page contentType="text/html; charset=UTF-8"%>

<%-- 
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>

<%
String query = request.getParameter("q");
if (query == null)
	query = "";
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/style.css">
<title>Ricerca</title>
</head>
<body>
	<%@ include file="_header.jsp"%>

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
			Iterator<?> it = searchResults.iterator();
			while (it.hasNext()) {
				ProductBean bean = (ProductBean) it.next();
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