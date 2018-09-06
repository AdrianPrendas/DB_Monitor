google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {

    var data = google.visualization.arrayToDataTable([
        ['Storage', 'size'],
        ['Free MB', 250],
        ['Used MB', 50]
    ]);

    var options = {
        title: 'TableSpace storage information A1'
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
}