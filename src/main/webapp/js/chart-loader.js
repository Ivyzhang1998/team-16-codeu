
var userId;

function drawLastSevenDaysChart(){
    
    const resourceLink = `/chartsdata?user=${userId}&analysisType=lastSevenDays`;

    fetch(resourceLink, {
        method: 'post'
        })
        .then((response) => {
            return response.json();
        })
        .then((entries) => {
            var sevenDaysChartData = new google.visualization.DataTable();

            //Columns for DataTable instance
            sevenDaysChartData.addColumn("string", "Date");
            sevenDaysChartData.addColumn("number", "Carbon Footprint");

            entries.forEach((entry) => {
                let entryRow = [entry.date.slice(0, -18), entry.footprint];
                sevenDaysChartData.addRow(entryRow);
            });

            let options = {
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
            return response.json();
        })
        .then((entries) => {
            var breakdownChartData = new google.visualization.DataTable();   
            
             // [ "Meal Type", "Total Footprint"]
             // [ "Breakfast",  "2006607"]
             // [ "Lunch",  "46576756567"]
            breakdownChartData.addColumn("string", "Meal Type");
            breakdownChartData.addColumn("number", "Total Carbon Footprint");

            for(category in entries) {
                let entryRow = [category, entries[category]];
                breakdownChartData.addRow(entryRow);
            }

            let options = {
                pieHole: 0.4,
                legend: {
                    alignment: 'center',
                    position: 'top'
                }
            };
        
            let chart = new google.visualization.PieChart(document.getElementById("breakdown"));
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
