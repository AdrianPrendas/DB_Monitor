var Controller = Controller || {}
var interval

$(function () {

    $("#btn-stop").prop("disabled", true)
    $("#btn-start").prop("disabled", false)

    $("#btn-start").on("click", function () {
        if ($("#warning").val() == "") {
            return
        }
        $("#btn-stop").prop("disabled", false);
        $("#time").prop("disabled", true);
        $("#warning").prop("disabled", true);

        countDown($("#time").val())
        consult()
    })

    $('#datetimepicker').datetimepicker({
        pickDate: false
    });

    $("#btn-stop").on("click", function () {
        clearInterval(interval)
        $("#btn-start").prop("disabled", false)
        $("#time").prop("disabled", false)
        $("#warning").prop("disabled", false)
    })

});

function consult() {
    Proxy.getBufferInfo(function (table) {
        var buffer = Storage.retrieve("buffer")
        if (buffer == null) {
            buffer = {}
            buffer['MAX'] = 0
            buffer['FREE'] = []
            buffer['TIME'] = []
            buffer['INTERVAL'] = $("#warning").val()
        }
       
        var d = new Date(Date.now())
        var h = d.getHours()
        var m = d.getMinutes()
        var s = d.getSeconds()
        
        buffer['FREE'].push(table[0][1])
        buffer['MAX'] = table[1][1]
        buffer['TIME'].push(h+":"+m+":"+s)

        Storage.store("buffer", buffer)
        Controller.createLinearChart(buffer)
    })

}


function countDown(timePeriod) {
    var N = 0;
    var parts = timePeriod.split(/:/);
    var timePeriodMillis = (parseInt(parts[0], 10) * 60 * 60 * 1000) +
            (parseInt(parts[1], 10) * 60 * 1000) +
            (parseInt(parts[2], 10) * 1000);


    var countDownDate = new Date().getTime() + timePeriodMillis

// Update the count down every 1 second
    interval = setInterval(function () {
        
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

        if (distance < 0) {
            document.getElementById("demo").innerHTML = "consult..."
            document.getElementById("N").innerHTML = N
            timePeriodMillis = (parseInt(parts[0], 10) * 60 * 60 * 1000) +
            (parseInt(parts[1], 10) * 60 * 1000) +
            (parseInt(parts[2], 10) * 1000);
            countDownDate = new Date().getTime() + timePeriodMillis
            consult()
            N++
        }

    }, 1000);
}
