var Controller = Controller || {}
var avg = 0

$(function () {
    

    $("#btn-connect").on("click", function () {
        var intervalo = $("#time").val()
        var muestras = $("#samples").val()
        if(isNaN(parseInt(muestras))){
            return 
        }
        $("#btn-connect").prop("disabled",true);
        $("#time").prop("disabled",true);
        $("#samples").prop("disabled",true);
        
        countDown(intervalo, muestras)
        consult()
    })
    $('#datetimepicker').datetimepicker({
        pickDate: false
    });
 
});

function consult(){
    Proxy.getTablespaces(function (res) {
        
        //*** local - sotrage ***
        var storage = {tuples:[],increases:[],table:[]}
        var table = []
        storage = Storage.retrieve("binnacle");
        if (storage == null) {
            var storage = {tuples:[],increases:[],table:[]}
        }
        
        if(storage.tuples.length!=0){
            var oldtuple = storage.tuples.pop()
            var newtuple = res
            
            for(var i=0;i<newtuple.length;i++){
                storage.increases.push({
                    tablespace:newtuple[i][0],
                    increase:newtuple[i][3]-oldtuple[i][3]
                })
            }
            
            
            
            
            //res.puhs(85)//humbral
            
            Controller.createTable(res)
            Controller.createBarChart(res)
            Controller.createPieChart(res,0)   
        }
        
        storage.tuples.push(res)
        Storage.store("binnacle", storage);
        console.log("new record in local storage");
        console.log(storage)
        //*** local - sotrage ***               
    })
    
}

function countDown(timePeriod, samples) {
    
    if(samples==0){
        $("#btn-connect").prop("disabled",false);
        $("#time").prop("disabled",false);
        $("#samples").prop("disabled",false);
        document.getElementById("demo").innerHTML = "Finish";
        return
    }
        
      
      var parts = timePeriod.split(/:/);
var timePeriodMillis = (parseInt(parts[0], 10) * 60 * 60 * 1000) +
                       (parseInt(parts[1], 10) * 60 * 1000) + 
                       (parseInt(parts[2], 10) * 1000);
        
        
        var countDownDate = new Date().getTime()+timePeriodMillis

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
                $("#samples").val(samples-1)
                countDown(timePeriod, samples-1)
            }
        }, 1000);
    }
