angular.module('PortalCliente.customerManager', [])
    .config(['$routeProvider', function ($routeProvider) {

        $routeProvider.when('/customers', {
            templateUrl: 'app/src/module/customer/template/customer.search.html',
            controller: 'searchCustomerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('SEARCH');
                }
            }
        });
        $routeProvider.when('/create-customer', {
            templateUrl: 'app/src/module/customer/template/customer.form.html',
            controller: 'createCustomerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('CREATE');
                }
            }
        });
        $routeProvider.when('/edit-customer/:id', {
            templateUrl: 'app/src/module/customer/template/customer.form.html',
            controller: 'editCustomerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('EDIT');
                }
            },
            resolve: {
                customer: function (Restangular, $route) {
                    return Restangular.one('customers', $route.current.params.id).get();
                }
            }
        });
    }]);