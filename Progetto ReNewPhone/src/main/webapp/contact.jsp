<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/content.css">
  <title>Contacts</title>
</head>
<body>
  <%@ include file="_header.html" %>

  <div class="content">
    <h1>Contact</h1>
    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Inventore harum unde voluptas laudantium. Sapiente magni neque perspiciatis, vero, cumque recusandae in quis officia labore voluptate error ullam autem obcaecati excepturi?</p>
    <form action="contact.php" method="post">
      <input type="text" name="name" placeholder="Your name">
      <input type="email" name="email" placeholder="Your email">
      <textarea name="message" placeholder="Your message"></textarea>
      <input type="submit" value="Send">
    </form>
  </div>

  <%@ include file="_footer.html" %>
</body>
</html>
