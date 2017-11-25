app.service('insurerService', function (Restangular, $rootScope, userFactory) {

    this.search = function (name, cnpj, email, firstResult, maxResults) {
        return Restangular.all('insurers').getList({
            name: name,
            cnpj: cnpj,
            email: email,
            userId: userFactory.getUser().id,
            firstResult: firstResult,
            maxResults: maxResults
        });
    };

    this.count = function (name, cnpj, email) {
        return Restangular.one('insurers/count').get({
            name: name,
            cnpj: cnpj,
            email: email,
            userId: userFactory.getUser().id
        });
    };

    this.findAutoComplete = function (value) {
        return Restangular.all('insurers/autoComplete').getList({value: value, userId: userFactory.getUser().id});
    };

    this.list = function () {
        return Restangular.all('insurers').getList().$object;
    };

    this.save = function (insurer) {
        return Restangular.all('insurers').post(insurer);
    };

    this.edit = function (insurer) {
        return insurer.put();
    };

    this.remove = function (insurer) {
        return insurer.remove();
    };
});