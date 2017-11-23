app.controller('createUserController', function ($scope, $location, $rootScope, userService, maskFactory, toast) {

    angular.extend($scope, maskFactory);

    $rootScope.login = {color: false, menu: false, confirmation: false};

    $scope.perfilUser = profileUser();

    $scope.user = {status: true};

    $scope.save = function (formUser) {

        if (!formUser.$valid) {
            $scope.validate = true;
            return;
        }

        userService.save($scope.user).then(function (u) {
            toast.open('success', 'Usuário cadastrado com sucesso!');
            $location.path('/users');
        }, function(response) {
            toast.open('warning', response.message);
        });
    };

    $scope.goBack = function (b) {
        $location.path('/users');
    };
});