app.controller('createCustomerController', function ($scope, $location, $rootScope, $timeout, $window, maskFactory, validationFactory, customerService, toast, userFactory) {

    angular.extend($scope, validationFactory);

    $scope.customer = {};
    $scope.address = {};
    $scope.calDays = days();
    $scope.calMonth = month();
    $scope.calYear = year();
    $scope.states = states();
    $scope.marital = maritalStatusForSelected();
    $scope.genreCustomer = genreForSelected();

    var pf = 'app/src/module/customer/template/customer.pf.html';
    var pj = 'app/src/module/customer/template/customer.pj.html';
    $scope.formAddress = 'app/src/module/address/address.form.html';

    $scope.type = 1;

    $scope.selectedCustomer = function (type) {
        $scope.type = parseInt(type);
        if ($scope.type == 1) {
            $scope.customer = {};
            $scope.formType = pf;
        } else if ($scope.type == 2) {
            $scope.customer = {};
            $scope.formType = pj;
        }
    };

    $scope.selectedCustomer(1);

    $scope.save = function (customerForm) {
        $scope.validate = false;
        $scope.invalidValueCpf = false;
        $scope.invalidValueCnpj = false;

        if (customerForm.$valid) {

            if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                $scope.customer.address = $scope.address;
            }

            // Retira os caractes especiais do cpf/cnpj
            $scope.customer.cpfCnpj = $scope.customer.cpfCnpj.replace(/\./g, "").replace("/", "").replace("-", "");

            if ($scope.type == 1) {
                // Concatena os campos vindos dos selects para formar a data
                $scope.customer.birthDate = $scope.day + "/" + $scope.month + "/" + $scope.year;
            }

            $scope.customer.type = $scope.type;
            $rootScope.isBusy = true;
            $scope.customer.user = userFactory.getUser();

            customerService.save($scope.customer).then(function (value) {
                $rootScope.isBusy = false;
                toast.open('success', 'Cliente salvo com sucesso.');
                $location.path('/customers');
            });
        }
        else {
            $rootScope.isBusy = false;
            $scope.validate = true;

            if ($scope.type == 1) {
                $scope.addressForm = $scope.pfForm = $scope.customerForm;
            }
            else if ($scope.type == 2) {
                $scope.addressForm = $scope.pjForm = $scope.customerForm;
            }
        }
    };

    $scope.goBack = function () {
        $location.path('/customers');
    };
})