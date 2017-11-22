app.controller('deleteProducerController',
    function ($scope, $modalInstance, $timeout, producer, producerService, maskFactory) {

    angular.extend($scope, maskFactory);
    $scope.item = producer;

    $scope.ok = function () {
        producerService.remove(producer).then(function () {
            var alert = {type: "success", msg: 'Produtor removido com sucesso'};
            $modalInstance.close(alert);
        },
        function (err) {
            var alert = {type: "danger", msg: 'Ops! Ocorreu um problema ao excluir este produtor. Tente novamente'};
            $modalInstance.close(alert);
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});