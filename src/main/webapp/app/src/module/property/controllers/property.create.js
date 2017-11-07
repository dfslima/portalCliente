app.controller('createPropertyController', function ($route, $scope, $location, $rootScope, $window, $timeout, validationFactory,
                                                     routeProperty, maskFactory, autoComplete, propertyService, customerService, toast) {


    angular.extend($scope, validationFactory);
    angular.extend($scope, maskFactory);
    angular.extend($scope, autoComplete);

    // valida se já exise uma tipo e carrega os htmls
    if (angular.isDefined($route.current.params.propertyType)) {
        $scope.property = {propertyType: $route.current.params.propertyType.toUpperCase()};
        $scope.routeProperty = routeProperty.form($scope.property.propertyType);
    }

    $rootScope.isBusy = false;
    $scope.hideSelect = false;

    $scope.customer = {};
    $scope.property.vessel = {};
    $scope.property.company = {};
    $scope.property.equipment = {};
    $scope.property.vehicle = {};
    $scope.property.life = {};

    $scope.loadSelecteds = function () {

        $scope.states = states();
        $scope.calYear = year();

        $scope.condominiumTypes = condominiumType();
        $scope.condominiumSubTypes = condominiumSubType();

        $scope.homeBuildingTypes = homeBuildingType();
        $scope.homeTypes = homeType();
        $scope.homeUseTypes = homeUseType();

        $scope.vehicleTypes = vehicleType();

        $scope.vesselTypes = vesselTypes();

        $scope.businessZones = businessZone();
    };

    $scope.loadSelecteds();

    $scope.onSelectCustomer = function ($item) {
        $scope.customer = $item;

        if($scope.customer.type == 2)
            $scope.customer.name = $scope.customer.corporateName;

        $scope.isDisplayFields = true;
    };

    $scope.onSelectVehicleType = function ($item){
        $scope.vType = $item.name;
    };

    $scope.clearCustomer = function () {
        $scope.customer = undefined;
        $scope.isDisplayFields = false;
    };

    $scope.save = function () {

        $scope.validate = false;

        if ($scope.propertyForm.$valid) {

            $rootScope.isBusy = true;

            if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                $scope.property.address = $scope.address;
            }

            $scope.property.status = true;
            $scope.property.customer = $scope.customer;
            propertyService.save($scope.property).then(function (response) {
                delete $scope.customer;
                $rootScope.isBusy = false;
                toast.open('success', 'Propriedade salva com sucesso!');
                $location.path('/properties/' + $scope.property.propertyType.toLowerCase());
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
                $scope.residenceForm = $scope.propertyForm;
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
        $location.path('/properties/' + $scope.property.propertyType.toLowerCase());
    };
});