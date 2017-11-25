app.controller('createProducerController',
    function ($scope, $modal, $location, $rootScope, $timeout, producerService, userFactory) {

        $scope.genres = genreForSelected();
        $scope.address = {};
        $scope.formAddress = 'app/src/module/address/address.form.html';
        $scope.states = states();
        $scope.titlePage = 'Cadastro de produtores';

        $scope.validate = false;
        $scope.save = function (producerForm) {
            if (producerForm.$valid) {

                if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                    $scope.producer.address = $scope.address;
                }

                $rootScope.isBusy = true;
                $scope.producer.user = userFactory.getUser();

                producerService.save($scope.producer).then(function () {
                    $rootScope.isBusy = false;
                    $location.path('/producers');
                }, function () {
                    $rootScope.isBusy = false;
                });
            } else {
                $scope.validate = true;
                $scope.addressForm = $scope.producerForm;
            }
        };
    });