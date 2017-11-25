app.service('loginService', function(Restangular) {
	
	this.login = function(user) {
		return Restangular.all('auth').post(user);
	};
});