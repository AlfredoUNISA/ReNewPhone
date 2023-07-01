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
<script src="js/uploadImage.js"></script>
<title>Admin - Modify</title>
</head>
<body>

	<div class="content">



	</div>

	<%@ include file="_footer.html"%>
</body>
</html>