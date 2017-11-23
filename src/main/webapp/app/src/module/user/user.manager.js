angular.module('PortalCliente.userManager', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/users', {
            templateUrl: 'app/src/module/user/template/user.search.html',
            controller: 'searchUserController'
        });

        $routeProvider.when('/create-user', {
            templateUrl: 'app/src/module/user/template/user.form.html',
            controller: 'createUserController'
        });

        $routeProvider.when('/edit-user/:id', {
            templateUrl: 'app/src/module/user/template/user.form.html',
            controller: 'editUserController',
            resolve: {
                user: function (Restangular, $route) {
                    return Restangular.one('users', $route.current.params.id).get();
                }
            }
        });
    }]);