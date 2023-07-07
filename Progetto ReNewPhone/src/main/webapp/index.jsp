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
<html>
<head>
	<link rel="stylesheet" href="css/style.css">
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
		
		
	</div>
	<div class="messaggio-var">News</div>
	
	
	
	
	
<div class="slider">
  <input type="button" value="<" onclick="prec()">  
  <div id="img_slider">
  
    <img src="https://i.postimg.cc/kMhJKgr6/immagine2.jpg">
    <img src="https://i.postimg.cc/WbF2ncj9/immagine3.jpg">
    <img src="https://i.postimg.cc/15h9JYts/immagine4.jpg">
    <img src="https://i.postimg.cc/rmD8YBxF/immagine5.jpg">
    <img src="resources/xiaomi.jpg">
  </div>
  <input type="button" value=">" onclick="succ()"> 
</div>


	
	
	<div class="messaggio"> I nostri ricondizionati</div>
	
 <div class="brand-container">
  <a href="products">  <div class="brand">
      <img src="resources/apple.jpg" alt="iPhone">
    </div>
    </a>
     <a href="products">  <div class="brand">
   <img src="resources/samsung.jpg" alt="Samsung">
    </div>
    </a>
     <a href="products">  <div class="brand">
      <img src="resources/ipad_logo.jpg" alt="iPad">
    </div>
    </a>
     <a href="products">  <div class="brand">
   <img src="resources/pixel.jpg" alt="Pixel">
    </div>
    </a>
       <a href="products">  <div class="brand">
   <img src="resources/xiaomi.jpg" alt="Xiaomi">
    </div>
    </a>
  </div>
  
<div class="messaggio"> F.A.Q.</div>

  
  
	<%@ include file="_footer.html" %>
</body>
</html>