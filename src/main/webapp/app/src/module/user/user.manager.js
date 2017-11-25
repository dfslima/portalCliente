angular.module('PortalCliente.userManager', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/users', {
            templateUrl: 'app/src/module/user/template/user.search.html',
            controller: 'searchUserController',
            resolve: {
                role: function (role) {
                    return role.havePermission('SEARCH');
                }
            }
        });

        $routeProvider.when('/create-user', {
            templateUrl: 'app/src/module/user/template/user.form.html',
            controller: 'createUserController',
            resolve: {
                role: function (role) {
                    return role.havePermission('CREATE');
                }
            }
        });

        $routeProvider.when('/create-new-account', {
            templateUrl: 'app/src/module/user/template/user.new-account.html',
            controller: 'newAccountUserController',
            resolve: {
                role: function (role) {
                    return role.havePermission('NEW_ACCOUNT');
                }
            }
        });

        $routeProvider.when('/welcome', {
            templateUrl: 'app/src/module/user/template/user.welcome.html',
            controller: 'welcomeUserController',
            resolve: {
                role: function (role) {
                    return role.havePermission('WELCOME');
                }
            }
        });

        $routeProvider.when('/edit-user/:id', {
            templateUrl: 'app/src/module/user/template/user.form.html',
            controller: 'editUserController',
            resolve: {
                role: function (role) {
                    return role.havePermission('EDIT');
                }
            },
            resolve: {
                user: function (Restangular, $route) {
                    return Restangular.one('users', $route.current.params.id).get();
                }
            }
        });
    }]);