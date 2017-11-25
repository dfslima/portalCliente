app.factory('role', function ($location, $rootScope) {

    return {
        havePermission: function (ACTION) {
            if (localStorage.getItem('userAuth') == undefined) {
                if (!(ACTION === 'NEW_ACCOUNT' || ACTION === 'WELCOME')) {
                    $location.path('/');
                }
            }

            if (ACTION === 'NEW_ACCOUNT' || ACTION === 'WELCOME') {
                return;
            }

            if (!(ACTION === 'CREATE' || ACTION === 'EDIT' || ACTION === 'SEARCH' || ACTION === 'VIEW'))
                $location.path('/');
        }
    };
});