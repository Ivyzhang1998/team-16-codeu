
//Loads and initializes the Google Charts API, then calls drawChart()
function initGoogleCharts() {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

//Sets up chart data in a DataTable, draws a chart on the page
function drawChart(){
    fetch("/chartsdata/week")
        .then((response) => {
            return response.json();
        })
        .then((entries) => {
            let chartData = new google.visualization.DataTable();
            
            //Columns for DataTable instance
            chartData.addColumn("string", "Date");
            chartData.addColumn("number", "Carbon Footprint");

            let dates = Object.keys(entries);
            for(let i = 0; i < dates.length; i++) {
                let entryRow = [dates[i], entries[dates[i]]];
                chartData.addRow(entryRow);
            }

            let options = {
                "width" : 1200,
                "height": 800
            };
        
            let chart = new google.visualization.LineChart(document.getElementById("chart"));
            chart.draw(chartData, options);
        });
}

// Fetch data and populate the UI of the page.
function buildUI(userId) {
    initGoogleCharts();
}

