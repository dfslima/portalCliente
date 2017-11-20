app.controller('searchInsurerController', function ($scope, $location, $rootScope, $modal, toast,
    paginationFactory, maskFactory, insurerService) {

    angular.extend($scope, paginationFactory);
    angular.extend($scope, maskFactory);


    $scope.edit = function(id) {
        $location.path('/edit-insurer/'+id);
    };

     $scope.newInsurer = function () {
        $location.path('create-insurer');
     };

    $scope.pageChanged = function(value) {
        $scope.firstResult = value;
        getList();
    };

    $scope.search = function() {
        $scope.isShow = false;
        $scope.itens = [];
        insurerService.count($scope.name, $scope.cnpj, $scope.email).then(function(total) {
            if($scope.validateSize(total.count)){
                getList();
                $scope.isShow = true;
            }
        });
    };

    $scope.search();

    function getList() {
        insurerService.search($scope.name, $scope.cnpj, $scope.email, $scope.firstResult, $scope.maxResults).then( function (results) {
            $scope.itens = results;
        });
    }

    $scope.clear = function() {
        $scope.name = $scope.cnpj = $scope.email = undefined;
        $scope.search();
    };

    $scope.remove = function(item) {

        var modalInstance = $modal.open({
            templateUrl: 'app/src/module/insurer/template/insurer.delete.html',
            size:'sm',
            resolve: {
                insurer: function () {
                    return item;
                }
            },
            controller: 'deleteInsurerController'
        });

        modalInstance.result.then(function(pro) {
            toast.open(response.type, response.msg);

            if(response.type != 'danger') {
                $scope.search();
            }
        });
    };
});