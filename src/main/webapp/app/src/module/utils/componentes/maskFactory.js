app.factory('maskFactory', function(){
	
	var maskFactory = {};
	
	maskFactory.maskCpfCnpj = function(value) {
		if(value != undefined){
			return maskCpfCnpj(value);
		}
	};
	
	maskFactory.maskPhone = function(value) {
		if(value != undefined){
			return maskPhone(value);
		}
	};
	
	
	return maskFactory;	
	
});