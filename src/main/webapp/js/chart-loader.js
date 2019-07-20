
function drawLastSevenDaysChart(userId){
    
    const resourceLink = `/chartsdata?user=${userId}&analysisType=lastSevenDays`;

    fetch(resourceLink, {
        method: 'post'
        })
        .then((response) => {
            console.log(response);
            return response.json();
        })
        .then((entries) => {
            let chartData = new google.visualization.DataTable();

            console.log(entries);
            
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
    
    const resourceLink = `/chartsdata?user=${userId}&analysisType=breakdown`;

    fetch(resourceLink, {
        method: 'post'
        })
        .then((response) => {
            console.log(response);
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

//Loads and initializes the Google Charts API, then calls drawChart()
function initGoogleCharts(userId) {
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawLastSevenDaysChart(userId));
    //google.charts.setOnLoadCallback(drawBreakdownChart(userId));
}

// Fetch data and populate the UI of the page.
function buildUI(userId) {
    console.log(userId);
    initGoogleCharts(userId);
}
