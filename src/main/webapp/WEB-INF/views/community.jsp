<!DOCTYPE html>
<html>
  <head>
    <title>Community Page</title>
    <link rel="stylesheet" href="/css/main.css">

    <script>

      /** Fetches users and adds them to the page. */
      function fetchUserList(){
        const url = '/user-list';
        fetch(url).then((response) => {
          return response.json();
        }).then((users) => {
          const userlist = document.getElementById('userlist');
          userlist.innerHTML = '';
          users.forEach((user) => {
           const userListItem = buildUserListItem(user);
           userlist.appendChild(userListItem);
          });
        });
      }

      /**
       * Builds a list element that contains a link to a user page, e.g.
       * <li><a href="/user-page.html?user=test@example.com">test@example.com</a></li>
       */
      function buildUserListItem(user){
        const userLink = document.createElement('a');
        userLink.setAttribute('href', '/user-page.html?user=' + user);
        userLink.appendChild(document.createTextNode(user));
        const userListItem = document.createElement('li');
        userListItem.appendChild(userLink);
        return userListItem;
      }

      /** Fetches data and populates the UI of the page. */
      function buildUI(){
       fetchUserList();
      }
    </script>
  </head>
  <body onload="buildUI()">
    <div id="content">
      <%@ include file="./components/navigation.jsp" %>
      
      <h1>Community Page</h1>
      <p>Here is a list of every user who has posted a message:</p>
      <hr/>
      <ul id="userlist">Loading...</ul>
    </div>
  </body>
</html>
