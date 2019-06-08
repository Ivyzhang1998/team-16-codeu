
//Loads and initializes the Google Charts API, then calls drawChart()
function initGoogleCharts() {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

//Sets up chart data in a DataTable, draws a chart on the page
function drawChart(){
    var foodData = new google.visualization.DataTable();

    foodData.addColumn("string", "Food Title");
    foodData.addColumn("number", "Carbon Footprint (kg)");

    foodData.addRows([
        ["Lamb", 39.2],
        ["Beef", 27.0],
        ["Cheese", 13.5],
        ["Pork", 12.1],
        ["Chicken", 6.9],
        ["Tuna", 6.1],
        ["Eggs", 4.8],
        ["Potatoes", 2.9],
        ["Rice", 2.7],
        ["Nuts", 2.3],
        ["Beans/Tofu", 2.0],
        ["Milk", 1.9],
        ["Fruit", 1.1],
        ["Lentils", 0.9]
    ]);

    var options = {
        "title" : "Greenhouse Gas Emissions Per Kilo of Food",
        "width" : 800,
        "height": 600
    };

    var chart = new google.visualization.BarChart(document.getElementById("chart"));
    chart.draw(foodData, options);
}

// Fetch data and populate the UI of the page.
function buildUI() {
    initGoogleCharts();
}

