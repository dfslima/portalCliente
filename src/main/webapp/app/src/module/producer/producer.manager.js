angular.module('PortalCliente.producerManager', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/producers', {
            templateUrl: 'app/src/module/producer/template/producer.search.html',
            controller: 'searchProducerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('SEARCH');
                }
            }
        });

        $routeProvider.when('/create-producer', {
            templateUrl: 'app/src/module/producer/template/producer.form.html',
            controller: 'createProducerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('CREATE');
                }
            }
        });

        $routeProvider.when('/edit-producer/:id', {
            templateUrl: 'app/src/module/producer/template/producer.form.html',
            controller: 'editProducerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('EDIT');
                }
            },
            resolve: {
                producer: function (Restangular, $route) {
                    return Restangular.one('producers', $route.current.params.id).get();
                }
            }
        });
    }]);