Storage = {
	store : function (id, object){
		return localStorage.setItem(id, JSON.stringify(object));
	},

	retrieve: function (id){
		var jsonObject = localStorage.getItem(id);
		if(jsonObject === null)
			return null;
			
        	return JSON.parse(jsonObject);
	}
};

