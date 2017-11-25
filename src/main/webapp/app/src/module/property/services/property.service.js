app.service('propertyService', function (Restangular, $location, $rootScope, userFactory) {

    this.search = function (propertyType, customerId, cpfCnpj, street, city,
                            equipmentModel, vehicleLicensePlate, vehicleModelName, boatName, vehicleChassis, vehicleCodeFipe, firstResult, maxResults) {

        return Restangular.all('properties').getList(
            {
                propertyType: propertyType,
                customerId: customerId,
                cpfCnpj: cpfCnpj,
                street: street,
                city: city,
                equipmentModel: equipmentModel,
                vehicleLicensePlate: vehicleLicensePlate,
                vehicleModelName: vehicleModelName,
                boatName: boatName,
                vehicleChassis: vehicleChassis,
                vehicleCodeFipe: vehicleCodeFipe,
                userId: userFactory.getUser().id,
                firstResult: firstResult,
                maxResults: maxResults
            }).$object;
    };

    this.count = function (propertyType, customerId, cpfCnpj, street, city,
                           equipmentModel, vehicleLicensePlate, vehicleModelName, boatName, vehicleChassis, vehicleCodeFipe) {

        return Restangular.one('properties/count').get(
            {
                propertyType: propertyType,
                customerId: customerId,
                cpfCnpj: cpfCnpj,
                street: street,
                city: city,
                equipmentModel: equipmentModel,
                vehicleLicensePlate: vehicleLicensePlate,
                vehicleModelName: vehicleModelName,
                boatName: boatName,
                vehicleChassis: vehicleChassis,
                vehicleCodeFipe: vehicleCodeFipe,
                userId: userFactory.getUser().id
            });
    };

    this.findAutoComplete = function (value, type, customerId) {
        return Restangular.all('properties/autoComplete').getList({
            value: value,
            type: type,
            customerId: customerId,
            userId: userFactory.getUser().id
        });
    };

    this.list = function () {
        return Restangular.all('properties').getList().$object;
    };

    this.getUnique = function (id) {
        return Restangular.one('properties', id).get();
    };

    this.save = function (property) {
        return Restangular.all('properties').post(property);
    };

    this.edit = function (property) {
        return property.put();
    };

    this.remove = function (property) {
        return property.remove();
    };

    var propertyType = null;

    this.setPropertyType = function (p) {
        propertyType = p;
    };
    this.getPropertyType = function () {
        return propertyType;
    };

    // seleciona o tipo de propriedade e redireciona para cadastrar nova propriedade
    this.selectedProperty = function (type) {

        //verifica se já existe no scope um tipo de propriedade
        if (angular.isDefined(type)) {
            $location.path('/create-property/' + type.toLowerCase());
        }
        // não existe um tipo de propriedade setado no scope então abre o modal para selecionar.
        else {
            var modalInstance = $modal.open({
                templateUrl: 'app/views/property/modal/selected-property.html',
                size: 'sm',
                controller: function ($scope, $modalInstance, routeProperty) {
                    $scope.properties = propertiesForSelected();
                    $scope.isBusy = false;

                    $scope.selected = function () {
                        $modalInstance.close(routeProperty.form($scope.property, 1));
                    };

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            });

            modalInstance.result.then(function (pro) {
                $location.path('/createProperty/' + pro.type.toLowerCase());
            });
        }
    };
});