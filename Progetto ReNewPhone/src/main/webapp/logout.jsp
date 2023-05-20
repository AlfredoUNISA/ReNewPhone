<%@ page language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- 					Legenda Sessione
	CURRENT_USER_ID			  : int 	  -> ID utente corrente
	CURRENT_USER_BEAN		  : UserBean  -> Bean per utente corrente
	IS_CURRENT_USER_ADMIN	  : Bool 	  -> Boolean per sapere se l'user è admin
	IS_CURRENT_USER_REGISTRED : Bool	  -> Boolean per sapere se l'user è registrato
--%>

<%
    // Ottieni l'oggetto HttpSession
    request.getSession(false);
    
    // Verifica se la sessione esiste
    if (session != null) {
        // Invalida la sessione
        session.invalidate();
        
        // Disabilita il cookie di accesso automatico
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userCookie")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }
    
    // Reindirizza l'utente alla pagina di accesso o a un'altra pagina desiderata
    response.sendRedirect("index.jsp");
%>
