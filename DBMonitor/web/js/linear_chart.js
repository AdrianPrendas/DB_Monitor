google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Year', 'used', 'warning'],
        ['2013', 400, 900],
        ['2014', 460, 900],
        ['2015', 1120, 900],
        ['2016',540, 900]
    ]);

    var options = {
        title: 'Buffer usage',
        hAxis: {title: 'Time', titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0},
        colors:['blue','yellow'],
        series:{1:{type:'line'}}
    };

    var chart = new google.visualization.AreaChart(document.getElementById('linearchart'));
    chart.draw(data, options);
}