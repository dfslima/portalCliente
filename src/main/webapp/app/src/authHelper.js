app.service('authHelper', function($rootScope, $location, $window) {

	this.request =  function(config, q, url) {

		if (localStorage.getItem('userAuth') != undefined) {
			$rootScope.userAuth = angular.fromJson(localStorage.getItem('userAuth'));
			$rootScope.menuHTML = 'app/views/menu/menuDashboard.html';
		}else {
			if(!checkRoute(config))
				$location.path('/login');
		}

		return config || q.when(config);
	};

	this.response = function(response) {
		return response.data;
	};
	
	this.user = function() {

        var v = {};

		if($rootScope.userAuth === 1 || $rootScope.userAuth === 2){
			v.permission = true;
		}

		else{
			v.permission = false;
			v.route = $location.previous;
		}
	};
});