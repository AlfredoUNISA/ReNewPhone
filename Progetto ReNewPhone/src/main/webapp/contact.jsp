<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
  <link rel="stylesheet" href="css/style.css">
  <title>Contatti</title>
</head>
<body>

  <div class="content">
    <h1>Contact</h1>
    <h4>Se riscontri dei problemi in fase d'acquisto o hai dubbi, non esitare a contattarci.</h4>
    <h4>Compila il modulo inserendo la tua email, ti risponderemo il prima possibile</h4>
    <form action="messaggio_inviato.jsp" method="post">
      <input type="text"  pattern="[a-zA-Z]*"name="name" required placeholder="Inserisci il tuo nome">
      <style> 
     input:invalid {
    animation: shake 600ms;
    color: rgb(255, 35, 35);
}

@keyframes shake {
    25% {
        transform: translateX(5px);
    }
    50% {
        transform: translateX(-5px);
    }
    75% {
        transform: translateX(5px);
    }
}
      
      </style>
      <input type="email" name="email" required placeholder="Inserisci la tua email">
      <textarea name="message" required  placeholder="Scrivi qui il tuo messaggio"></textarea>
      <input type="submit" value="Invia">
    </form>
    
    <p> Il servizio è attivo 7 giorni su 7 dalle 8:00 alle 18:00 </p>
  </div>

  <%@ include file="_footer.html" %>
</body>
</html>
