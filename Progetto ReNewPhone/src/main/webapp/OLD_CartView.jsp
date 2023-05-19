<%@ page import="rnp.CartDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.CartBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>
<%@ page contentType="text/html; charset=ISO-8859-1"%>

<%
	String userParam = request.getParameter("usr");
	
	UserDAODataSource userDAO = null;
	UserBean user_bean = null;
	
	ProductDAODataSource productDAO = null;
	
	int id_user = -1;
	if (userParam != null) {
		id_user = Integer.parseInt(userParam);
		
		productDAO = new ProductDAODataSource();
		
		userDAO = new UserDAODataSource();
		user_bean = userDAO.doRetrieveByKey(id_user); 
	}
	
	Collection<?> cart = (Collection<?>) request.getAttribute("cart");
	if (cart == null){
		// attenzione ai carrelli vuoti, evitare il loop
	}
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/style.css">
	<title><%= user_bean.getName() %>'s cart</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		<%
		if (cart != null && !cart.isEmpty() && id_user != -1) {
		%>
		<h2>Utente: "<%= user_bean.getName() %>" (<%= id_user %>)</h2>
		<table>
			<tr>
				<th>Nome Articolo</th>
				<th>Prezzo (singolo articolo)</th>
				<th>Quantità Aggiunta</th>
				<th>Azioni</th>
			</tr>
			<%
			Iterator<?> it = cart.iterator();
			while (it.hasNext()) {
				CartBean bean = (CartBean) it.next();
				ProductBean product_bean = productDAO.doRetrieveByKey(bean.getId_product());
			%>
			<tr>
				<td><%=product_bean.getName()%></td>
				<td><%=product_bean.getPrice()%></td>
				<td><%=bean.getQuantity()%></td>
				<td><a href="javascript:void(0);" onclick="if(confirm('Sei sicuro di voler eliminare questo prodotto dal carrello?')){location.href='my-cart?action=delete&id_product=<%=bean.getId_product()%>';}">Elimina</a><br>
					<a href="my-cart?action=read&id=<%=bean.getId_product()%>">Dettagli</a></td>
			</tr>
		<%
			}
		}
		else {
		%>
			<p>Errore</p>
		<%
		}
		%>
		</table>
		
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>