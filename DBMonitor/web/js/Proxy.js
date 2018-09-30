var Proxy = Proxy || {}

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

//metodos de usuario
Proxy.register = function (user, callback) {
    $.ajax({
        url: "/DBMonitor/UserService",
        type: "POST",
        dataType: "json",
        data: {
            action: "register",
            user:JSON.stringify(JsonUtils.repalcer(0,user))
        }
    }).done(function (res) {
        if(res.status == "ER"){
            alert(res.response)
        }else if(res.status =="OK")
            res.user = JsonUtils.revive(0,JSON.parse(res.user))
            alert(res.response)
            callback(res)
    }).fail(function (err) {
        console.log(err)
    })
}

Proxy.login = function (user, callback) {
    $.ajax({
        url: "/DBMonitor/UserService",
        type: "POST",
        dataType: "json",
        data: {
            action: "login",
            user:JSON.stringify(JsonUtils.repalcer(0,user))
        }
    }).done(function (res) {
        if(res.status == "ER"){
            alert(res.response)
        }else if(res.status =="OK")
            res.user = JsonUtils.revive(0,JSON.parse(res.user))
            alert(res.response)
            callback(res)
    }).fail(function (err) {
        console.log(err)
    })
}
//fin de metodos del usuario