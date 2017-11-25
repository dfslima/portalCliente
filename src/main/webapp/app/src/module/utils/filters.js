app.filter('mySort', function ($filter) {
        return function (input) {
            return $filter('orderBy')(input, "+");
        };
    })

    .filter('myDesc', function ($filter) {
        return function (input) {
            return $filter('orderBy')(input, "-");
        };
    })

    .filter('toString', function () {
        return function (input) {
            return input.toString();
        };
    })

    .filter('percent', function () {
        return function (input, qtd) {

            qtd = qtd === undefined ? 1 : qtd;

            if (input === undefined) {
                return 0 + '%';
            } else {
                return (input * 100).toFixed(qtd) + '%';
            }
        };
    })

    .filter('customCurrency', function ($filter) {
        return function (amount, currencySymbol) {
            var currency = $filter('currency');

            if (amount < 0) {
                return currency(amount, currencySymbol).replace("(", "- ").replace(")", "");
            }

            return currency(amount, currencySymbol);
        };
    })

    .filter('chiptext', function ($filter) {
        return function (text, length) {

            if (text != undefined) {
                return text.length > length ? text.substring(0, length) + '...' : text + '';
            } else {
                return '';
            }
        };
    })

    .filter('hidetext', function ($filter) {
        return function (text, length) {

            if (text != undefined) {
                return text.length > length ? text : '';
            } else {
                return '';
            }
        };
    })

    .filter('firstName', function ($filter) {

        return function (text) {

            if (text != undefined) {
                return text.split(' ')[0];
            } else {
                return '';
            }
        };
    })

    .filter('lastName', function ($filter) {

        return function (text) {

            if (text != undefined) {
                var name = text.split(' ');
                return name[0] + ' ' + name.slice(-1).pop();
            } else {
                return '';
            }
        };
    })

    .filter('formatOrdinalNumber', function ($filter) {

        return function (number) {

            if (number != undefined) {
                return number.toString() + 'º';
            } else {
                return '';
            }
        }
    });