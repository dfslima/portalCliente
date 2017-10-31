app.controller('deleteCustomerController', function ($scope, $modalInstance, $timeout, maskFactory, customer, customerService) {

    angular.extend($scope, maskFactory);

    $scope.item = customer;

    $scope.ok = function () {
        customerService.remove(customer).then(function () {
            $modalInstance.close();
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});