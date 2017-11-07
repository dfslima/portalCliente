app.controller('createProducerController',
    function ($scope, $modal, $location, $rootScope, $timeout, producerService) {

        $scope.mStatus = maritalStatusForSelected();
        $scope.genres = genreForSelected();
        $scope.calDays = days();
        $scope.calMonth = month();
        $scope.calYear = year();
        $scope.address = {};
        $scope.formAddress = 'app/views/address/addressForm.html';
        $scope.states = states();
        $scope.dateProducer = {};

        $scope.titlePage = 'Confirmação de casdastro do produtor';

        $scope.validate = false;
        $scope.save = function (producerForm) {
            if (producerForm.$valid) {
                if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                    $scope.producer.address = $scope.address;
                }

                $scope.producer.birthDate = $scope.dateProducer.day + "/" + $scope.dateProducer.month + "/" + $scope.dateProducer.year;

                $rootScope.isBusy = true;
                producerService.confirmRegister($scope.producer).then(function () {
                    $rootScope.isBusy = false;
                    $scope.successConfirm = true;
                }, function () {
                    $scope.successConfirm = false;
                    $rootScope.isBusy = false;
                });
            } else {
                $scope.validate = true;
                $scope.addressForm = $scope.producerForm;
            }
        };
    });