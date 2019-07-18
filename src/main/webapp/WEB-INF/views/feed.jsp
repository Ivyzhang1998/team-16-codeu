<!DOCTYPE html>
<html>

<head>
    <title>Message Feed</title>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/user-page.css">
    <link rel="stylesheet" href="/css/spectre.min.css"/>
    <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
    <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
    <script src="/js/feed-loader.js"></script>
</head>

<body onload="buildUI();">
    <div id="content">
        <%@ include file="./components/navigation.jsp" %>
         
        <h1>Message Feed</h1>
        <hr />
        <div id="message-container">Loading...</div>
    </div>
</body>

</html>
