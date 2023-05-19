<%@page import="rnp.UserDAODataSource"%>
<%@page import="rnp.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<%@ include file="_header.jsp" %>
<head>
	<meta charset="ISO-8859-1">
	<%if(CURRENT_USER_ID > 0) {%>
		<title><%=CURRENT_USER_BEAN.getName()%>'s page</title>
	<%} else {%>
		<title>Error while loading up user!</title>
	<%} %>
</head>
<body>
	<div class="content">
		<h1>Hello, <%=CURRENT_USER_BEAN.getName()%></h1>
		<form action="logout.jsp" method="post">
    			<input type="submit" value="Logout">
		</form><br>
	
	</div>
	<%@ include file="_footer.html" %>
</body>
</html>