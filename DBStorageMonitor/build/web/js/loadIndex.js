var Controller = Controller || {}


$(function () {
    //$("#hache1").html("jquery")

    $("#btn-connect").on("click", function () {

        Controller.createTable()
        Controller.createBarChart()
        Controller.createPieChart("A1")
        conuntDown()

    })
    $('#datetimepicker').datetimepicker({
        pickDate: false
    });
    
    
   

});


function countDown(){
    // Set the date we're counting down to
var countDownDate = new Date(2018, 07, 28, 3, 50, 0, 0).getTime();

// Update the count down every 1 second
var x = setInterval(function() {

    // Get todays date and time
    var now = new Date().getTime();
    
    // Find the distance between now and the count down date
    var distance = countDownDate - now;
    
    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);
    
    // Output the result in an element with id="demo"
    document.getElementById("demo").innerHTML = days + "d " + hours + "h "
    + minutes + "m " + seconds + "s ";
    
    // If the count down is over, write some text 
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("demo").innerHTML = "EXPIRED";
    }
}, 1000);
}

