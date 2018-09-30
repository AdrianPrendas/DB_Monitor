var JsonUtils = JsonUtils || {};

JsonUtils.revive = function (k, v) {
    if(v instanceof Object && v._class == 'Avion')
        return new User(v.username, v.password, v.access_level)
    return v;
};

JsonUtils.repalcer = function (k, v) {
    if (v instanceof User)
        v._class = "User";
    return v;
};