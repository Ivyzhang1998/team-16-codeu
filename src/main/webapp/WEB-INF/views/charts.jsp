<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% String userId = UserServiceFactory.getUserService().getCurrentUser().getUserId();%>

<!DOCTYPE html>
<html>

    <head>
        <title>Charts</title>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="/css/user-page.css">
        <link rel="stylesheet" href="/css/spectre.min.css"/>
        <link rel="stylesheet" href="/css/spectre-icons.min.css"/>
        <link rel="stylesheet" href="/css/spectre-exp.min.css"/>
        <link rel="stylesheet" href="/css/main.css">
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript" src="/js/chart-loader.js"></script>
    </head>

    <body onload="buildUI('<%= userId %>');">
        <%@ include file="./components/navigation.jsp" %>

        <div class="container">

            <h1 id="page-title" class="text-center">Review</h1>

            <div class="columns col-gapless">

                <div class="column col-11 col-mx-auto">
                    <h2 class="text-center"> CO2 Footprint The Last Seven Days </h2>
                    <div id="lastSevenDays"></div>
                </div>

                <div class="column col-11 col-mx-auto">
                    <h2 class="text-center"> CO2 Breakdown by Meal Type </h2>
                    <div id="breakdown"></div>
                </div> 

            </div>
        </div>

    </body>

</html>
