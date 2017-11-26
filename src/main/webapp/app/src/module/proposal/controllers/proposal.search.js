app.controller('searchProposalController', function ($scope, $location, $rootScope, $modal, $window, $timeout, $route, $filter, validationFactory,
                                                     paginationFactory, proposalService, propertyFactory, autoComplete, maskFactory, proposalFactory) {

    angular.extend($scope, paginationFactory);
    angular.extend($scope, propertyFactory);
    angular.extend($scope, proposalFactory);
    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);
    angular.extend($scope, validationFactory);

    $rootScope.isBusy = false;
    $scope.isShow = true;

    $scope.edit = function (id, propertyType) {
        $location.path('/viewPolicy/' + propertyType.toLowerCase() + '/' + id);
    };

    $scope.search = function () {

        $scope.itens = [];

        if (prepare()) {

            $rootScope.isBusy = true;
            proposalService.count($scope.proposal, $scope.board, customerId, $scope.cpfCnpj,
                $scope.startDate, $scope.endDate, insurerId, producerId).then(function (total) {

                if ($scope.validateSize(total.count)) {
                    getList();
                } else {
                    $rootScope.isBusy = false;
                    $scope.isShow = false;
                }
            });
        }
    };

    $scope.search();

    function getList() {
        proposalService.search($scope.proposal, $scope.board, customerId,
            $scope.cpfCnpj, $scope.startDate, $scope.endDate, insurerId, producerId, null, null, $scope.firstResult, $scope.maxResults).then(function (results) {
            $scope.itens = results;
            $rootScope.isBusy = false;
        });
    }

    $scope.onSelectInsurance = function ($item) {
        $scope.insurance = $item;
    };

    $scope.onSelectProducer = function ($item) {
        $scope.producer = $item;
    };

    var customerId = 0;
    var insurerId = 0;
    var producerId = 0;

    function prepare() {

        if ($scope.customer != undefined) {
            if ($scope.customer.name != undefined && $scope.customer.name != '') {
                customerId = $scope.customer.id;
            }
            else
                customerId = undefined;
        } else {
            customerId = undefined;
        }

        if ($scope.insurance != undefined) {
            if ($scope.insurance.corporateName != undefined && $scope.insurance.corporateName !== '') {
                insurerId = $scope.insurance.id;
            } else {
                insurerId = undefined;
            }

        } else {
            insurerId = undefined;
        }

        if ($scope.producer != undefined) {
            if ($scope.producer.name != undefined && $scope.producer.name !== '') {
                producerId = $scope.producer.id;
            } else {
                producerId = undefined;
            }
        } else {
            producerId = undefined;
        }

        return true;
    }

    $scope.clear = function () {
        $scope.proposal = $scope.board = $scope.customer = $scope.cpfCnpj = $scope.statusPolicy = $scope.producer = $scope.insurance = undefined;
        $scope.search();
    };

    $scope.pageChanged = function (value) {
        $scope.firstResult = value;
        getList()
    };

    $scope.onSelectCustomer = function ($item) {
        $scope.customer = $item;

        if ($scope.customer.type == 2)
            $scope.customer.name = $scope.customer.corporateName;
    };

    $scope.selectedProperties = function () {

        var modalInstance = $modal.open({
            templateUrl: 'app/src/module/property/template/selectProperty.html',
            size: 'sm',
            controller: function ($scope, $modalInstance, propertyService) {

                $scope.properties = propertiesForSelected();

                $scope.selected = function () {
                    $modalInstance.close($scope.property);
                };

                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
            }
        });

        modalInstance.result.then(function (pro) {
            $location.path('/create-proposal/' + pro.toLowerCase());
        });
    };

    $scope.getStatus = function (value) {
        if (value) {
            return 'A';
        } else {
            return 'P';
        }
    };
});