<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%-- 
	CURRENT_USER_ID   : int 			  -> ID utente corrente
	CURRENT_USER_BEAN : UserBean 		  -> Bean per utente corrente
--%>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/style.css">
  <title>Contatti</title>
</head>
<body>
  <%@ include file="_header.jsp" %>

  <div class="content">
    <h1>Contact</h1>
    <h4>Se riscontri dei problemi in fase d'acquisto o hai dubbi, non esitare a contattarci.</h4>
    <h4>Compila il modulo inserendo la tua email, ti risponderemo il prima possibile</h4>
    <form action="messaggio_inviato.jsp" method="post">
      <input type="text" name="name" required placeholder="Inserisci il tuo nome">
      <input type="email" name="email" required placeholder="Inserisci la tua email">
      <textarea name="message" required  placeholder="Scrivi qui il tuo messaggio"></textarea>
      <input type="submit" value="Invia">
    </form>
    
    <p> Il servizio � attivo 7 giorni su 7 dalle 8:00 alle 18:00 </p>
  </div>

  <%@ include file="_footer.html" %>
</body>
</html>
