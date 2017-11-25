app.controller('searchInsurerController', function($scope, $location, $rootScope, $modal, $window, $timeout, $route, $filter,
                                                   paginationFactory, policyService, propertyFactory, policyUtilities, autoComplete, maskFactory){

    angular.extend($scope, paginationFactory);
    angular.extend($scope, propertyFactory);
    angular.extend($scope, policyUtilities);
    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);

    $scope.objSituation = {};

    if(policyService.param) {
        $scope.statusPolicy = 'PROPOSAL';
        $scope.objSituation.situation = true;
    }else {
        $scope.objSituation.situation = false;
    }

    $scope.statusPolicies = segooConstants.policyStatus;

    $rootScope.isBusy = false;
    $scope.isShow = true;
    var customerId = 0;

    $scope.edit = function(id, propertyType) {
        $location.path('/viewPolicy/'+ propertyType.toLowerCase() + '/' + id);
    };

    $scope.search = function() {

        if(prepare($scope.customer)) {

            if($scope.statusPolicy != 'PROPOSAL')
                $scope.objSituation.situation = false;

            $rootScope.isBusy = true;
            policyService.count($scope.proposal, $scope.board, customerId, $scope.cpfCnpj,
                $scope.startDate, $scope.endDate, insurerId, producerId, null, null, $scope.statusPolicy, $scope.objSituation.situation).then(function(total) {
                if($scope.validateSize(total.count)){
                    policyService.search($scope.proposal, $scope.board, customerId,
                        $scope.cpfCnpj, $scope.startDate, $scope.endDate, insurerId, producerId, null, null, $scope.firstResult,
                        $scope.maxResults, $scope.statusPolicy, $scope.objSituation.situation).then(function (results) {
                        $scope.itens = results;
                        $rootScope.isBusy = false;
                    });
                }else {
                    $rootScope.isBusy = false;
                    $scope.isShow = false;
                }
            });

        }
    };
    ///////LIMPA O AUTOCOMPLETE
    $scope.clearAutoComplete = function() {
        $scope.customer.name = undefined;
    }
    $scope.clearAutoCompleteInsurer = function() {
        $scope.insurance.corporateName = undefined;
    }
    $scope.clearAutoCompleteProducer = function(){
        $scope.producer.name = undefined;
    }

    $scope.onSelectInsurance = function ($item) {
        $scope.insurance = $item;
    };

    $scope.onSelectProducer = function ($item) {
        $scope.producer = $item;
    };


    function isSimpleUser() {
        return $rootScope.userSystem.profile === 5 || $rootScope.userSystem.profile === 'ROLE_INCIDENT';
    }


    if(!isSimpleUser()) {
        $scope.search();
    }
    else {
        $scope.isShow = false;
    }


    function prepare(customer) {


        if (customer != undefined) {
            if (customer.name != undefined && customer.name != '') {
                customerId = customer.id;
            }
            else
                customerId = undefined;
        } else {
            customerId = undefined;
        }

        if ($scope.insurance != undefined) {
            if ($scope.insurance.corporateName != undefined && $scope.insurance.corporateName !== '') {
                insurerId = $scope.insurance.id;
            } else {
                insurerId = undefined;
            }

        } else {
            insurerId = undefined;
        }

        if ($scope.producer != undefined) {
            if ($scope.producer.name != undefined && $scope.producer.name !== '') {
                producerId = $scope.producer.id;
            } else {
                producerId = undefined;
            }
        } else {
            producerId = undefined;
        }

        if(isSimpleUser()) {
            if(!(($scope.proposal != undefined && $scope.proposal != '')
                    || ($scope.board != undefined && $scope.board != '')
                    || ($scope.customer != undefined && $scope.customer != '')
                    || ($scope.cpfCnpj != undefined && $scope.cpfCnpj != '')
                    || insurerId != undefined
                    || producerId != undefined)) {

                messageSegoo.showMsg('warning', 'Informe o nº da proposta, seguradora, produtor, nome/cpf do segurado ou a placa do veículo antes de pesquisar', 6000);
                return false;
            }
        }

        return true;
    }

    $scope.clear = function(){
        $scope.proposal = $scope.board = $scope.customer = $scope.cpfCnpj = $scope.statusPolicy = $scope.producer = $scope.insurance = undefined;
        $scope.objSituation.situation = false;

        if(isSimpleUser()) {
            $scope.isShow = false;
        } else {
            $scope.search();
        }
    };

    $scope.pageChanged = function(value) {
        $scope.firstResult = value;
        $scope.search();
    };

    $scope.onSelectCustomer = function ($item) {
        $scope.customer = $item;

        if($scope.customer.type == 2)
            $scope.customer.name = $scope.customer.corporateName;
    };

    $scope.selectedProperties = function() {

        var modalInstance = $modal.open({
            templateUrl: 'app/views/property/modal/selectedProperty.html',
            size: 'sm',
            controller: function($scope, $modalInstance, propertyService) {
                $scope.properties = propertiesForSelected();

                $scope.selected = function() {
                    $modalInstance.close($scope.property);
                };

                $scope.cancel = function() {
                    $modalInstance.dismiss('cancel');
                };
            }
        });

        modalInstance.result.then(function(pro) {
            $location.path('/createPolicy/' + pro.toLowerCase());
        });
    };

    $scope.getStatus = function (value){
        if (value){
            return 'A';
        }else {
            return 'P';
        }
    };

    $scope.generatePolicy = function(item) {

        policyService.getUnique(item.id).then(function(policyObj) {
            var modalInstance = $modal.open({
                templateUrl: 'app/views/policy/modal/policyGenerate.html',
                controller: 'generatePolicyController',
                resolve :{
                    policy: function() {
                        return policyObj;
                    }
                }
            });

            modalInstance.result.then(function (p) {
                $scope.search();
            });
        });
    };

    $scope.$on("$destroy", function() {
        if(policyService.param != 0)
            policyService.param = 0;
    });

});