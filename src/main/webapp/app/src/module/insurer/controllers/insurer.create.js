app.controller('createInsurerController', function($scope, $location, $rootScope, insurerService, toast, userFactory) {

   	$scope.formAddress = 'app/src/module/address/address.form.html';
   	$scope.states = states();
   	$scope.address = {};
   	$scope.insurer = {};

   	$scope.save = function(form) {

   		$scope.validate = false;

   		if(form.$valid) {

   			$scope.insurer.address = $scope.address;
   			$scope.insurer.user = userFactory.getUser();

   			insurerService.save($scope.insurer).then(function() {
   			    toast.open('success', 'Seguradora cadastrada com sucesso!');
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

   	$scope.goBack = function(){
   		$location.path('/insurers');
   	};
});