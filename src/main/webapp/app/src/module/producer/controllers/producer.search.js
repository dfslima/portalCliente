app.controller('searchProducerController',
    function ($scope, $location, $rootScope, $modal, $timeout,
            producerService, paginationFactory, toast, maskFactory) {

    angular.extend($scope, paginationFactory);
    angular.extend($scope, maskFactory);

    $scope.edit = function (id) {
        $location.path('edit-producer/' + id);
    };

    $scope.newProducer = function () {
        $location.path('create-producer');
    };

    $scope.search = function () {
        $scope.isShow = false;
        $scope.itens = [];
        producerService.count($scope.name, $scope.cpf, $scope.email, $scope.status).then(function (total) {
            if ($scope.validateSize(total.count)) {
                getList();
                 $scope.isShow = true;
            }
        });
    };

    $scope.search();

    function getList() {
        producerService.search($scope.name, $scope.cpf, $scope.email, $scope.status,
            $scope.firstResult, $scope.maxResults).then(function (results) {
            $scope.itens = results;
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
            templateUrl: 'app/src/module/producer/template/producer.delete.html',
            size: 'sm',
            resolve: {
                producer: function () {
                    return item;
                }
            },
            controller: 'deleteProducerController'
        });

        modalInstance.result.then(function (response) {
            toast.open(response.type, response.msg);

            if(response.type != 'danger') {
                $scope.search();
            }
        });
    };
});