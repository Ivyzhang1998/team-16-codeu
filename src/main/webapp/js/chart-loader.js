// Fetch data and populate the UI of the page.
function buildUI(userId) {
    initGoogleCharts(userId);
}

//Loads and initializes the Google Charts API, then calls drawChart()
function initGoogleCharts(userId) {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawLastSevenDaysChart(userId));
    google.charts.setOnLoadCallback(drawBreakdownChart(userId));
}

function drawLastSevenDaysChart(userId){
    const data = {
        user: userId,
        analysisType: "lastSevenDays"
    };

    fetch("/chartsdata", {
        method: 'post',
        body: JSON.stringify(data);
        })
        .then((response) => {
            return response.json();
        })
        .then((entries) => {
            let chartData = new google.visualization.DataTable();
            
            //Columns for DataTable instance
            chartData.addColumn("string", "Date");
            chartData.addColumn("number", "Carbon Footprint");

            for(date in entries) {
                let entryRow = [date, entries[date]];
                chartData.addRow(entryRow);
            }

            let options = {
                "width" : 1200,
                "height": 800,
                curveType: 'function',
                legend: { position: 'bottom' }
            };
        
            let chart = new google.visualization.LineChart(document.getElementById("lastSevenDays"));
            chart.draw(chartData, options);
        });
}

function drawBreakdownChart(userId) {
    const data = {
        user: userId,
        analysisType: "breakdown"
    };

    fetch("/chartsdata", {
        method: 'post',
        body: JSON.stringify(data);
        })
        .then((response) => {
            return response.json();
        })
        .then((entries) => {
            let chartData = new google.visualization.DataTable();
            
            //Columns for DataTable instance
            chartData.addColumn("string", "Category");
            chartData.addColumn("number", "Carbon Footprint");

            for(category in entries) {
                for(let i = 0; i < entries[category].length; i++) {
                    let entryRow = [category, entries[category][i]];
                    chartData.addRow(entryRow);
                }
            }

            let options = {
                "width" : 1200,
                "height": 800,
                curveType: 'function',
                legend: { position: 'bottom' }
            };
        
            let chart = new google.visualization.AreaChart(document.getElementById("breakdown"));
            chart.draw(chartData, options);
        });
}
