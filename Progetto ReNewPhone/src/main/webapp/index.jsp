<%@ page import="rnp.DAO.UserDAODataSource"%>
<%@ page import="rnp.Bean.UserBean"%>
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
<html lang="en">
<head>
	<link rel="stylesheet" href="css/style.css">
	<script type="text/javascript"><%@include file="js/ProductFilter.js" %></script>
	<script type="text/javascript"><%@include file="js/slider.js" %></script>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Homepage</title>
</head>
<body>

	<div class="content">
		<h1>Home Page</h1>
		<% if (IS_CURRENT_USER_REGISTRED){ %>
			<p>Benvenuto nel sito di ReNewPhone <b><%=CURRENT_USER_BEAN.getName()%> <%=CURRENT_USER_BEAN.getSurname()%></b>, il sito di riferimento per la vendita di smartphone ricondizionati.</p>

		<%} else {%>
			<h3>Il sito di riferimento per la vendita di smartphone ricondizionati.</h3>
		<%} %>
		
		
		<% if (IS_CURRENT_USER_ADMIN){ %>
		<hr>
			<a href="admin.jsp">Vai alla pagina per admin</a><br>
		<hr>
		<%}%>
		
		<hr>
	</div>
	<div class="messaggio-var">News</div>
	
	
	
	
	
<div class="slider">
  <input type="button" value="<" onclick="prec()">  
  <div id="img_slider">
    <img src="resources/promo1.jpg" alt="promo1">
         <img src="resources/promo2.jpg" alt="promo2">
         <img src="resources/promo3.jpg" alt="promo3">
     
  </div>
  <input type="button" value=">" onclick="succ()"> 
</div>



	
	
	<div class="messaggio"> I nostri ricondizionati</div>
	
 <div class="brand-container">
 
 	<div class="brand">
      <img src="resources/apple.jpg" alt="iPhone" onclick="ProductRedirect()">
    </div>

  	<div class="brand">
   <img src="resources/samsung.jpg" alt="Samsung" onclick="ProductRedirect()">
    </div>
 
    <div class="brand">
   <img src="resources/pixel.jpg" alt="Pixel" onclick="ProductRedirect()">
    </div>

    <div class="brand">
   <img src="resources/xiaomi.jpg" alt="Xiaomi" onclick="ProductRedirect()">
    </div>
    
  </div>
  
  

  
<div class="messaggio"> F.A.Q.</div>

<div class="faq-container">
    
    <div class="faq-item">
      <h2 class="faq-question" onclick="toggleAnswer(this)">1. Cosa sono gli smartphone ricondizionati?</h2>
      <p class="faq-answer">
        Gli smartphone ricondizionati sono dispositivi usati che sono stati riparati, testati e riportati a condizioni di funzionamento come nuovi. Vengono sottoposti a un processo di ricondizionamento per garantire la loro qualità e prestazioni.
      </p>
    </div>
    <div class="faq-item">
      <h2 class="faq-question" onclick="toggleAnswer(this)">2. Quali sono le garanzie per gli smartphone ricondizionati?</h2>
      <p class="faq-answer">
        Offriamo una garanzia di 12 mesi per tutti gli smartphone ricondizionati acquistati dal nostro sito. La garanzia copre i difetti di fabbrica e i problemi di funzionamento, esclusi i danni causati dall'uso improprio o accidentale.
      </p>
    </div>
    <div class="faq-item">
      <h2 class="faq-question" onclick="toggleAnswer(this)">3. Come vengono selezionati gli smartphone ricondizionati?</h2>
      <p class="faq-answer">
        I nostri esperti selezionano attentamente gli smartphone usati da sottoporre al processo di ricondizionamento. Sono scelti in base alla loro qualità, alla presenza di danni estetici e al loro funzionamento generale. Solo i dispositivi che superano i nostri standard elevati vengono ricondizionati e messi in vendita.
      </p>
    </div>
    <div class="faq-item">
      <h2 class="faq-question" onclick="toggleAnswer(this)">4. Qual è la differenza tra un dispositivo ricondizionato e uno nuovo?</h2>
      <p class="faq-answer">
        La principale differenza tra un dispositivo ricondizionato e uno nuovo è il prezzo. Gli smartphone ricondizionati sono generalmente più economici rispetto ai nuovi. Tuttavia, i dispositivi ricondizionati sono stati accuratamente testati e riparati per garantire il loro corretto funzionamento, quindi offrono un'ottima opzione per chi desidera risparmiare denaro senza compromettere la qualità.
      </p>
    </div>
  </div>
  
<hr>
   <div class="rating">
     <h1>La tua opinione conta</h1> 
     <h3>invia il tuo feedback</h3>
    <input type="radio" name="rating" id="star5" value="5">
    <label for="star5"></label>
    <input type="radio" name="rating" id="star4" value="4">
    <label for="star4"></label>
    <input type="radio" name="rating" id="star3" value="3">
    <label for="star3"></label>
    <input type="radio" name="rating" id="star2" value="2">
    <label for="star2"></label>
    <input type="radio" name="rating" id="star1" value="1">
    <label for="star1"></label>
    <div id="Invio"> <input type="button" onclick="inviaValutazione()" value="Invia"></div>
  </div>
 
   
  



	<%@ include file="_footer.html" %>
	
	 <div class="popup-overlay">
    <div class="popup-content">
      <h2>Iscriviti alla nostra newsletter!</h2>
      <p>Ricevi le ultime novità direttamente nella tua casella di posta.</p>
      <form>
        <input type="email" placeholder="Inserisci il tuo indirizzo email" required>
      </form>
       <input type="button" value="Iscrivimi">
      <p class="close-popup">Chiudi</p>
    </div>
  </div>
</body>
</html>