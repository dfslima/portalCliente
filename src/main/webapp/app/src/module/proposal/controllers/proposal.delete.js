app.controller('deleteInsurerController', function ($scope, $modalInstance, $timeout, maskFactory, insurer, insurerService) {

    angular.extend($scope, maskFactory);
    $scope.item = insurer;

    $scope.ok = function () {
        insurerService.remove(insurer).then(function () {
            var alert = {type: "success", msg: 'Seguradora removida com sucesso'};
            $modalInstance.close(alert);
        },
        function (err) {
            var alert = {type: "danger", msg: 'Ops! Ocorreu um problema ao excluir esta seguradora. Tente novamente'};
            $modalInstance.close(alert);
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});