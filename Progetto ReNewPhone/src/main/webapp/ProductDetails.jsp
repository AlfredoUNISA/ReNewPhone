<%@ page
	import="rnp.DAO.OrderDAODataSource,rnp.DAO.UserDAODataSource,rnp.DAO.ProductDAODataSource"%>
<%@ page
	import="rnp.Bean.OrderBean,rnp.Bean.UserBean,rnp.Bean.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp"%>
<%
List<?> listProductBean = (List<?>) request.getAttribute("product-details-list");
//System.out.println("Lista in jsp:" + listProductBean);
ProductBean productSample = (ProductBean) listProductBean.get(0);
//System.out.println("ProductBean:" + productSample);
String model = productSample.getModel();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript"><%@include file="js/ProductDetail.js" %></script>
<title><%=productSample.getName()%></title>
</head>
<body>

	<div class="content">

		<i><div class="messaggio"><%=productSample.getName()%></div></i>

		<div class="detailContainer">
			<img class="detailImage" alt="<%=model%>"
				src="resources/<%=model%>.jpg">
			<div class="detailProduct">

				<div id="ricerca">
					<label for="brandName">Brand:</label> 
					<p id="brandName"><%=productSample.getBrand()%></p>
					
					<label for="storageSelect">Spazio interno:</label> 
					<select id="storageSelect" required>
						<option value="0">Seleziona prima un dispositivo</option>
					</select><br><br> 
					
					<label for="ramSelect">RAM:</label> 
					<select id="ramSelect" required>
						<option value="0">Seleziona prima uno spazio interno</option>
					</select><br><br> 
					
					<label for="displaySizesSelect">Schermo:</label>
					<select id="displaySizesSelect" required>
						<option value="0">Seleziona prima la ram</option>
					</select><br><br> 
					
					<label for="colorSelect">Colore:</label> 
					<select	id="colorSelect" required>
						<option value="0">Seleziona prima uno schermo</option>
					</select><br><br> 
					
					<label for="stateSelect">Condizione:</label> 
					<select id="stateSelect" required>
						<option value="0">Seleziona prima un colore</option>
					</select><br><br>
				</div>

				<div id="idContainer"></div>
				<div class="messaggio-prezzo">
					<div id="priceContainer"></div>
					<div id="remainingQuantityContainer"></div>
				</div>


				<div class="buyProduct">
					<h5>
						Seleziona la quantità: <select id="quantitySelect">
							<option id="quantityOption1" value="1">1</option>
							<option id="quantityOption2" value="2">2</option>
							<option id="quantityOption3" value="3">3</option>
							<option id="quantityOption4" value="4">4</option>
							<option id="quantityOption5" value="5">5</option>
						</select>
					</h5>
					<input type="button" id="addToCartBtn" value="Aggiungi al carrello">
				</div>
			</div>
		</div>
	</div>

	<%@ include file="_footer.html"%>
</body>
</html>