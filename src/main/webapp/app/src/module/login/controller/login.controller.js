app.controller('loginController', function ($scope, $location, $rootScope, $window, loginService) {

    $scope.user = {};

    /**
     * Garante que tendo dados do usuário e dados da corretora logada na sessão, o sistema se mantenha
     * no dashbord ao digitar uma url errada
     * */
    if (localStorage.getItem('userAuth') != undefined) {
        $location.path('/home');
    } else {
        $rootScope.login = {color: true, menu: true, confirmation: false};
        $location.path('/login');
    }

    $scope.logar = function () {

        $scope.isBusy = true;
        $scope.msgError = 'Fazendo login. Aguarde...';

        loginService.login($scope.user).then(function (data) {

            if (data.erro) {
                $scope.msgError = data.message;
                return;
            }

            localStorage.setItem("userAuth", JSON.stringify(data));
            delete $rootScope.login;
            $location.path('/home');

        }, function (error) {
            $scope.isBusy = false;
            $scope.msgError = 'E-mail ou senha inválido. Verifique e tente novamente';
        });
    };
});