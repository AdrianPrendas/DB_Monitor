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
        res = res.map(function(a){
            return [a[0], a[1], parseInt(a[2]), parseInt(a[3])]
        })
        callback(res)
    }).fail(function (err) {
        callback(err)
    })
}


