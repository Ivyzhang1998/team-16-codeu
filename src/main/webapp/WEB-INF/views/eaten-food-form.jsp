<%-- The Java code in this JSP file runs on the server when the user navigates
     to the homepage. The user will be able to upload an image of the food that
     they ate, and the accompanying info about the food.--%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler"); %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Log a food</title>
        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/spectre.min.css"/>
        <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
        <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.css" />
        <script
                src="https://code.jquery.com/jquery-3.4.1.min.js"
                integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                crossorigin="anonymous">
        </script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.js"></script>
    </head>
    <body>
     <%@ include file="./components/navigation.jsp" %>
     <p>Log what you ate and then click submit!</p>
     <p>Make sure that you <b>log in</b> before submitting this form!</p>
     <p>
         Click <a href="http://su19-codeu-16-9291.appspot.com/food-list">here</a> to view the entire list of food in our database!
     </p>
     <p>Log what you ate and then click submit!</p>
     <div class="control-group">
         <label for="select">Food</label>
         <select id="select" name="foodName" form="eatenFood">
             <c:forEach items="${foodList}" var="food">
                 <option value="<c:out value="${food}"/>"><c:out value="${food}"/></option>
             </c:forEach>
         </select>
     </div>
     <form id="eatenFood" method="POST" enctype="multipart/form-data" action="<%= uploadUrl %>">
         <input name="form-id" id="form-id" type="hidden" value="eaten">
         <label for="image">Upload an image:</label>
         <input type="file" name="image" id="image">
         <br/><br/>
         <label for="date">Date eaten:</label>
         <input type="date" name="date" id="date" required>
         <br/><br/>
         <label for="mealType">Meal:</label><br>
         <input type="radio" name="mealType" id="mealType" value="breakfast">breakfast<br>
         <input type="radio" name="mealType" value="lunch">lunch<br>
         <input type="radio" name="mealType" value="dinner">dinner<br>
         <input type="radio" name="mealType" value="snack">snack<br><br/>
         <br/><br/>
         <label for="amount">Amount eaten:</label>
         <input type="number" name="amount" id="amount" required>
         <br/>
         <button>Submit</button>
     </form>
     <script>
         $('select').selectize({
             placeholder:'choose a food',
             create:false,
         });
     </script>
    </body>
</html>
