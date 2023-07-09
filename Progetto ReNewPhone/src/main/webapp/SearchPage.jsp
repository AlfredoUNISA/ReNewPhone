<%@ page import="rnp.DAO.ProductDAODataSource"%>
<%@ page import="rnp.Bean.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page contentType="text/html; charset=UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp"%>

<%
	String query = request.getParameter("q");
	if (query == null)
		query = "";
	%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="css/style.css">
<title>Ricerca</title>
</head>
<body>

	<div class="content">
		<h1>Ricerca prodotti</h1>
		<form action="search" method="get">
			<input type="text" name="q" value="<%=query%>">
			<input type="button" value="AVVIA RICERCA">
		</form>

		<%
		Collection<?> searchResults = (Collection<?>) request.getAttribute("searchResults");
		%>
		<%
		if (searchResults != null && !searchResults.isEmpty()) {
		%>
		<table>
			<tr>
				<th>Immagine</th>
				<th>Codice</th>
				<th>Nome</th>
				<th>Ram</th>
				<th>Dimensione Display</th>
				<th>Memoria Interna</th>
				<th>Prezzo</th>
			</tr>
			<%
			Iterator<?> it = searchResults.iterator();
			while (it.hasNext()) {
				ProductBean bean = (ProductBean) it.next();
			%>
			<tr>
				<td><img class="productImg" alt="<%=bean.getModel()%>" src="resources/<%=bean.getModel()%>.jpg"> </td>
				<td><%=bean.getId()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getRam()%></td>
				<td><%=bean.getDisplay_size()%></td>
				<td><%=bean.getStorage()%></td>
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