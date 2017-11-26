app.factory('franchiseFactory', function () {

    var franchiseFactory = {};

    // Para mostrar na listagem
    franchiseFactory.getFranchiseDescription = function (value, propertyType) {
        if (propertyType == 'VEHICLE') {
            if (value == 'HULL') {
                return 'Casco';
            }
            else if (value == 'GLASSES') {
                return 'Vidros';
            }
            else if (value == 'HEADLIGHTS') {
                return 'Faróis';
            }
        }
    };

    return franchiseFactory;
})
    .factory('franchiseDescriptionFactory', function () {

        return {
            franchiseForPropertyType: function (propertyType) {
                if (propertyType != undefined) {
                    if (propertyType === 'VEHICLE') {
                        return [
                            {value: 'HULL', name: 'Casco'},
                            {value: 'GLASSES', name: 'Vidros'},
                            {value: 'HEADLIGHTS', name: 'Faróis'}
                        ];
                    }
                }
            }
        };
    });