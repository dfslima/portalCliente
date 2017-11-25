angular.module('PortalCliente.HomeController', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/home', {
            templateUrl: 'app/src/module/home/home.html',
            controller: 'homeController',
            resolve: {
                role: function (role) {
                    return role.havePermission('VIEW');
                }
            }
        });
    }]);