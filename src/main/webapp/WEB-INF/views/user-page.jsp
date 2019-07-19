
<!DOCTYPE html>
<html>
  <head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/user-page.css">
    <link rel="stylesheet" href="/css/spectre.min.css"/>
    <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
    <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/user-page-loader.js"></script>
  </head>
  <body onload="buildUI();">
    <%@ include file="./components/navigation.jsp" %>
    
    <div class="container">
      <h1 id="page-title">User Page</h1>

      <form id="message-form" action="/messages" method="POST" class="hidden">
        Enter a new message:
        <br/>
        <textarea name="text" id="message-input"></textarea>
        <br/>
        <input type="submit" value="Submit">
      </form>
      <hr/>
  
      <div id="message-container">Loading...</div>
    </div>

  </body>
</html>
