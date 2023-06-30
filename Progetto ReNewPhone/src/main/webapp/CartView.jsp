<%@ page import="rnpDAO.OrderDAODataSource,rnpDAO.UserDAODataSource,rnpDAO.ProductDAODataSource"%>
<%@ page import="rnpBean.CartBean,rnpBean.OrderBean,rnpBean.UserBean,rnpBean.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp" %>

<%
	if(IS_CURRENT_USER_ADMIN)
		response.sendRedirect("index.jsp");
%>

<!DOCTYPE html>
<html>
<head>
	<!-- /my-cart -->
	<link rel="stylesheet" href="css/style.css">
	<title>Carrello</title>
</head>
<body>
	
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
	            int sum = 0;
	            if(IS_CURRENT_USER_REGISTRED) {
		            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
		            Collection<?> cart = (Collection<?>) request.getAttribute("cart");
		            ProductDAODataSource productDAO = new ProductDAODataSource();
		            
		            
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
		         <% }
	            } 
	            %>
	            <%--  
	            <%
	            else {
	            	// Utente non registrato, utilizza il carrello dal cookie

	                // Prendi il cookie "carrello" dalla richiesta
	                Cookie[] cookies = request.getCookies();
	                Cookie cartCookie = null;
	                if (cookies != null) {
	                    for (Cookie cookie : cookies) {
	                        if (cookie.getName().equals("cartCookie")) {
	                            cartCookie = cookie;
	                            break;
	                        }
	                    }
	                }
	                if (cartCookie != null) {
	                    String cartValue = cartCookie.getValue();
	                    String[] cartItems = cartValue.split("a");

	                    if (cartItems.length > 0) {
	                        ProductDAODataSource productDAO = new ProductDAODataSource();
	                        List<CartBean> cartList = new ArrayList<>();

	                        // Itera sui prodotti nel carrello
	                        for (String cartItem : cartItems) {
	                            String[] itemDetails = cartItem.split(":");
	                            int productId = Integer.parseInt(itemDetails[0]);
	                            int quantity = Integer.parseInt(itemDetails[1]);

	                            // Ottieni il prodotto dal database utilizzando l'ID del prodotto
	                            ProductBean product = productDAO.doRetrieveByKey(productId);

	                            // Crea un oggetto CartBean con il prodotto e la quantità
	                            CartBean cartBean = new CartBean();
	                            cartBean.setId_product(productId);
	                            cartBean.setQuantity(quantity);

	                            // Aggiungi l'oggetto CartBean alla lista
	                            cartList.add(cartBean);
	                        }

	                        // Visualizza i prodotti nel carrello utilizzando i dati dalla lista di CartBean
	                        for (CartBean cartBean : cartList) {
	                            int productId = cartBean.getId_product();
	                            int quantity = cartBean.getQuantity();

	                            // Ottieni il prodotto dal database utilizzando l'ID del prodotto
	                            ProductBean product = productDAO.doRetrieveByKey(productId);
							%>
							<tr>
								<td><%=product.getName()%></td>
								<td><%=product.getId()%></td>
								<td><%=product.getPrice()%></td>
								<td><%=quantity%></td>
		                		<!--
		                		<td>
		                    		<a href="my-cart?action=delete&user=<%=CURRENT_USER_BEAN.getId()%>&product=<%=product.getId()%>">Elimina</a>
		                		</td>
		                		-->
		            		</tr>
							<%
								sum += product.getPrice() * quantity;
	                        }
	                    } else {
	    		            %>
	    		            <tr>
	    		                <td colspan="5">Nessun prodotto nel carrello.</td>
	    		            </tr>
	    		         <% }
	                } //response.sendRedirect("my-cart");
	            } 
	            %>
	            --%>
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