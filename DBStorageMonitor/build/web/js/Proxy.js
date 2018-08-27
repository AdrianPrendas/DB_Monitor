var Proxy = Proxy || {};

Proxy.f = function (callback) {
    $.ajax({
        url: "/DBStorageMonitor/DBAService",
        type: "POST",
        dataType: "json",
        data: {
            action: "connect"
        }
    }).done(function (res) {
        callback(res)
    }).fail(function (err) {
        callback(err)
    })
}

Proxy.getTablespaces = function (callback) {
    $.ajax({
        url: "/DBStorageMonitor/DBAService",
        type: "POST",
        dataType: "json",
        data: {
            action: "getTablespaces"
        }
    }).done(function (res) {
        callback(res)
    }).fail(function (err) {
        callback(err)
    })
}