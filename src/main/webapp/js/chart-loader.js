
//Loads and initializes the Google Charts API, then calls drawChart()
function initGoogleCharts() {
    google.charts.load('current', {packages: ['corechart']});
    google.charts.setOnLoadCallback(drawChart);
}

//Sets up chart data in a DataTable, draws a chart on the page
function drawChart(){
    fetch("/chartsdata")
        .then((response) => {
            return response.json();
        })
        .then((bookJson) => {
            let bookData = new google.visualization.DataTable();
            
            //Columns for DataTable instance
            bookData.addColumn("string", "Book Title");
            bookData.addColumn("number", "Rating");

            bookJson.forEach((book) => {
                let bookRow = [book.title, book.rating];
                bookData.addRow(bookRow);
            });

            let options = {
                "width" : 800,
                "height": 600
            };
        
            let chart = new google.visualization.BarChart(document.getElementById("chart"));
            chart.draw(bookData, options);
        });
}

// Fetch data and populate the UI of the page.
function buildUI() {
    initGoogleCharts();
}

