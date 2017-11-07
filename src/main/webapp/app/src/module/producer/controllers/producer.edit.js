app.controller('editProducerController',
    function ($scope, $location, $rootScope, $timeout, producer, producerService, utilsService) {

    $scope.isVisible = true;
    $scope.calDays = days();
    $scope.calMonth = month();
    $scope.calYear = year();
    $scope.mStatus = maritalStatusForSelected();
    $scope.genres = genreForSelected();
    $scope.dateProducer = {};

    if (producer.address != undefined) {
        $scope.address = producer.address;
    } else {
        $scope.address = {};
    }

    $scope.producer = producer;
    $scope.producerFormHtml = 'app/views/producer/producer.form.html';
    $scope.titlePage = 'Confirmação de casdastro do produtor';

    if (producer.birthDate != undefined) {
        var str = producer.birthDate.split("/");
        $scope.dateProducer.day = str[0];
        $scope.dateProducer.month = str[1];
        $scope.dateProducer.year = str[2];
    }

    $scope.formAddress = 'app/views/address/addressForm.html';
    $scope.states = states();


    $scope.addressByCep = function () {
        $scope.addressErro = false;

        $scope.isLoading = true;
        var zipCode = $scope.address.zipCode;

        if (zipCode.length < 9) {
            $scope.isLoading = false;
            $scope.address = new Object();
            $scope.address.zipCode = zipCode;
            return $scope.addressErro = true;
        }

        utilsService.addressByCep(zipCode).then(function (data) {
            if (data != undefined) {
                $scope.isLoading = false;
                $scope.addressErro = data.addressError;
                $scope.address = data;
            } else {
                $scope.isLoading = false;
                $scope.addressErro = true;
                $scope.address = new Object();
                $scope.address.zipCode = zipCode;
            }
        });
    };


    $scope.save = function (producerForm) {

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
    };

    $scope.goBack = function () {
        $location.path('/producers');
    };
});	