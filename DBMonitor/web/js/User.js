function User(username, password, access_level){
    this.User(username, password, access_level)
}

User.prototype = {
    User: function (username, password, access_level){
        this.username = username
        this.password = password
        this.access_level = ( !access_level ? 2 : access_level )
       
    },
    toString: function () {
        return "User={"
                + "username: " + username
                + "password: " + password
                + "access_level: " + access_level
                + "\n}";
    }
};
