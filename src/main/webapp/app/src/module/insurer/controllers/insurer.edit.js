app.controller('editInsurerController', function($scope, toast, $location, $rootScope, insurer, insurerService) {

   	$scope.isVisible = true;

   	$scope.address = insurer.address;
   	$scope.insurer = insurer;

   	$scope.formAddress = 'app/views/address/addressForm.html';
   	$scope.states = states();

   	$scope.goBack = function() {
   		$location.path('/insurers');
   	};

   	$scope.closeAlert = function(index) {
   		$scope.alertSuccessEdit.splice(index, 1);
   	};

   	$scope.save = function(form) {
   		$scope.validate = false;

        if(form.$valid) {

            $scope.insurer.address = $scope.address;

            insurerService.edit($scope.insurer).then(function() {
                toast.open('success', 'Dados da seguradora alterdos com sucesso!');
                $location.path('/insurers');
            }, function(response) {
                toast.open('warning', response.message);
            });
        }
        else {
            $scope.validate = true;
            $scope.addressForm = form;
        }
   	};
 });