app.controller('editPropertyController', function ($scope, Restangular, $location, $rootScope, $window,
               routeProperty, propertyFactory, autoComplete, maskFactory, propertyService, property, toast, customerService) {

    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);
    angular.extend($scope, propertyFactory);

    $rootScope.isBusy = false;
    $scope.isDisplayFields = true;
    $scope.states = states();
    $scope.calYear = year();

    $scope.property = property;
    $scope.address = $scope.property.address;

    // PARA RECUPERAR O NOME DEPENDENDO DA KEY
    if ($scope.property.propertyType == 'VEHICLE') {
        $scope.vType = propertyFactory.typeVehicle($scope.property.vehicle.vehicleType);
    }

    $scope.customer = $scope.property.customer;
    $scope.routeProperty = routeProperty.form($scope.property.propertyType, 1);
    $scope.states = states();
    $scope.hideSelect = true;

    $scope.onSelectCustomer = function ($item) {
        customerService.get($item.id).then(function (data) {
            $scope.customer = data;
            $scope.isDisplayFields = true;
        });
    };

    $scope.clearCustomer = function () {
        $scope.customer = undefined;
        $scope.isDisplayFields = false;
    };

    $scope.save = function () {
        $scope.validate = false;
        $rootScope.isBusy = true;

        if ($scope.propertyForm.$valid) {

            if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                $scope.property.address = $scope.address;
            }

            $scope.property.customer = $scope.customer;
            propertyService.edit($scope.property).then(function (response) {

                if (response != undefined) {
                    if (response.erro) {
                        $rootScope.isBusy = false;
                        toast.open('warning', response.message);
                    }
                } else {
                    $rootScope.isBusy = false;
                    delete $window.sessionStorage.routeForm;
                    toast.open('success', 'Ramo alterada com sucesso!');
                    $location.path('/properties/' + $scope.property.propertyType.toLowerCase());
                }
            });
        } else {
            $scope.validate = true;
            $rootScope.isBusy = false;

            if ($scope.property.propertyType == 'CONDOMINIUM') {
                $scope.addressForm = $scope.condominiumForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'VESSEL') {
                $scope.vesselForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'COMPANY') {
                $scope.addressForm = $scope.companyForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'EQUIPMENT') {
                $scope.equipamentForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'RESIDENCE') {
                //$scope.residenceForm
                $scope.addressForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'VEHICLE') {
                $scope.vehicleForm = $scope.propertyForm;
            }
            else if ($scope.property.propertyType == 'LIFE') {
                $scope.lifeForm = $scope.propertyForm;
            }
        }
    };

    $scope.goBack = function () {
        delete $window.sessionStorage.routeForm;
        $location.path('/properties/' + $scope.property.propertyType.toLowerCase());
    };
});