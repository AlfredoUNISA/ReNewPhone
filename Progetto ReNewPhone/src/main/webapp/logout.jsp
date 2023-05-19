<%@ page language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<%
    // Ottieni l'oggetto HttpSession
    request.getSession(false);
    
    // Verifica se la sessione esiste
    if (session != null) {
        // Invalida la sessione
        session.invalidate();
    }
    
    // Reindirizza l'utente alla pagina di accesso o a un'altra pagina desiderata
    response.sendRedirect("index.jsp");
%>
