app.service('customerService', function (Restangular) {

    this.search = function (name, cpfCnpj, type, firstResult, maxResults) {
        return Restangular.all('customers').getList(
            {name: name, cpfCnpj: cpfCnpj, type: type, firstResult: firstResult, maxResults: maxResults}).$object;
    };

    this.count = function (name, cpfCnpj, type, startCreateDate, endCreateDate) {
        return Restangular.one('customers/count').get({
            name: name,
            cpfCnpj: cpfCnpj,
            type: type,
            startCreateDate: startCreateDate,
            endCreateDate: endCreateDate
        });
    };

    this.findAutoComplete = function (param) {
        return Restangular.all('customers/autoComplete').getList({param:param});
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