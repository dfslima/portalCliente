app.controller('deleteProducerController',
    function ($scope, $modalInstance, $timeout, producer, producerService) {

    // Para que o modal receba os atributos do produtor
    // e exíba-os no momento da exclusão
    $scope.item = producer;

    $scope.ok = function () {
        producerService.remove(producer).then(function () {
            $scope.alertSuccess = [{type: "success", msg: 'Produtor removido com sucesso.'}];
            $modalInstance.close();
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});