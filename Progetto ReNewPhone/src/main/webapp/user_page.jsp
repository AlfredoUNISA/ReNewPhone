<%@ page import="rnpDAO.UserDAODataSource"%>
<%@page import="rnpBean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>
<%@ include file="_header.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%if(IS_CURRENT_USER_REGISTRED) {%>
		<title><%=CURRENT_USER_BEAN.getName()%>'s page</title>
	<%} else {%>
		<title>Error while loading up user!</title>
	<%} %>
</head>
<body>
	<div class="content">
		<h1>Hello, <%=CURRENT_USER_BEAN.getName()%></h1>
		
		<p>IS_CURRENT_USER_ADMIN: <%= IS_CURRENT_USER_ADMIN %></p>
		<p>IS_CURRENT_USER_REGISTRED: <%= IS_CURRENT_USER_REGISTRED %></p>
		
		<table>
				<thead>
					<tr>
						<th>Id</th>
						<th>Nome</th>
						<th>Cognome</th>
						<th>Email</th>
						<th>Password</th>
						<th>Indirizzo</th>
						<th>Città</th>
						<th>Cap</th>
						<th>Telefono</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><%=CURRENT_USER_BEAN.getId()%></td>
						<td><%=CURRENT_USER_BEAN.getName()%></td>
						<td><%=CURRENT_USER_BEAN.getSurname()%></td>
						<td><%=CURRENT_USER_BEAN.getEmail()%></td>
						<td><%=CURRENT_USER_BEAN.getPassword()%></td>
						<td><%=CURRENT_USER_BEAN.getAddress()%></td>
						<td><%=CURRENT_USER_BEAN.getCity()%></td>
						<td><%=CURRENT_USER_BEAN.getCap()%></td>
						<td><%=CURRENT_USER_BEAN.getPhone()%></td>
					</tr>
				</tbody>
			</table>
		<form action="orders?sort=id" method="post">
		<%if(!IS_CURRENT_USER_ADMIN){ %>
    		<input type="submit" value="I Tuoi Ordini">
    	<% } %>
		</form><br>
		<form action="logout.jsp" method="post">
    			<input type="submit" value="Logout">
		</form><br>
	
	</div>
	<%@ include file="_footer.html" %>
</body>
</html>