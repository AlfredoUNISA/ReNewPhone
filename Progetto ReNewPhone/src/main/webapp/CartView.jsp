<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.CartBean, rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<head>
	<!-- /my-cart -->
	<link rel="stylesheet" href="css/style.css">
	<title>Carrello</title>
</head>
<body>
	<%@ include file="_header.jsp" %>
	
	<div class="content">
	    
	    <h2>Utente: "<%= CURRENT_USER_BEAN.getName() %> <%= CURRENT_USER_BEAN.getSurname() %>" (id = <%= CURRENT_USER_ID %>)</h2>
	    
	    <!-- TABELLA PRINCIPALE -->
	    <table>
	        <thead>
	            <tr>
	                <th>Nome Articolo</th>
	                <th>Id Articolo</th>
					<th>Prezzo (singolo articolo)</th>
					<th>Quantità Aggiunta</th>
	                <th><i>Azioni</i></th>
	            </tr>
	        </thead>
	        <tbody>
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> cart = (Collection<?>) request.getAttribute("cart");
	            ProductDAODataSource productDAO = new ProductDAODataSource();
	            
	            int sum = 0;
	            
	            // ITERAZIONE
	            if (cart != null && cart.size() != 0) {
					Iterator<?> it = cart.iterator();
					while (it.hasNext()) {
						CartBean cart_bean = (CartBean) it.next();
						ProductBean product_bean = productDAO.doRetrieveByKey(cart_bean.getId_product());
	            %>
	            <tr>
					<td><%=product_bean.getName()%></td>
					<td><%=product_bean.getId()%></td>
					<td><%=product_bean.getPrice()%></td>
					<td><%=cart_bean.getQuantity()%></td>
	                <td>
	                    <a href="my-cart?action=delete&user=<%=CURRENT_USER_BEAN.getId()%>&product=<%=product_bean.getId()%>">Elimina</a>
	                </td>
	            </tr>
	            <% 
	            	sum += product_bean.getPrice() * cart_bean.getQuantity();
	                }
	            } else {
	            %>
	            <tr>
	                <td colspan="5">Nessun prodotto nel carrello.</td>
	            </tr>
	            <% } %>
	        </tbody>
	    </table>
		
		<h3>Totale: <%=sum%></h3>
		
		
		<!-- Pulsante per la finalizzazione dell'ordine -->
		<form method="post" action="my-cart">
    		<input type="hidden" name="action" value="finalize">
    		<input type="hidden" name="total" value="<%=sum%>">
    		<input type="hidden" name="user" value="<%=CURRENT_USER_ID%>">
    		<input type="submit" value="Finalizza ordine">
		</form>
	
		<!-- PARTE DELL'INSERIMENTO -->
		<br>
	    <h2>Aggiungi un prodotto al carrello</h2>
	    <form method="post" action="my-cart">
	    	<input type="hidden" name="action" value="add">
	    	<input type="hidden" name="user" value="<%=CURRENT_USER_ID%>">
	    	
	        <label>Id prodotto:</label>
	        <input type="number" name="product" min="1" required><br><br> 
			
			<label>Quantità:</label>
	        <input type="number" name="quantity" min="1" value="1" required><br><br>
	        
	        <input type="submit" value="Add">
	    </form>
    </div>
    
    <%@ include file="_footer.html" %>
</body>
</html>