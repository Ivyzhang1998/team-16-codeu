<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% UserService userService = UserServiceFactory.getUserService(); %>


<div class="container">
  <nav>
      <header class="navbar">
  
            <section class="navbar-section">
              <a href="/" class="navbar-brand mr-2"><strong>Carbon Mob</strong></a>
              <a href="/aboutus" class="btn btn-link">About</a>
              <a href="/community" class="btn btn-link">Community</a>
              <a href="/feed" class="btn btn-link">Public Feed</a>
              <a href="/stats" class="btn btn-link">Stats</a>
              <a href="/charts" class="btn btn-link">Charts</a>
              <div class="dropdown">
                <a href="#" class="btn btn-link dropdown-toggle">Forms</a>
                <ul class="menu">
                  <li class="menu-item">
                    <a href="/eaten-food-form" class="btn btn-link">Meal Form</a>
                    <a href="/co2-food-form" class="btn btn-link">CO2 Data Form</a>
                  </li>
                </ul>
              </div>
            </section>
  
  
            <section class="navbar-section">
  
              <%
              boolean isUserLoggedIn = (boolean) userService.isUserLoggedIn();
              if (isUserLoggedIn) {
                String username = userService.getCurrentUser().getEmail();
              %>
              <a href="/user-page?user=<%= username %>" class="btn btn-link">Your Page</a>
              <a href="/logout" class="btn btn-link">Logout</a>
              <% } else {   %>
              <a href="/login" class="btn btn-link">Login</a>
              <% } %>
              
            </section>
      </header>
  </nav>
</div>
