app.controller('deleteProposalController', function ($scope, $modalInstance, $timeout, maskFactory, proposal, proposalService) {

    angular.extend($scope, maskFactory);
    $scope.item = proposal;

    $scope.ok = function () {
        proposalService.remove(proposal).then(function () {
            var alert = {type: "success", msg: 'Proposta removida com sucesso'};
            $modalInstance.close(alert);
        },
        function (err) {
            var alert = {type: "danger", msg: 'Ops! Ocorreu um problema ao excluir esta proposta. Tente novamente'};
            $modalInstance.close(alert);
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});