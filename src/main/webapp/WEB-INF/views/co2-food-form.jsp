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
</head>
<body>
<p>Log what you ate and then click submit!</p>

<form method="POST" enctype="multipart/form-data" action="<%= uploadUrl %>">
    <input name="form-id" id="form-id" type="hidden" value="co2">
    <label for="foodName">Food name:</label>
    <input name="foodName" id="foodName" required>
    <br/><br/>
    <label for="co2">CO2/year:</label>
    <input name="co2" id="co2" type="number" required>
    <br/><br/>
    <button>Submit</button>
</form>
</body>
</html>
