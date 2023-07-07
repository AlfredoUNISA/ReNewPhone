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
if (!IS_CURRENT_USER_ADMIN) {
	// Manda alla pagina di errore 404 per motivi di sicurezza
	response.sendError(HttpServletResponse.SC_NOT_FOUND);
}
	
	//System.out.println("[admin_orders] CURRENT_USER_ID: " + CURRENT_USER_ID);
	//System.out.println("[admin_orders] IS_CURRENT_USER_ADMIN: " + IS_CURRENT_USER_ADMIN);
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<title>Admin - Orders</title>
</head>
<body>

	<div class="content">
		<h1>Ordini Effettuati</h1>

		<!-- TABELLA PRINCIPALE -->
		<table>
			<thead>
				<tr>
					<th>ID Ordine</th>
					<th>ID Utente</th>
					<th>Totale Ordine</th>
					<th>Data Ordine</th>
					<th>Dettagli</th>
				</tr>
			</thead>
			<tbody>
				<!-- Inserisci qui gli ordini -->
			</tbody>
		</table>
		<div class="pagination"></div>
		<br>
		
		<div class="userDetails"></div>
		<br>
		<div class="orderDetails"></div>
		

	</div>
	<script type="text/javascript"><%@include file="js/ordersAdminAjax.js" %></script>
	
	<%@ include file="_footer.html"%>
</body>
</html>