app.controller('welcomeUserController', function ($scope, $location, $rootScope) {

    $rootScope.login = {color: true, menu: true, confirmation: false};

    $scope.next = function () {
        $location.path('/home');
    };
});