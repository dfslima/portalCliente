app.controller('editCustomerController', function ($scope, $location, $rootScope, $timeout, $window, userFactory,
                                                   maskFactory, validationFactory, toast, customer, customerService) {
userFactory
    angular.extend($scope, validationFactory);

    $scope.showType = true;
    $rootScope.isBusy = false;

    $scope.calDays = days();
    $scope.calMonth = month();
    $scope.calYear = year();

    $scope.address = customer.address;
    $scope.customer = customer;

    $scope.marital = maritalStatusForSelected();
    $scope.genreCustomer = genreForSelected();
    $scope.states = states();

    var pf = 'app/src/module/customer/template/customer.pf.html';
    var pj = 'app/src/module/customer/template/customer.pj.html';
    $scope.formAddress = 'app/src/module/address/address.form.html';

    $scope.type = customer.type;

    $scope.selectedCustomer = function (type) {
        $scope.type = parseInt(type);
        if ($scope.type == 1) {
            $scope.formType = pf;
        } else if ($scope.type == 2) {
            $scope.formType = pj;
        }
    };

    $scope.selectedCustomer($scope.type);

    if (customer.birthDate != undefined) {
        var dataCustomer = customer.birthDate;
        var str = dataCustomer.split("/");
        $scope.day = str[0];
        $scope.month = str[1];
        $scope.year = str[2];
    }

    $scope.save = function (customerForm) {

        $scope.validate = false;
        $scope.invalidValueCpf = false;
        $scope.invalidValueCnpj = false;

        if (customerForm.$valid) {

            if ($scope.address !== undefined && $scope.address !== null && $scope.address !== '') {
                $scope.customer.address = $scope.address;
            }

            if ($scope.customer.type == 1) {
                // Concatena os campos vindos dos selects para formar a data
                $scope.customer.birthDate = $scope.day + "/" + $scope.month + "/" + $scope.year;
            }

            // Retira os caractes especiais do cpf/cnpj
            $scope.customer.cpfCnpj = $scope.customer.cpfCnpj.replace(/\./g, "").replace("/", "").replace("-", "");

            $rootScope.isBusy = true;
            $scope.customer.user = userFactory.getUser();

            customerService.edit($scope.customer).then(function (response) {

                $rootScope.isBusy = false;

                if (response != undefined) {
                    if (response.erro) {
                        toast.open('warning', response.message);
                        return;
                    }
                }

                toast.open('success', 'Cliente alterado com sucesso');
                $location.path('/customers');
            });
        }
        else {
            $rootScope.isBusy = false;
            $scope.validate = true;


            if ($scope.customer.type == 1) {
                $scope.addressForm = $scope.pfForm = $scope.customerForm;
            }
            else if ($scope.customer.type == 2) {
                $scope.addressForm = $scope.pjForm = $scope.customerForm;
            }
        }
    };

    $scope.goBack = function () {
        $location.path('/customers');
    };
});