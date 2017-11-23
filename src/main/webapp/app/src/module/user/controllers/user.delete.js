app.controller('deleteUserController', function ($scope, $modalInstance, $timeout, user, userService) {

    $scope.item = user;

    $scope.ok = function () {

        userService.remove(user).then(function () {
                var alert = {type: "success", msg: 'Seguradora removida com sucesso'};
                $modalInstance.close(alert);
            },
            function (err) {
                var alert = {
                    type: "danger",
                    msg: 'Ops! Ocorreu um problema ao excluir esta seguradora. Tente novamente'
                };
                $modalInstance.close(alert);
            });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
});