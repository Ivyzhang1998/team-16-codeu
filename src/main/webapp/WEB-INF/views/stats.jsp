<% int messageCount = (int) request.getAttribute("messageCount"); %>
<% int userCount = (int) request.getAttribute("userCount"); %>

<!DOCTYPE html>
<html>
  <head>
    <title>Stats</title>
    <link rel="stylesheet" href="/css/main.css">
    <script src="/js/stats-page-loader.js"></script>
  </head>
  <body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/aboutus.html">About Our Team</a></li>
        <li><a href="/stats.html">Stats</a></li>
      </ul>
    </nav>
    <div id="content">
      <h1>Site Statistics</h1>
      <hr/>
      <div id="stats-container">
          <p>Message count: <%= messageCount %></p>
          <p>User count: <%= userCount %></p>
      </div>
    </div>
  </body>
</html>
