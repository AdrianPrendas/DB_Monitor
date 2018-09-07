Proxy.getTablespaces = function (callback) {
    $.ajax({
        url: "/DBMonitor/DBAService",
        type: "POST",
        dataType: "json",
        data: {
            action: "getTablespaces"
        }
    }).done(function (res) {
        res.sort(function(a,b){
            return a[0] > b[0]
        })
        var WARNING = 80
        res = res.map(function(a){
            return [a[0], a[1], parseInt(a[2]), parseInt(a[3]),WARNING, a[4], a[5], a[6], a[7] ]
        })
        callback(res)
    }).fail(function (err) {
        alert(err)
    })
}

Proxy.getBufferInfo = function (callback) {
    $.ajax({
        url: "/DBMonitor/DBAService",
        type: "POST",
        dataType: "json",
        data: {
            action: "getBufferInfo"
        }
    }).done(function (res) {
        callback(res)
    }).fail(function (err) {
        alert(err)
    })
}