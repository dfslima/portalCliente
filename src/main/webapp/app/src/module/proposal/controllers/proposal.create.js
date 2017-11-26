app.controller('createProposalController', function ($route, $scope, $location, $rootScope, $modal, $window, $filter,
                                                     proposalFactory, autoComplete, maskFactory, proposalService, customerService, propertyService,
                                                     propertyFactory, validationFactory, insurerService, producerService) {

    angular.extend($scope, autoComplete);
    angular.extend($scope, maskFactory);
    angular.extend($scope, validationFactory);
    angular.extend($scope, proposalFactory);
    angular.extend($scope, propertyFactory);

    $scope.propertyType = $route.current.params.propertyType.toUpperCase();
    $scope.details = propertyFactory.details($scope.propertyType);
    $scope.proposalFactory = proposalFactory;
    $scope.propertyName = propertyFactory.propertyName($scope.propertyType);

    var validaInsurer = false;

    if ($scope.propertyType === 'VEHICLE') {
        $scope.editValue = true;
    }

    $scope.producer = {};
    $scope.policy = {};
    $scope.editPropertyView = true;
    $scope.policy.startTerm = moment().format('DD/MM/YYYY');
    $scope.policy.dateTransmission = moment().format('DD/MM/YYYY');
    $scope.policy.endTerm = moment().add(1, 'year').format('DD/MM/YYYY');

    $scope.policy.policyCommission = $scope.policy.brokerageCommission = $scope.policy.producerCommission =
        $scope.policy.discount = $scope.policy.discountBrokerage = $scope.policy.netAward = $scope.policy.netAwardDiscount = $scope.policy.aggravation = 0;

    var percentProducerAux = percentPolicyAux = percentBrokerageAux = policyPDefault = 0;

    $scope.endCallYear = function (invalidstartTerm, invalidendTerm) {
        //$scope.endTerm.year = parseInt($scope.startTerm.year) + 1;
        if ($scope.policy.startTerm != undefined && $scope.policy.startTerm != '' && !invalidstartTerm && !invalidendTerm) {
            $scope.policy.endTerm = moment(reformatDate($scope.policy.startTerm))
                .add(+1, 'year').format('DD/MM/YYYY');
        }
    };

    $scope.startCallYear = function (invalidendTerm, invalidstartTerm) {

        if ($scope.policy.endTerm != undefined && $scope.policy.endTerm != '' && !invalidendTerm && !invalidstartTerm) {
            $scope.policy.startTerm = moment(reformatDate($scope.policy.endTerm))
                .add(-1, 'year').format('DD/MM/YYYY');
        }
    }

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

            console.log(result)

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
                $scope.validateValue =  true;
            } else {
                $scope.validateValue = false;
            }
        } else {
            $scope.validateValue = false;
        }
    };

    $scope.calculateValues = function (isDiscount, isAggravation, reference) {

        if (reference === 'FOR_BROKERAGE') {
            $scope.sliderBrokerage.value = proposalService.transformPercent30($scope.policy.brokerageCommissionPercent, $scope.policy.policyCommissionPercent);
        }

        if (reference === 'FOR_PRODUCER') {
            $scope.sliderProducer.value = proposalService.transformPercent30($scope.policy.producerCommissionPercent, $scope.policy.policyCommissionPercent);
        }

        var result = proposalService.calculateValues($scope.propertyType, $scope.policy.netAward,
            $scope.policy.policyCommissionPercent, $scope.sliderBrokerage.value, $scope.sliderProducer.value);

        if (result !== undefined) {

            $scope.policy.policyCommission = result.policyCommission;
            $scope.policy.brokerageCommission = result.brokerageCommission;
            $scope.policy.producerCommission = result.producerCommission;

            if (reference === 'FOR_POLICY') { //&& $scope.propertyType !== 'VEHICLE') {
                setValuesSliderBrokerage($scope.sliderBrokerage.value, $scope.policy.policyCommissionPercent);
                setValuesSliderProducer($scope.sliderProducer.value, $scope.policy.policyCommissionPercent);
                $scope.sliderBrokerage.options.disabled = $scope.sliderProducer.options.disabled = $scope.getWayCommissioningByType($rootScope.brokerage).securityType === 2 && $scope.propertyType === 'VEHICLE';
            }

            $scope.calculateCommissioning(reference); // Chama os benditos cálculos			
            $scope.sliderBrokerage.value = parseFloat($scope.policy.brokerageCommissionPercent) * 100;
            $scope.sliderProducer.value = parseFloat($scope.policy.producerCommissionPercent) * 100;


            // ##### CORRETORA
            // Converte para range de 100%
            $scope.policy.brokerageCommissionPercent = proposalService.transformPercent100($scope.policy.brokerageCommissionPercent, $scope.policy.policyCommissionPercent);

            // ##### PRODUTOR
            // Converte para range de 100%
            $scope.policy.producerCommissionPercent = proposalService.transformPercent100($scope.policy.producerCommissionPercent, $scope.policy.policyCommissionPercent);


            if (reference === 'FOR_DISCOUNT') {
                $scope.policy.brokerageCommissionPercent = parseFloat(1) - parseFloat($scope.policy.producerCommissionPercent);
                $scope.sliderBrokerage.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
                $scope.sliderProducer.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
            }

            if (reference === 'FOR_DISCOUNT_BROKERAGE') {
                $scope.policy.producerCommissionPercent = parseFloat(1) - parseFloat($scope.policy.brokerageCommissionPercent);
                $scope.sliderBrokerage.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
                $scope.sliderProducer.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
            }

            if (reference === 'FOR_AGGRAVATION') {
                $scope.sliderBrokerage.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
                $scope.sliderProducer.options.ceil = parseFloat($scope.policy.policyCommissionPercent) * 100;
            }
        }
    };


    $scope.calculateCommissioning = function (reference) {

        if (reference == 'FOR_POLICY') {
            proposalService.setPolicyCommission($scope.policy.policyCommission);
            proposalService.setPolicyPercent($scope.policy.policyCommissionPercent);
        }

        var result = proposalService.calculateCommissioning(reference,
            $scope.policy.producerCommission, $scope.sliderProducer.value,
            $scope.policy.brokerageCommission, $scope.sliderBrokerage.value,
            $scope.policy.policyCommission, $scope.policy.policyCommissionPercent,
            percentPolicyAux, $scope.policy.netAward, $scope.policy.discount,
            percentProducerAux, percentBrokerageAux, $scope.policy.discountBrokerage, $scope.policy.aggravation);

        if (result != undefined) {

            $scope.policy.brokerageCommission = result.brokerageComission;
            $scope.policy.brokerageCommissionPercent = result.brokeragePercent;

            $scope.policy.producerCommission = result.producerCommission;
            $scope.policy.producerCommissionPercent = result.producerPercent;

            $scope.policy.policyCommission = result.policyCommission;
            $scope.policy.policyCommissionPercent = result.policyPercent;

            $scope.policy.discount = result.discount;
            $scope.discountCommission = result.discountCommission;
            $scope.policy.discountBrokerage = result.discountBrokerage;
            $scope.policy.aggravation = result.aggravation;

            if (result.policyPAux != undefined) {
                percentPolicyAux = result.policyPAux;
            }
        }
    };

    $scope.returnNames = function (value) {
        return propertyFactory.description(value, $scope.propertyType);
    };

    $scope.goBack = function () {
        $location.path('/proposals');
    };

    function validateEmptyData() {

        if (typeof $scope.policy.netAward == "string") {
            $scope.policy.netAward = parseFloat($scope.policy.netAward);

            if (isNaN($scope.policy.netAward))
                $scope.policy.netAward = 0
        }

        if (typeof $scope.policy.aggravation == "string") {
            $scope.policy.aggravation = parseFloat($scope.policy.aggravation);

            if (isNaN($scope.policy.aggravation))
                $scope.policy.aggravation = 0
        }

        if ($scope.policy.discount == undefined) {
            $scope.policy.discount = 0;
        }

        if (typeof $scope.policy.discount == "string") {
            $scope.policy.discount = parseFloat($scope.policy.discount);

            if (isNaN($scope.policy.discount))
                $scope.policy.discount = 0
        }

        if ($scope.policy.discountBrokerage == undefined) {
            $scope.policy.discountBrokerage = 0;
        }

        if (typeof $scope.policy.discountBrokerage == "string") {
            $scope.policy.discountBrokerage = parseFloat($scope.policy.discountBrokerage);

            if (isNaN($scope.policy.discountBrokerage))
                $scope.policy.discountBrokerage = 0
        }
    }

    $scope.save = function () {

        $rootScope.isBusy = true;

        if ($scope.policyForm.$valid && $scope.validateProperty && $scope.validatePercentProducer
            && percentProducerAux >= $scope.policy.discount && $scope.validateDates($scope.policy.startTerm, $scope.policy.endTerm)) {

            // VERIFICA SE OS VALORES DOS AUTOCOMPLETES SÃO VÁLIDOS ANTES DE SALVAR
            if ($scope.validateInsurer() && $scope.validateCustomer() && $scope.validateProducer()) {

                validateEmptyData();

                processDataPolicy();

                removeListVehiclesIfNotFleet();

                proposalService.save($scope.policy).then(function (policyResponse) {

                    toast.open('success', 'Proposta salva com sucesso.');
                    $location.path('/policies');
                    $rootScope.isBusy = false;
                });

            } else {
                $rootScope.isBusy = false;
                $scope.validate = true;
            }
        }
        else {
            $scope.validate = true;
            $rootScope.isBusy = false;
        }
    };

    function processDataPolicy() {

        $scope.policy.fleet = $scope.isFleet;
        $scope.policy.insuranceId = $scope.insurer.id;
        $scope.policy.producerId = $scope.producer.id;
        delete $scope.customer.birthDate;
        delete $scope.customer.createdAt;
        delete $scope.customer.updateAt;
        $scope.policy.customer = $scope.customer;
        delete $scope.property.createdAt;
        delete $scope.property.customer;
        delete $scope.property.updateAt;
        $scope.policy.property = $scope.property;
        $scope.policy.type = 'PROPOSAL';
        $scope.policy.status = 'PROPOSAL';

        $scope.policy.producerCommission = parseFloat($scope.policy.producerCommission).toFixed(2);

        $scope.policy.brokerageCommission = parseFloat($scope.policy.brokerageCommission).toFixed(2);

        $scope.policy.policyCommission = parseFloat($scope.policy.policyCommission).toFixed(2);

        // Se for renovação, setamos a apólice renovada na nova proposta para manter a ligação entre elas
        if (policy != undefined) {
            policy.statusRenovation = isRenovation;
        }

        $scope.policy.policyRenovation = policy;
    }
});