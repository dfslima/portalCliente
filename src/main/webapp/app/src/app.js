var app =
    angular.module('PortalCliente', ['restangular', 'ngRoute', 'ngLocale', 'ui.bootstrap',
        'ngMask', 'ui.utils.masks', 'vButton', 'ngMaterial',
        'PortalCliente.HomeController',
        'PortalCliente.userManager',
        'PortalCliente.customerManager',
        'PortalCliente.PropertyManager',
        'PortalCliente.producerManager',
        'PortalCliente.insurerManager',
        'PortalCliente.proposalManager'

    ]).config(['$routeProvider', 'RestangularProvider', '$httpProvider', '$mdThemingProvider',
        function ($routeProvider, RestangularProvider, $httpProvider, $mdThemingProvider) {

            $mdThemingProvider.theme('default').primaryPalette('indigo').accentPalette('orange');

            $routeProvider.when('/', {
                templateUrl: 'app/src/module/login/template/login.html',
                controller: 'loginController'
            });

            $routeProvider.when('/login', {
                templateUrl: 'app/src/module/login/template/login.html',
                controller: 'loginController'
            });

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

        }]).run(function (Restangular, $location, $rootScope, $window, authHelper, toast) {

        Restangular.setResponseInterceptor(function (data, operation, what, url, response) {
            return authHelper.response(response);
        });

        Restangular.setErrorInterceptor(function (response) {

            $rootScope.isBusy = false;

            if (response.status == 400) {
                toast.open('warning', response.data.message);
            }
            else if (response.status == 401) {
                $rootScope.logout();
                $location.path('/login');
            }
            else if (response.status == 403) {
                $location.path('/login');
                $rootScope.logout();
            }
            else if (response.status == 500) {
                toast.open('danger', 'Ops! Ocorreu um problema. Favor, tente novamente');
            }

            return false;
        });
    });
