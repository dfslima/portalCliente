app.controller('editProducerController',
    function ($scope, $location, $rootScope, $timeout, producer, producerService) {

    $scope.isVisible = true;
    $scope.genres = genreForSelected();

    if (producer.address != undefined) {
        $scope.address = producer.address;
    } else {
        $scope.address = {};
    }

    $scope.producer = producer;
    $scope.titlePage = 'Alterar dados do produtor';
    $scope.formAddress = 'app/views/address/addressForm.html';
    $scope.states = states();

    $scope.save = function (producerForm) {

        if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
            $scope.producer.address = $scope.address;
        }

        $rootScope.isBusy = true;
        producerService.edit($scope.producer).then(function () {
            $rootScope.isBusy = false;
            $location.path('/producers');
        }, function () {
            $rootScope.isBusy = false;
        });
    };

    $scope.goBack = function () {
        $location.path('/producers');
    };
});	