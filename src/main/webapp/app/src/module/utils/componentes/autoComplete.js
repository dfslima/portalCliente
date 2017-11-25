app.factory('autoComplete', function (insurerService, producerService, customerService, propertyService) {

    var autoComplete = {};

    autoComplete.autoCompleteInsurer = function (value) {
        return insurerService.findAutoComplete(value);
    };

    autoComplete.autoCompleteProducer = function (value) {
        return producerService.findAutoComplete(value);
    };

    autoComplete.autoCompleteCustomer = function (value) {
        return customerService.findAutoComplete(value);
    };

    autoComplete.autoCompleteProperty = function (value) {
        return propertyService.findAutoComplete(value);
    };

    return autoComplete;
});