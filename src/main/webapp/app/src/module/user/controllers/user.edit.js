app.controller('editUserController', function ($scope, Restangular, $location, $rootScope, $timeout,
                                               user, userService, toast, userFactory) {

    $scope.isVisible = true;
    $scope.perfilUser = profileUser();
    $scope.user = user;

    $scope.save = function (formUser) {

        if (!formUser.$valid) {
            $scope.validate = true;
            return;
        }

        if ($scope.user.user != undefined) {
            $scope.user.user = userFactory.getUser();
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