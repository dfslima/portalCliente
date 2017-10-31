angular.module('PortalCliente.customerManager', [])
    .config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/customers', {
            templateUrl: 'app/src/module/customer/template/customer.search.html',
            controller: 'searchCustomerController'
        });
        $routeProvider.when('/create-customer', {
            templateUrl: 'app/src/module/customer/template/customer.form.html',
            controller: 'createCustomerController'
        });
        $routeProvider.when('/edit-customer/:id', {
            templateUrl: 'app/src/module/customer/template/customer.form.html',
            controller: 'editCustomerController',
            resolve: {
                customer: function (Restangular, $route) {
                    return Restangular.one('customers', $route.current.params.id).get();
                }
            }
        });
    }]);