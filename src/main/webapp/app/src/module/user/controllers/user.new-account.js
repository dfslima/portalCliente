app.controller('newAccountUserController', function ($scope, $location, $rootScope, userService, maskFactory, toast) {

    angular.extend($scope, maskFactory);

    $rootScope.login = {color: true, menu: true, confirmation: false};

    $scope.user = {status: true, profile: 'ROLE_ADMIN'};

    $scope.save = function (formUser) {

        if (!formUser.$valid) {
            $scope.validate = true;
            return;
        }

        userService.save($scope.user).then(function (data) {

            toast.open('success', 'Usuário cadastrado com sucesso!');
            localStorage.setItem("userAuth", JSON.stringify(data));
            delete $rootScope.login;
            $location.path('/welcome');

        }, function(response) {
            toast.open('warning', response.message);
        });
    };

    $scope.goBack = function (b) {
        $location.path('/login');
    };
});