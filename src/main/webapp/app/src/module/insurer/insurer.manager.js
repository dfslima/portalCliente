angular.module('PortalCliente.insurerManager',[])
    .config(['$routeProvider','RestangularProvider', function ($routeProvider, RestangularProvider) {

    	$routeProvider.when('/insurers', {
    	    templateUrl: 'app/src/module/insurer/template/insurer.search.html',
    	    controller: 'searchInsurerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('SEARCH');
                }
            }
    	});

    	$routeProvider.when('/create-insurer', {
    	    templateUrl: 'app/src/module/insurer/template/insurer.form.html',
    	    controller: 'createInsurerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('CREATE');
                }
            }
    	});

    	$routeProvider.when('/edit-insurer/:id', {
    	    templateUrl: 'app/src/module/insurer/template/insurer.form.html',
    	    controller: 'editInsurerController',
            resolve: {
                role: function (role) {
                    return role.havePermission('EDIT');
                }
            },
    		resolve: {
    			insurer: function(Restangular, $route){
    				return Restangular.one('insurers',$route.current.params.id).get();
    			}
    		}
        });
    }]);