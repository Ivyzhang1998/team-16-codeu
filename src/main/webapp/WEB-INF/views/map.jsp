<!DOCTYPE html>
<html>
  <head>
    <title>UFO Sightings</title>
    <link rel="stylesheet" href="/css/main.css">
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAIJxmuluUxbW2r2rZgOebw7B0U1rRtrvA"></script>
    <script src="/js/map-loader.js"></script>
  </head>
  <body onload="createUfoSightingsMap();">
    <%@ include file="./components/navigation.jsp" %>
    
    <h1>UFO Sightings</h1>
    <div class="map"></div>
  </body>
</html>
