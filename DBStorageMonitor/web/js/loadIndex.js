var Controller = Controller || {}


$(function () {
    //$("#hache1").html("jquery")

    $("#btn-connect").on("click", function () {
        
        Controller.createTable()
        Controller.createBarChart()
        Controller.createPieChart("A1")
        
    })

});


