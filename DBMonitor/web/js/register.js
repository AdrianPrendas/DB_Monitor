var Proxy = Proxy || {}

$(function () {
    $("#registerForm").on("submit", function (event) {
        event.preventDefault()
        
        var shaObj = new jsSHA("SHA-512", "TEXT");
        shaObj.update($("#password").val());
        //shaObj.update("test");
        var hash = shaObj.getHash("HEX");
        hash = hash.toUpperCase()
        alert("password (SHA-512, HEX):\n\n" + hash + "\n\n str length:" + hash.length)
        var username = $("#user-name").val()
        var user = {username:username, password: hash}
        Proxy.register(user,function(res){
            console.log(res)
        })
    })
    

});
