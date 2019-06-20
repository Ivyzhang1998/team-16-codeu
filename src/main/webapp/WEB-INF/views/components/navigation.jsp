<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% UserService userService = UserServiceFactory.getUserService(); %>


<nav>
    <ul id="navigation">
      <li><a href="/">Home</a></li>
      <li><a href="/aboutus">About Our Team</a></li>
      <li><a href="/feed">Public Feed</a></li>
      <li><a href="/stats">Stats</a></li>
      <li><a href="/charts">Charts</a></li>
      <li><a href="/map">Map</a></li>

      <%
      boolean isUserLoggedIn = (boolean) userService.isUserLoggedIn();
      if (isUserLoggedIn) {
        String username = userService.getCurrentUser().getEmail();
      %>
      <a href="/user-page?user=<%= username %>">Your Page</a>
      <a href="/logout">Logout</a>
      <% } else {   %>
      <a href="/login">Login</a>
      <% } %>
    </ul>
</nav>
