google.charts.load('current', {'packages': ['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var data = google.visualization.arrayToDataTable([
        ['Year', 'used'],
        ['2013', 400],
        ['2014', 460],
        ['2015', 1120],
        ['2016',540]
    ]);

    var options = {
        title: 'Buffer usage',
        hAxis: {title: 'Time', titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0},
        colors:['blue']
    };

    var chart = new google.visualization.AreaChart(document.getElementById('linearchart'));
    chart.draw(data, options);
}