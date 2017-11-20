app.service('insurerService', function (Restangular) {

   this.search = function(name, cnpj, email, firstResult, maxResults) {
   		return Restangular.all('insurers').getList({name:name, cnpj:cnpj, email:email, firstResult:firstResult, maxResults:maxResults});
   	};

   	this.count = function(name, cnpj, email) {
           return Restangular.one('insurers/count').get({name:name, cnpj:cnpj, email:email});
   	};

   	this.findAutoComplete = function(value, brokerageId) {
   		return Restangular.all('insurers/autoComplete').getList({value:value, brokerageId:brokerageId});
   	};

   	this.list = function() {
   		return Restangular.all('insurers').getList().$object;
   	};

   	this.save = function(insurer) {
   		return Restangular.all('insurers').post(insurer);
   	};

   	this.edit = function(insurer) {
   		return insurer.put();
   	};

   	this.remove = function(insurer) {
   		return insurer.remove();
   	};
});