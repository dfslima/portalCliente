app.controller('appController', function($scope, $location, $rootScope, $window) {


	$scope.getNameUser = function () {
		if ($rootScope.userAuth != undefined) {
			if ($rootScope.userAuth.name != undefined) {
				return $rootScope.userAuth.name.split(' ')[0];
			}
		}

		return '';
    };

	$rootScope.logout = function() {
		delete $rootScope.userAuth;
		localStorage.removeItem('userAuth');
		$location.path('/login');
	};

    $scope.goHome = function () {
        $location.path('/home')
    };
});