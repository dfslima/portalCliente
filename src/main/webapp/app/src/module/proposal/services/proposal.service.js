app.service('proposalService', function (Restangular, $rootScope, userFactory) {

    this.search = function (proposal, board, customerId, cpfCnpj, startTransmissionDate, endTransmissionDate, insurerId, producerId, firstResult, maxResults) {
        return Restangular.all('proposals').getList({
            proposal: proposal,
            board: board,
            customerId: customerId,
            cpfCnpj: cpfCnpj,
            startTransmissionDate: startTransmissionDate,
            endTransmissionDate: endTransmissionDate,
            insurerId: insurerId,
            producerId: producerId,
            property: property,
            firstResult: firstResult,
            maxResults: maxResults,
            userId: userFactory.getUser().id
        });
    };

    this.count = function (proposal, board, customerId, cpfCnpj, startTransmissionDate, endTransmissionDate, insurerId, producerId) {
        return Restangular.one('proposals/count').get({
            proposal: proposal,
            board: board,
            customerId: customerId,
            cpfCnpj: cpfCnpj,
            startTransmissionDate: startTransmissionDate,
            endTransmissionDate: endTransmissionDate,
            insurerId: insurerId,
            producerId: producerId,
            userId: userFactory.getUser().id
        });
    };

    this.save = function (p) {
        return Restangular.all('proposals').customPOST(p, "", {}, {});
    };

    this.edit = function (policy) {
        return Restangular.all('proposals/' + policy.id).customPUT(policy);
    };

    this.remove = function (policy) {
        return Restangular.all('proposals/' + policy.id)
            .customDELETE("", {fileRepositoryPath: $rootScope.brokerage.fileRepositoryPath}, {});
    };

    this.validate = function(propertyId) {
        return Restangular.one('proposals/validate').get({propertyId:propertyId});
    };
});