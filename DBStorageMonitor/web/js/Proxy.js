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