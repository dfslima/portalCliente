app.controller('searchCustomerController', function ($scope, $location, $rootScope, $modal, $timeout, maskFactory, paginationFactory, customerService) {

    angular.extend($scope, maskFactory);
    angular.extend($scope, paginationFactory);


    $scope.isShow = true;

    $scope.edit = function (id) {
        $location.path('/edit-customer/' + id);
    };

    $scope.search = function () {
        $rootScope.isBusy = true;
        $scope.itens = [];
        customerService.count($scope.nameCustomer, $scope.cpfCnpjCustomer, $scope.typeCustomer, null, null)
            .then(function (total) {

                if ($scope.validateSize(total.count)) {
                    getList();
                    $rootScope.isBusy = false;

                } else {
                    $rootScope.isBusy = false;
                    $scope.isShow = false;
                }
        });
    };

    function getList() {
        customerService.search($scope.nameCustomer, $scope.cpfCnpjCustomer,
            $scope.typeCustomer, $scope.firstResult, $scope.maxResults).then(function (response) {
            $scope.itens = response;
        });
    }

    $scope.search();

    $scope.clear = function () {
        $scope.nameCustomer = $scope.cpfCnpjCustomer = $scope.typeCustomer = undefined;
        getList();
    };

    $scope.pageChanged = function (value) {
        $scope.firstResult = value;
        $scope.search();
    };

    $scope.remove = function (item) {
        var modalInstance = $modal.open({
            templateUrl: 'app/src/module/customer/template/customer.delete.html',
            size: 'sm',
            controller: 'deleteCustomerController',
            resolve: {
                customer: function () {
                    return item;
                }
            }
        });

        modalInstance.result.then(function (pro) {
            $scope.alertSuccess = [{type: "success", msg: 'Segurado removido com sucesso!'}];
            $scope.search();
        });
    };
});