<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/content.css">
  <title>Contatti</title>
</head>
<body>
  <%@ include file="_header.html" %>

  <div class="content">
    <h1>Contact</h1>
    <h4> Se riscontri dei problemi in fase d'acquisto o hai dubbi, non esitare a contattarci.</h4>
    <h4>compila il modulo inserendo la tua email, ti risponderemo il prima possibile</h4>
    <form action="messaggio_inviato.jsp" method="post">
      <input type="text" name="name" placeholder="Inserisci il tuo nome">
      <input type="email" name="email" placeholder="Inserisci la tua email">
      <textarea name="message" placeholder="Scrivi qui il tuo messaggio"></textarea>
      <input type="submit" value="Invia">
    </form>
    
    <p> il servizio è attivo 7 giorni su 7 dalle 8 alle 18 </p>
  </div>

  <%@ include file="_footer.html" %>
</body>
</html>
