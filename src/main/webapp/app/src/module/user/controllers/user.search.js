app.controller('searchUserController', function ($scope, $location, $rootScope, $modal, $timeout,
                                               userService, paginationFactory, userFactory, toast) {

    angular.extend($scope, paginationFactory);
    angular.extend($scope, userFactory);

    $scope.perfilsUser = profileUser();

    $scope.edit = function (id) {
        $location.path('/edit-user/' + id);
    };

    $scope.pageChanged = function (value) {
        $scope.firstResult = value;
        getList();
    };

    $scope.search = function () {
        $scope.isShow = false;
        $scope.itens = [];
        userService.count($scope.nameUser, $scope.loginUser, $scope.perfilUser).then(function (total) {
            if ($scope.validateSize(total.count)) {
                getList();
                $scope.isShow = true;
            }
        }, function (error) {
            $scope.isShow = false;
        });
    };

    $scope.search();

    function  getList() {
        userService.search($scope.nameUser, $scope.loginUser, $scope.perfilUser,
            $scope.firstResult, $scope.maxResults).then(function (results) {
            $scope.itens = results;
        });
    }

    $scope.clear = function () {
        $scope.nameUser = $scope.loginUser = $scope.perfilUser = undefined;
        $scope.search();
    };

    $scope.remove = function (item) {
        var modalInstance = $modal.open({
            templateUrl: 'app/views/user/userRemove.html',
            size: 'sm',
            resolve: {
                user: function () {
                    return item;
                }
            },
            controller: 'deleteUserController'
        });

        modalInstance.result.then(function (response) {

            toast.open(response.type, response.msg);

            if (response.type !== 'danger') {
                $scope.search();
            }
        });
    };
});