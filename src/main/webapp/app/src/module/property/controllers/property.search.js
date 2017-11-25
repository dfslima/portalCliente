app.controller('searchPropertyController', function ($route, $scope, $location, $rootScope, $modal, $window, $timeout, toast, validationFactory,
                                                     paginationFactory, maskFactory, autoComplete, propertyFactory, propertyService, routeProperty) {

    angular.extend($scope, paginationFactory);
    angular.extend($scope, validationFactory);
    angular.extend($scope, propertyFactory);
    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);

    $scope.propertyTypes = propertiesForSelected();
    $scope.propertyType = 'VEHICLE';

    $scope.edit = function (id) {
        $location.path('/viewProperty/' + $scope.propertyType.toLowerCase() + '/' + id);
    };

    // ## Pesquisa
    $scope.search = function () {

        if ($scope.routeProperty != undefined) {

            prepare($scope.customer);
            $rootScope.isBusy = true;

            var propertyType = $scope.routeProperty.type;
            propertyService.count($scope.propertyType, customerId, $scope.cpfCnpj, $scope.street,
                $scope.city, $scope.equipmentModel, $scope.vehicleLicensePlate, $scope.vehicleModelName,
                $scope.boatName, $scope.vehicleChassis, $scope.vehicleCodeFipe).then(function (total) {

                if ($scope.validateSize(total.count)) {
                    getList();
                    $rootScope.isBusy = false;
                } else {
                    $rootScope.isBusy = false;
                    $scope.isShow = false;
                }
            });
        }
    };

    function getList() {
        $scope.itens = propertyService.search($scope.propertyType, customerId, $scope.cpfCnpj, $scope.street, $scope.city,
            $scope.equipmentModel, $scope.vehicleLicensePlate, $scope.vehicleModelName, $scope.boatName,
            $scope.vehicleChassis, $scope.vehicleCodeFipe, $scope.firstResult, $scope.maxResults);
    }

    if (angular.isDefined($route.current.params.propertyType)) {
        $scope.propertyType = $route.current.params.propertyType.toUpperCase();
        $scope.routeProperty = routeProperty.list($scope.propertyType);
        $scope.search();
    }

    //altera pagina da pesquisa
    $scope.pageChanged = function (value) {
        $scope.firstResult = value;
        getList();
    };

    function prepare(customer) {

        if (customer != undefined) {
            if (customer.name != undefined && customer.name != '') {
                customerId = customer.id;
            }
            else
                customerId = undefined;
        } else {
            customerId = undefined;
        }
    }
    $scope.edit = function(id) {
        $location.path('/edit-property/'+$scope.propertyType.toLowerCase()+'/'+id);
    };


    $scope.selectClear = function () {
        $scope.customer = $scope.cpfCnpj = $scope.street = $scope.city = $scope.equipmentModel =
            $scope.vehicleLicensePlate = $scope.vehicleModelName = $scope.boatName = $scope.vehicleChassis = $scope.vehicleCodeFipe = undefined;
    };

    // seleciona a propriedade
    $scope.selectedSearch = function (selected) {
        if (selected != undefined) {
            $scope.selectClear();
            $location.path('/properties/' + selected.toLowerCase());
        }
    };

    $scope.clear = function () {
        $scope.selectClear();
        $scope.firstResult = 1;
        $scope.maxResults = 10;
        $scope.search();
    };

    $scope.onSelectCustomer = function ($item) {
        $scope.customer = $item;

        if ($scope.customer.type == 2)
            $scope.customer.name = $scope.customer.corporateName;
    };

    // seleciona o tipo de propriedade e redireciona para cadastrar nova propriedade
    $scope.selectedProperties = function () {
        propertyService.selectedProperty($scope.propertyType);
    };

    $scope.remove = function(item) {
        var modalInstance = $modal.open({
            templateUrl: 'app/src/module/property/template/property.delete.html',
            size: 'sm',
            resolve: {
                property:function() {
                    return item;
                }
            },

            controller: 'deletePropertyController'

        });

        modalInstance.result.then(function(property) {

            $rootScope.isBusy = true;
            propertyService.remove(property).then(function(data) {
                if (data != undefined) {
                    if (data.erro) {
                        $rootScope.isBusy = false;
                        toast.open('warning', data.message);
                    }
                }else {
                    $rootScope.isBusy = false;
                    toast.open('success', 'Ramo removido com sucesso!');
                    $scope.search();
                }
            });
        });
    };
});