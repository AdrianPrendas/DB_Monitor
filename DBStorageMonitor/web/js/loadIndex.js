var Controller = Controller || {}


$(function () {


    $("#btn-connect").on("click", function () {
        var intervalo = $("#time").val()
        var muestras = $("#samples").val()
        if (isNaN(parseInt(muestras))||$("#warning").val()=="") {
            return
        }
        $("#btn-connect").prop("disabled", true);
        $("#time").prop("disabled", true);
        $("#samples").prop("disabled", true);
        $("#warning").prop("disabled", true);

        countDown(intervalo, muestras)
        consult()
    })
    $('#datetimepicker').datetimepicker({
        pickDate: false
    });

});

function consult() {
    Proxy.getTablespaces(function (res) {

        //*** local - sotrage ***
        var storage = {} // {a1:{size:0,free:[],used:[]}}
        storage = Storage.retrieve("binnacle");
        if (storage == null) {
            storage = {}
        }
        console.log(storage)

        for (var i = 0; i < res.length; i++) {
            if (storage[res[i][0]] == null) {
                storage[res[i][0]] = {size: res[i][1], free: [res[i][2]], used: [res[i][3]], increase:[], avg:0, interval:[]}
            } else {
                storage[res[i][0]].size = res[i][1]
                storage[res[i][0]].free.push(res[i][2])
                storage[res[i][0]].used.push(res[i][3])
                storage[res[i][0]].interval.push($("#demo").html())
                if(storage[res[i][0]].used.length>1){
                    var arr = storage[res[i][0]].used
                    var incremento = arr[arr.length-1]-arr[arr.length-2]
                    storage[res[i][0]].increase.push(incremento)
                    var sum = storage[res[i][0]].increase.reduce(function(a,b){return a+b})
                    var avg = sum/storage[res[i][0]].increase.length
                    storage[res[i][0]].avg = avg
                }
            }
        }
        
        res = res.map(function(tuple){
            var total = tuple[1]
            var free = tuple[2]
            var used = tuple[3]
            var warning = tuple[4]
            var avg = storage[tuple[0]].avg
            
            tuple.push($("#warning").val())
            tuple.push( (free-(total*(100-warning)/100))/avg )
            tuple.push(free * avg)
            return tuple
        })
        
        Controller.createTable(res)
        Controller.createBarChart(res)
        Controller.createPieChart(res, 0)

        Storage.store("binnacle", storage);
        console.log("new record in local storage");
        console.log(storage)
        //*** local - sotrage ***               
    })

}

function countDown(timePeriod, samples) {

    if (samples == 0) {
        $("#btn-connect").prop("disabled", false);
        $("#time").prop("disabled", false);
        $("#samples").prop("disabled", false);
        $("#warning").prop("disabled", false);
        document.getElementById("demo").innerHTML = "Finish";
        return
    }


    var parts = timePeriod.split(/:/);
    var timePeriodMillis = (parseInt(parts[0], 10) * 60 * 60 * 1000) +
            (parseInt(parts[1], 10) * 60 * 1000) +
            (parseInt(parts[2], 10) * 1000);


    var countDownDate = new Date().getTime() + timePeriodMillis

// Update the count down every 1 second
    var x = setInterval(function () {

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
            document.getElementById("demo").innerHTML = "Consult..."
            consult()
            $("#samples").val(samples - 1)
            countDown(timePeriod, samples - 1)
        }
    }, 1000);
}
