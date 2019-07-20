
var userId;

function drawLastSevenDaysChart(){
    
    const resourceLink = `/chartsdata?user=${userId}&analysisType=lastSevenDays`;

    fetch(resourceLink, {
        method: 'post'
        })
        .then((response) => {
            console.log(response);
            return response.json();
        })
        .then((entries) => {
            var sevenDaysChartData = new google.visualization.DataTable();

            console.log(entries);
            
            //Columns for DataTable instance
            sevenDaysChartData.addColumn("string", "Date");
            sevenDaysChartData.addColumn("number", "Carbon Footprint");

            entries.forEach((entry) => {
                let entryRow = [entry.date, entry.footprint];
                sevenDaysChartData.addRow(entryRow);
            });

            let options = {
                "width" : 1200,
                "height": 800,
                curveType: 'function',
                legend: { position: 'bottom' }
            };
        
            let chart = new google.visualization.LineChart(document.getElementById("lastSevenDays"));
            chart.draw(sevenDaysChartData, options);
        });
}

function drawBreakdownChart() {
    
    const resourceLink = `/chartsdata?user=${userId}&analysisType=breakdown`;

    fetch(resourceLink, {
        method: 'post'
        })
        .then((response) => {
            console.log(response);
            return response.json();
        })
        .then((entries) => {
            var breakdownChartData = new google.visualization.DataTable();   
            
             // [ "Date", "breakfast", "lunch", "dinner", "snack"]
             // [ "July 20",    150,        75,       30,      89]
            breakdownChartData.addColumn("string", "Date");

            for(category in entries) {

                breakdownChartData.addColumn("string", category);

                for(let i = 0; i < entries[category].length; i++) {
                    let entry = entries[category][i];
                    let entryRow = [entry.date, category, entry.footprint];
                    console.log(entryRow);
                    breakdownChartData.addRow(entryRow);
                }
            }

            let options = {
                "width" : 1200,
                "height": 800,
                curveType: 'function',
                legend: { position: 'bottom' }
            };
        
            let chart = new google.visualization.AreaChart(document.getElementById("breakdown"));
            chart.draw(breakdownChartData, options);
        });
}


// Fetch data and populate the UI of the page.
function buildUI(id) {
    userId = id;
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawLastSevenDaysChart);
    google.charts.setOnLoadCallback(drawBreakdownChart);
}
