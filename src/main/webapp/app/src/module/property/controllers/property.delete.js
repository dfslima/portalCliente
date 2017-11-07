app.controller('deletePropertyController', function ($scope, $modalInstance, $rootScope, property, propertyService) {

    $rootScope.isBusy = false

    $scope.ok = function () {
        $rootScope.isBusy = true;
        $modalInstance.close(property);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});