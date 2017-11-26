angular.module('PortalCliente.proposalManager', [])
    .config(['$routeProvider', 'RestangularProvider', function ($routeProvider, RestangularProvider) {

        $routeProvider.when('/proposals', {
            templateUrl: 'app/src/module/proposal/template/proposal.search.html',
            controller: 'searchProposalController',
            resolve: {
                role: function (role) {
                    return role.havePermission('SEARCH');
                }
            }
        });

        $routeProvider.when('/create-proposal/:propertyType', {
            templateUrl: 'app/src/module/proposal/template/proposal.form.html',
            controller: 'createProposalController',
            resolve: {
                role: function (role) {
                    return role.havePermission('CREATE');
                }
            }
        });

        $routeProvider.when('/edit-proposal/:propertyType/:id', {
            templateUrl: 'app/src/module/proposal/template/proposal.form.html',
            controller: 'editProposalController',
            resolve: {
                role: function (role) {
                    return role.havePermission('EDIT');
                }
            },
            resolve: {
                proposal: function (Restangular, $route) {
                    return Restangular.one('proposals', $route.current.params.id).get();
                }
            }
        });
    }]);