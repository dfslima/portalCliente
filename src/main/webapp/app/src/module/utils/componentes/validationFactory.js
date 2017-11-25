app.factory('validationFactory', function(){
	
	var validationFactory = {};
	
	validationFactory.lengthInputs = function(inputValue, minLength, 
			maxLength, nameInput, isRequired, $scope) {

		return lengthInputs(inputValue, minLength, maxLength,
					nameInput, isRequired, $scope);
	};
	
	validationFactory.validarCNPJ = function(cnpj) {
		return validarCNPJ(cnpj);
	};

	validationFactory.showNameCustomer = function (customer) {

		if(customer != undefined) {

            if(customer.type == 2)
                return customer.corporateName;
            else
                return customer.name;
        }
    };

	return validationFactory;	
	
});