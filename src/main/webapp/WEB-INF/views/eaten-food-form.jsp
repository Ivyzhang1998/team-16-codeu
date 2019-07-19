<%-- The Java code in this JSP file runs on the server when the user navigates
     to the homepage. The user will be able to upload an image of the food that
     they ate, and the accompanying info about the food.--%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    String uploadUrl = blobstoreService.createUploadUrl("/my-form-handler"); %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Log a food</title>
        <link rel="stylesheet" href="/css/main.css">
        <link rel="stylesheet" href="/css/spectre.min.css"/>
        <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
        <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
    </head>
    <body>
     <%@ include file="./components/navigation.jsp" %>
        <p>Log what you ate and then click submit!</p>

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
            <label for="foodName">Food name:</label>
            <input name="foodName" id="foodName" required>
            <br/><br/>
            <label for="amount">Amount eaten:</label>
            <input type="number" name="amount" id="amount" required>
            <br/>
            <button>Submit</button>
        </form>
    </body>
</html>
