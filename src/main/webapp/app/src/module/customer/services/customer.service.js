app.service('customerService', function (Restangular, $rootScope, userFactory) {

    this.search = function (name, cpfCnpj, type, firstResult, maxResults) {
        return Restangular.all('customers').getList({
            name: name,
            cpfCnpj: cpfCnpj,
            type: type,
            userId: userFactory.getUser().id,
            firstResult: firstResult,
            maxResults: maxResults
        });
    };

    this.count = function (name, cpfCnpj, type, startCreateDate, endCreateDate) {
        return Restangular.one('customers/count').get({
            name: name,
            cpfCnpj: cpfCnpj,
            type: type,
            userId: userFactory.getUser().id,
            startCreateDate: startCreateDate,
            endCreateDate: endCreateDate
        });
    };

    this.findAutoComplete = function (param) {
        return Restangular.all('customers/autoComplete').getList({param: param, userId: userFactory.getUser().id});
    };

    this.get = function (id) {
        return Restangular.one('customers', id).get();
    };

    this.list = function () {
        return Restangular.all('customers').getList().$object;
    };

    this.save = function (customer) {
        return Restangular.all('customers').post(customer);
    };

    this.edit = function (customer) {
        return customer.put();
    };

    this.remove = function (customer) {
        return customer.remove();
    };
});