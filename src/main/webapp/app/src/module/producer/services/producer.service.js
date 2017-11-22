app.service('producerService', function(Restangular, $window, $location, $rootScope) {
	
	this.search = function(name, cpf, email, status, firstResult, maxResults) {
		return Restangular.one('producers').get({name:name, cpf:cpf, email:email, status:status, 
			 firstResult:firstResult, maxResults:maxResults});
	};

	this.count = function(name, cpf, email, status) {
		return Restangular.one('producers/count').get({name:name, cpf:cpf, email:email, status:status});
	};

	this.list = function() {
		return Restangular.all('producers').getList().$object;
	};

	this.save = function(producer) {
		return Restangular.all('producers').post(producer);
	};
	
	this.edit = function(producer) {
//        var obj =  Restangular.copy(producer);
		return producer.put();
	};

	this.remove = function(producer) {
	    var prod = Restangular.copy(producer);
	    prod.route = 'producers';
		return prod.remove();
	};
});