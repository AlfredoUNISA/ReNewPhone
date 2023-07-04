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
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Admin - Panel</title>
</head>
<body>

	<div class="content">
		<h1>Pannello di amministrazione</h1>
		<form method="get" action="admin_orders.jsp">
			<input type="submit" value="Visualizza ordini">
		</form>

		<form method="get" action="admin_add.jsp">
			<input type="submit" value="Aggiungi elemento">
		</form>

		<form method="get" action="admin_hide-show.jsp">
			<input type="submit" value="Mostra/Nascondi elemento">
		</form>

		<form method="get" action="admin_modify.jsp">
			<input type="submit" value="Modifica elemento">
		</form>


	</div>

	<%@ include file="_footer.html"%>
</body>
</html>