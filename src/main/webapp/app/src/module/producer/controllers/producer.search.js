app.controller('searchProducerController',
    function ($scope, $location, $rootScope, $modal, $timeout, producerService, paginationFactory, toast) {

    angular.extend($scope, paginationFactory);

    // list producers
    $scope.isShow = false;

    $scope.statusProd = statusProducer();

    $scope.statusProducer = function (value) {
        if (value == 'REGISTERED') {
            return 'Ativo';
        } else {
            return 'Pendente';
        }
    };

    $scope.edit = function (id) {
        $location.path('edit-producer/' + id);
    };

    $scope.newProducer = function () {
        $location.path('create-producer');
    };

    $scope.search = function () {
        $scope.noResults = false;
        $scope.itens = [];
        producerService.count($scope.name, $scope.cpf, $scope.email, $scope.status).then(function (total) {
            if ($scope.validateSize(total.count)) {
                getList();
            } else {
                $scope.noResults = true;
            }
        });
    };

    $scope.search();

    function getList() {
        producerService.search($scope.name, $scope.cpf, $scope.email, $scope.status,
            $scope.firstResult, $scope.maxResults).then(function (results) {
            $scope.itens = results;
            $scope.classColor = true;
        });
    }

    $scope.pageChanged = function (value) {
        $scope.firstResult = value;
        getList();
    };

    $scope.clear = function () {
        $scope.name = $scope.cpf = $scope.email = $scope.status = undefined;
        $scope.search();
    };

    $scope.remove = function (item) {
        var modalInstance = $modal.open({
            templateUrl: 'app/views/producer/producer.delete.html',
            size: 'sm',
            resolve: {
                producer: function () {
                    return item;
                }
            },
            controller: 'deleteProducerController'
        });

        modalInstance.result.then(function (pro) {
            toast.open('success', 'Produtor salvo com sucesso!');
        });
    };
});