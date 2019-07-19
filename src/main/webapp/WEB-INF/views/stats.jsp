<% int messageCount = (int) request.getAttribute("messageCount"); %>
<% int userCount = (int) request.getAttribute("userCount"); %>

<!DOCTYPE html>
<html>
  <head>
    <title>Stats</title>
    <link rel="stylesheet" href="/css/spectre.min.css"/>
    <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
    <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/stats-page-loader.js"></script>
  </head>
  <body>
    <%@ include file="./components/navigation.jsp" %>
    
    <div id="content">
      
        <div class="container">
            <h1>Site Statistics</h1>
            <hr/>
            <div id="stats-container">
                <p>Message count: <%= messageCount %></p>
                <p>User count: <%= userCount %></p>
            </div>
        </div>

    </div>
  </body>
</html>
