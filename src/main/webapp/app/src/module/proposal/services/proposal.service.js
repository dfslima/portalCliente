app.service('proposalService', function (Restangular, $rootScope, userFactory) {

    this.search = function (proposal, board, customerId, cpfCnpj, startTransmissionDate, endTransmissionDate, insurerId, producerId, property, propertyId, firstResult, maxResults, status, situation) {
        return Restangular.all('policies').getList({
            proposal: proposal,
            board: board,
            customerId: customerId,
            cpfCnpj: cpfCnpj,
            startTransmissionDate: startTransmissionDate,
            endTransmissionDate: endTransmissionDate,
            insurerId: insurerId,
            producerId: producerId,
            property: property,
            propertyId: propertyId,
            firstResult: firstResult,
            maxResults: maxResults,
            status: status,
            situation: situation
        });
    };

    this.count = function (proposal, board, customerId, cpfCnpj, startTransmissionDate, endTransmissionDate, insurerId, producerId, property, propertyId, status, situation) {
        return Restangular.one('policies/count').get({
            proposal: proposal,
            board: board,
            customerId: customerId,
            cpfCnpj: cpfCnpj,
            startTransmissionDate: startTransmissionDate,
            endTransmissionDate: endTransmissionDate,
            insurerId: insurerId,
            producerId: producerId,
            property: property,
            propertyId: propertyId,
            status: status,
            situation: situation
        });
    };

    this.save = function (p) {
        return Restangular.all('policies').customPOST(p, "", {}, {});
    };

    this.edit = function (policy) {
        return Restangular.all('policies/' + policy.id).customPUT(policy);
    };

    this.remove = function (policy) {
        return Restangular.all('policies/' + policy.id).customDELETE("", {fileRepositoryPath: $rootScope.brokerage.fileRepositoryPath}, {});
    };
});