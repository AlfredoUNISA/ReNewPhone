<%@ page contentType="text/html; charset=ISO-8859-1" import="java.util.*,rnp.UserBean,rnp.UserDAODataSource"%>

<%
	Collection<?> users = (Collection<?>) request.getAttribute("users");

	if(users == null) {
		response.sendRedirect("./users");	
		return;
	}
	
	UserBean user = (UserBean) request.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="css/content.css">
	<title>Users</title>
</head>
<body>
	<%@ include file="_header.html" %>

	<div class="content">
		<h2>Utenti Registrati</h2>
		
		<table border="1">
			<tr>
				<th>Id <a href="users?sort=id">Sort</a></th>
				<th>Nome <a href="users?sort=name">Sort</a></th>
				<th>Email <a href="users?sort=email">Sort</a></th>
				<th>Azione</th>
			</tr>
			<%
				if (users != null && users.size() != 0) {
					Iterator<?> it = users.iterator();
					while (it.hasNext()) {
						UserBean bean = (UserBean) it.next();
			%>
			<tr>
				<td><%=bean.getId()%></td>
				<td><%=bean.getName()%></td>
				<td><%=bean.getEmail()%></td>
				<td><a href="javascript:void(0);" onclick="if(confirm('Sei sicuro di voler eliminare questo utente?')){location.href='users?action=delete&id=<%=bean.getId()%>';}">Elimina</a><br>
					<a href="users?action=read&id=<%=bean.getId()%>#Dettagli">Dettagli</a></td>
			</tr>
			<%
					}
				} else {
			%>
			<tr>
				<td colspan="6">Non ci sono prodotti</td>
			</tr>
			<%
				}
			%>
		</table>

		<%
			Boolean error = (Boolean) request.getAttribute("error-email");
			if (user != null) {
		%>
		<h2 id="Dettagli">Dettagli</h2>
		<table border="1">
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Email</th>
				<th>Password</th>
				<th>Indirizzo</th>
				<th>Città</th>
				<th>Cap</th>
				<th>Telefono</th>
			</tr>
			<tr>
				<td><%=user.getId()%></td>
				<td><%=user.getName()%></td>
				<td><%=user.getEmail()%></td>
				<td><%=user.getPassword()%></td>
				<td><%=user.getAddress()%></td>
				<td><%=user.getCity()%></td>
				<td><%=user.getCap()%></td>
				<td><%=user.getPhone()%></td>

			</tr>
		</table>
		<%
			} else if (error != null && error == true) {
		%>
			<h2 style="color:red;">Email già presente</h2>
		
		<%
			}
		%>
		<h2>Inserisci</h2>
		<form action="users" method="post">
			<input type="hidden" name="action" value="insert"> 

			<label for="name">Nome:</label><br> 
			<input name="name" type="text" maxlength="25" required placeholder="Inserisci nome"><br> 
			
			<label for="email">Email:</label><br> 
			<input name="email" type="email" maxlength="100" required placeholder="Inserisci email"><br> 
			
			<label for="password">Password:</label><br> 
			<input name="password" type="password" maxlength="50" required placeholder="Inserisci password"><br> 
			
			<label for="address">Indirizzo:</label><br> 
			<textarea name="address" maxlength="60" required placeholder="Inserisci indirizzo"></textarea><br> 
			
			<label for="city">Città:</label><br> 
			<input name="city" type="text" maxlength="35" required placeholder="Inserisci città"><br> 
			
			<label for="cap">CAP:</label><br> 
			<input name="cap" type="text" maxlength="7" required placeholder="Inserisci CAP"><br> 
			
			<label for="phone">Telefono:</label><br> 
			<input name="phone" type="text" maxlength="20" required placeholder="Inserisci numero di telefono"><br>

			<input type="submit" value="Add">
			<input type="reset" value="Reset">
			
	
		</form>
	</div>

	<%@ include file="_footer.html" %>
</body>
</html>