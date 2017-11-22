angular.module('PortalCliente.producerManager', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/producers', {
            templateUrl: 'app/src/module/producer/template/producer.search.html',
            controller: 'searchProducerController'
        });

        $routeProvider.when('/create-producer', {
            templateUrl: 'app/src/module/producer/template/producer.form.html',
            controller: 'createProducerController'
        });

        $routeProvider.when('/edit-producer/:id', {
            templateUrl: 'app/src/module/producer/template/producer.form.html',
            controller: 'editProducerController',
            resolve: {
                producer: function (Restangular, $route) {
                    return Restangular.one('producers', $route.current.params.id).get();
                }
            }
        });
    }]);