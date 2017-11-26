app.controller('createProposalController', function ($route, $scope, $location, $rootScope, $modal, $window, $filter, franchiseFactory, franchiseDescriptionFactory,
                                                     proposalFactory, autoComplete, maskFactory, proposalService, customerService, propertyService,
                                                     propertyFactory, validationFactory, insurerService, producerService, userFactory, toast) {

    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);
    angular.extend($scope, validationFactory);
    angular.extend($scope, proposalFactory);
    angular.extend($scope, propertyFactory);
    angular.extend($scope, franchiseFactory);

    $scope.propertyType = $route.current.params.propertyType.toUpperCase();
    $scope.details = propertyFactory.details($scope.propertyType);
    $scope.proposalFactory = proposalFactory;
    $scope.propertyName = propertyFactory.propertyName($scope.propertyType);
    $scope.franchiseTypes = franchiseDescriptionFactory.franchiseForPropertyType($scope.propertyType);

    var validaInsurer = false;

    if ($scope.propertyType === 'VEHICLE') {
        $scope.editValue = true;
    }

    $scope.producer = {};
    $scope.proposal = {};
    $scope.franchise = {};
    $scope.proposal.franchises = [];
    $scope.editPropertyView = true;
    $scope.proposal.startTerm = moment().format('DD/MM/YYYY');
    $scope.proposal.dateTransmission = moment().format('DD/MM/YYYY');
    $scope.proposal.endTerm = moment().add(1, 'year').format('DD/MM/YYYY');

    $scope.proposal.proposalCommission = $scope.proposal.brokerageCommission = $scope.proposal.producerCommission =
        $scope.proposal.discount = $scope.proposal.discountBrokerage = $scope.proposal.netAward = $scope.proposal.netAwardDiscount = $scope.proposal.aggravation = 0;

    $scope.endCallYear = function (invalidstartTerm, invalidendTerm) {
        //$scope.endTerm.year = parseInt($scope.startTerm.year) + 1;
        if ($scope.proposal.startTerm != undefined && $scope.proposal.startTerm != '' && !invalidstartTerm && !invalidendTerm) {
            $scope.proposal.endTerm = moment(reformatDate($scope.proposal.startTerm))
                .add(+1, 'year').format('DD/MM/YYYY');
        }
    };

    $scope.startCallYear = function (invalidendTerm, invalidstartTerm) {

        if ($scope.proposal.endTerm != undefined && $scope.proposal.endTerm != '' && !invalidendTerm && !invalidstartTerm) {
            $scope.proposal.startTerm = moment(reformatDate($scope.proposal.endTerm))
                .add(-1, 'year').format('DD/MM/YYYY');
        }
    };

    $scope.onSelect = function ($item) {

        $scope.propertyList = [];

        $scope.customer = $item;
        $scope.isDisplayFields = true;

        propertyService.findAutoComplete('', $scope.propertyType, $item.id).then(function (result) {

            if (result.length > 0) {
                $scope.propertyList = result;
            }
            else {
                $scope.propertyList = [];
            }
        });
    };

    $scope.propertyData = {};
    $scope.onSelectProperty = function ($item) {

        $scope.property = $item;
        proposalService.validate($scope.property.id).then(function (result) {
            if (result.validate) {
                $scope.propertyDetails = true;
                $scope.erroProperty = false;
                $scope.validateProperty = true;
            } else {
                $scope.validateProperty = false;
                $scope.erroProperty = true;
            }
        });

    };

    $scope.clearCustomer = function () {
        $scope.customer = undefined;
        $scope.property = undefined;
        $scope.isDisplayFields = false;
        $scope.propertyDetails = false;
        $scope.erroProperty = false;
        $scope.propertyData = undefined;
    };

    $scope.clearProperty = function () {

        if ($scope.property.id == undefined) {
            $scope.clearCustomer();
        } else {
            $scope.property = undefined;
            $scope.propertyDetails = false;
            $scope.propertyData = undefined;
        }
    };

    $scope.validadeProducer = false;
    $scope.onSelectInsurer = function ($item) {

        if ($item !== undefined) {
            $scope.insurer = $item;
            $scope.validadeProducer = true;
        } else {
            $scope.validadeProducer = false;
        }
    };

    $scope.validadeProducer = false;

    $scope.insurerValidate = function () {
        validaInsurer = false;
    };

    $scope.onSelectProducer = function ($item) {
        if ($item !== undefined) {
            $scope.producer = $item;
            $scope.validateProducer();
        }
    };

    $scope.validateProducer = function () {

        if ($scope.producer != undefined) {
            if ($scope.producer.name != undefined && $scope.producer.name !== '') {
                $scope.validateValue = true;
            } else {
                $scope.validateValue = false;
            }
        } else {
            $scope.validateValue = false;
        }
    };

    $scope.returnNames = function (value) {
        return propertyFactory.description(value, $scope.propertyType);
    };

    $scope.goBack = function () {
        $location.path('/proposals');
    };

    function validateEmptyData() {

        if (typeof $scope.proposal.netAward == "string") {
            $scope.proposal.netAward = parseFloat($scope.proposal.netAward);

            if (isNaN($scope.proposal.netAward))
                $scope.proposal.netAward = 0
        }

        if (typeof $scope.proposal.aggravation == "string") {
            $scope.proposal.aggravation = parseFloat($scope.proposal.aggravation);

            if (isNaN($scope.proposal.aggravation))
                $scope.proposal.aggravation = 0
        }

        if ($scope.proposal.discount == undefined) {
            $scope.proposal.discount = 0;
        }

        if (typeof $scope.proposal.discount == "string") {
            $scope.proposal.discount = parseFloat($scope.proposal.discount);

            if (isNaN($scope.proposal.discount))
                $scope.proposal.discount = 0
        }

        if ($scope.proposal.discountBrokerage == undefined) {
            $scope.proposal.discountBrokerage = 0;
        }

        if (typeof $scope.proposal.discountBrokerage == "string") {
            $scope.proposal.discountBrokerage = parseFloat($scope.proposal.discountBrokerage);

            if (isNaN($scope.proposal.discountBrokerage))
                $scope.proposal.discountBrokerage = 0
        }
    }

    $scope.save = function () {

        $rootScope.isBusy = true;

        if ($scope.proposalForm.$valid) {

            validateEmptyData();

            processDataproposal();

            proposalService.save($scope.proposal).then(function (proposalResponse) {

                toast.open('success', 'Proposta salva com sucesso.');
                $location.path('/proposals');
                $rootScope.isBusy = false;
            });
        }
        else {
            $scope.validate = true;
            $rootScope.isBusy = false;
        }
    };

    function processDataproposal() {

        $scope.proposal.insurer = $scope.insurer;
        $scope.proposal.producer = $scope.producer;
        delete $scope.customer.birthDate;
        delete $scope.customer.createdAt;
        delete $scope.customer.updateAt;
        $scope.proposal.customer = $scope.customer;
        delete $scope.property.createdAt;
        delete $scope.property.customer;
        delete $scope.property.updateAt;
        $scope.proposal.property = $scope.property;
        $scope.proposal.type = 'PROPOSAL';
        $scope.proposal.status = 'PROPOSAL';
        $scope.proposal.user = userFactory.getUser();

        $scope.proposal.producerCommission = parseFloat($scope.proposal.producerCommission).toFixed(2);
        $scope.proposal.brokerageCommission = parseFloat($scope.proposal.brokerageCommission).toFixed(2);
        $scope.proposal.proposalCommission = parseFloat($scope.proposal.proposalCommission).toFixed(2);
    }

    $scope.calculate = function () {
        $scope.proposal.proposalCommission = (parseFloat($scope.proposal.netAward) * parseFloat($scope.proposal.proposalCommissionPercent)).toFixed(2);
        $scope.proposal.producerCommission = (parseFloat($scope.proposal.netAward) * parseFloat($scope.proposal.producerCommissionPercent)).toFixed(2);
        $scope.proposal.brokerageCommission = (parseFloat($scope.proposal.netAward) * parseFloat($scope.proposal.brokerageCommissionPercent)).toFixed(2);
    };

    // Adiciona uma ou várias franquias à proposta
    $scope.addFranchise = function (franchise) {

        if ($scope.proposal.franchises === undefined) {
            $scope.proposal.franchises = [];
        }

        var quantity = $scope.proposal.franchises.length;

        if (quantity > 0) {

            var exist = false;

            for (var i = 0; i < quantity; i++) {
                var item = $scope.proposal.franchises[i];

                if (item.franchiseType === franchise.franchiseType) {
                    exist = true;
                    i = quantity;
                }
            }
            if (!exist) {
                $scope.proposal.franchises.push(franchise);
            }

            exist = false;
        }
        else {
            $scope.proposal.franchises.push(franchise);
        }

        $scope.franchise = {};
    };

    // Remove uma franquia inserida no objeto apólice
    $scope.removeFranchise = function (item) {
        $scope.proposal.franchises.splice($scope.proposal.franchises.indexOf(item), 1);
    };
});