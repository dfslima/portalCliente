app.controller('editUserController', function ($scope, Restangular, $location, $rootScope, $timeout,
                                               user, userService, toast) {

    $scope.isVisible = true;
    $scope.perfilUser = profileUser();
    $scope.user = user;

    $scope.save = function (formUser) {

        if (!formUser.$valid) {
            $scope.validate = true;
            return;
        }

        userService.edit($scope.user).then(function () {
            toast.open('success', 'Dados do usuário alterados com sucesso!');
            $location.path('/users');
        }, function (response) {
            toast.open('warning', response.message);
        });
    };

    $scope.goBack = function () {
        $location.path('/users');
    };
});