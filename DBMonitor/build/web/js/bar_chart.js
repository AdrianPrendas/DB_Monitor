google.charts.load('current', {packages: ['corechart', 'bar']});
google.charts.setOnLoadCallback(drawStacked);
function drawStacked() {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Tablespace N');
    data.addColumn('number', 'Used');
    data.addColumn('number', 'Free');

    data.addRows([
        ["A1", 50, 250],
        ["A2", 150, 250],
        ["A3", 250, 250],
        ["A4", 550, 50]
    ]);

    var options = {
        title: 'Space level of tablespaces',
        isStacked: true,
        colors:["red","blue"],
        hAxis: {
            title: 'Tablespaces',
            format: 'tableSpace',
            viewWindow: {
                min: [7, 30, 0],
                max: [17, 30, 0]
            }
        },
        vAxis: {
            title: 'Size MB'
        }
    };
    
    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
    chart.draw(data, options);
    
}