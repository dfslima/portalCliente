var app =
    angular.module('PortalCliente', ['restangular', 'ngRoute', 'ngLocale', 'ui.bootstrap',
        'ngMask', 'ui.utils.masks', 'angular-jwt', 'vButton', 'ngMaterial',
        'PortalCliente.dashboardController',
        'PortalCliente.insurerManager', 'PortalCliente.insurerService',
        'PortalCliente.brokerageManager', 'PortalCliente.brokerageService',
        'PortalCliente.producerManager', 'PortalCliente.producerService',
        'PortalCliente.userManager', 'PortalCliente.userService',
        'PortalCliente.contantes',

        'PortalCliente.customerManager',
        'PortalCliente.PropertyManager'

    ]).config(['$routeProvider', 'RestangularProvider', '$httpProvider', '$mdThemingProvider',
        function ($routeProvider, RestangularProvider, $httpProvider, $mdThemingProvider) {

            $mdThemingProvider.theme('default').primaryPalette('indigo').accentPalette('orange');
            $routeProvider.when('/', {templateUrl: 'app/views/login/login.html', controller: 'loginController'});
            $routeProvider.when('/login', {templateUrl: 'app/views/login/login.html', controller: 'loginController'});
            $routeProvider.otherwise({redirectTo: '/'});

            var url = 'http://localhost:8080/PortalCliente/api/';

            RestangularProvider.setBaseUrl(url);

            $httpProvider.interceptors.push(function ($q, authHelper) {
                return {
                    'request': function (config) {
                        return authHelper.request(config, $q, url);
                    }
                };
            });

        }]).run(function (Restangular, $location, $rootScope, $window, authHelper) {

        Restangular.setResponseInterceptor(function (data, operation, what, url, response) {
            return authHelper.response(response);
        });

        Restangular.setErrorInterceptor(function (response) {

            if (response.status == 401) {
                $rootScope.logout();
                $location.path('/login');
            }
            else if (response.status == 403) {
                $location.path('/login');
                $rootScope.logout();
            }
            else {

            }
            return false;
        });
    });
