angular.module('PortalCliente.PropertyManager', [])
    .config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/properties/:propertyType', {
            templateUrl: 'app/src/module/property/template/property.search.html',
            controller: 'searchPropertyController'
        });
        $routeProvider.when('/create-property/:propertyType', {
            templateUrl: 'app/src/module/property/template/property.form.html',
            controller: 'createPropertyController'
        });
        $routeProvider.when('/edit-property/:propertyType/:id', {
            templateUrl: 'app/src/module/property/template/property.form.html',
            controller: 'editPropertyController',
            resolve: {
                property: function (Restangular, $route) {
                    return Restangular.one('properties', $route.current.params.id).get();
                }
            }
        });
    }]);