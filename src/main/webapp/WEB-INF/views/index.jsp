<% boolean isUserLoggedIn = (boolean) request.getAttribute("isUserLoggedIn"); %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link rel="stylesheet" href="/css/main.css">
  </head>
  <body>
    <nav>
      <ul id="navigation">
        <li><a href="/">Home</a></li>
        <li><a href="/aboutus.html">About Our Team</a></li>
        <li><a href="/feed.html">Public Feed</a></li>
        <li><a href="/stats.html">Stats</a></li>
        <li><a href="/charts.html">Charts</a></li>
        <li><a href="/map.html">Map</a></li>

        <%
        if (isUserLoggedIn) {
        String username = (String) request.getAttribute("username");
        %>
        <a href="/user-page.html?user=<%= username %>">Your Page</a>
        <a href="/logout">Logout</a>
        <% } else {   %>
        <a href="/login">Login</a>
        <% } %>
      </ul>
    </nav>
    <h1>Team 16 Project</h1>
    <p>This is the homepage.</p>
  </body>
</html>
