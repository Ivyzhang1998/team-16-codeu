<%-- The Java code in this JSP file runs on the server when the user navigates
     to the homepage. This allows us to insert the Blobstore upload URL into the
     form without building the HTML using print statements in a servlet. --%>
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
            <p>Upload an image:</p>
            <input type="file" name="image">
            <br/><br/>
            <p>Food name:</p>
            <textarea name="foodName" required></textarea>
            <br/><br/>
            <p>Amount eaten:</p>
            <textarea name="amount" required></textarea>
            <br/>
            <button>Submit</button>
        </form>
    </body>
</html>
