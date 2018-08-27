Storage = {
	store : function (id, object){
		return localStorage.setItem(id, JSON.stringify(object,this.replacer));
	},

	retrieve: function (id){
		var jsonObject = localStorage.getItem(id);
		if(jsonObject === null)
			return null;
			
        	return JSON.parse(jsonObject,this.revive);
	},

	revive: function (k,v) {
		return JsonUtils.revive(k,v);
	},

	replacer: function (k,v) {
		return JsonUtils.repalcer(k,v);
	}
};

