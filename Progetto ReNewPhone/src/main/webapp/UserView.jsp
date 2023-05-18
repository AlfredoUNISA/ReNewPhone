<%@ page import="rnp.OrderDAODataSource, rnp.UserDAODataSource, rnp.ProductDAODataSource"%>
<%@ page import="rnp.OrderBean, rnp.UserBean, rnp.ProductBean"%>
<%@ page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<!-- /users -->
	<title>User List</title>
</head>
<body>
	<%@ include file="_header.html" %>
	
	<div class="content">
	    <h2>Lista Utenti</h2>
	    
	    <!-- TABELLA PRINCIPALE -->
	    <table>
	        <thead>
	            <tr>
	                <th>Id <a href="users?sort=id">Sort</a></th>
					<th>Nome <a href="users?sort=name">Sort</a></th>
					<th>Email <a href="users?sort=email">Sort</a></th>
	                <th><i>Azioni</i></th>
	            </tr>
	        </thead>
	        <tbody>
	            <%
	            // OTTENIMENTO DI TUTTE LE RIGHE DALLA TABLE DEL DATABASE
	            Collection<?> users = (Collection<?>) request.getAttribute("users");
	            
	            // ITERAZIONE
	            if (users != null && users.size() != 0) {
					Iterator<?> it = users.iterator();
					while (it.hasNext()) {
						UserBean user = (UserBean) it.next();
	            %>
	            <tr>
	                <td><%=user.getId()%></td>
					<td><%=user.getName()%></td>
					<td><%=user.getEmail()%></td>
	                <td>
	                    <a href="users?action=details&id=<%= user.getId() %>#Dettagli">Dettagli</a>
	                    <a href="users?action=delete&id=<%= user.getId() %>">Elimina</a>
	                </td>
	            </tr>
	            <% 
	                }
	            } else {
	            %>
	            <tr>
	                <td colspan="4">No users found.</td>
	            </tr>
	            <% } %>
	        </tbody>
	    </table>
		
		<!-- TABELLA DETTAGLI -->
		<% 
			UserBean userDetails = (UserBean) request.getAttribute("user-details");
			if (userDetails != null) {
		%>
			<br>
			<h2 id="Dettagli">Dettagli</h2>
			<table>
				<thead>
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
				</thead>
				<tbody>
					<tr>
						<td><%=userDetails.getId()%></td>
						<td><%=userDetails.getName()%></td>
						<td><%=userDetails.getEmail()%></td>
						<td><%=userDetails.getPassword()%></td>
						<td><%=userDetails.getAddress()%></td>
						<td><%=userDetails.getCity()%></td>
						<td><%=userDetails.getCap()%></td>
						<td><%=userDetails.getPhone()%></td>
					</tr>
				</tbody>
			</table>
		<%}%>
	
		<!-- PARTE DELL'INSERIMENTO -->
		<br>
	    <h2>Aggiungi un utente</h2>
	    <form method="post" action="users?action=add">
	    	
	        <label>Nome:</label> 
			<input name="name" type="text" maxlength="25" placeholder="Inserisci nome" required><br><br> 
			
			<label>Email:</label>
			<input name="email" type="email" maxlength="100" placeholder="Inserisci email" required><br><br> 
			
			<label>Password:</label>
			<input name="password" type="password" maxlength="50" placeholder="Inserisci password" required><br><br> 
			
			<label>Indirizzo:</label>
			<textarea name="address" maxlength="60" placeholder="Inserisci indirizzo" required></textarea><br><br> 
			
			<label>Città:</label>
			<input name="city" type="text" maxlength="35" placeholder="Inserisci città" required><br><br> 
			
			<label>CAP:</label>
			<input name="cap" type="text" maxlength="7" placeholder="Inserisci CAP" required><br><br> 
			
			<label>Telefono:</label>
			<input name="phone" type="text" maxlength="20" placeholder="Inserisci numero di telefono" required><br><br>
	        
	        <input type="submit" value="Add">
	    </form>
    </div>
    
    <%@ include file="_footer.html" %>
</body>
</html>