app.factory('role', function ($location, $rootScope) {

    return {
        havePermission: function (ACTION) {
            if (localStorage.getItem('userAuth') == undefined || $rootScope.userAuth == undefined) {
                if (!(ACTION === 'NEW_ACCOUNT' || ACTION === 'WELCOME')) {
                    $location.path('/');
                }
            }

            if (ACTION === 'NEW_ACCOUNT' || ACTION === 'WELCOME') {
                return;
            }

            if (!(ACTION === 'CREAT' || ACTION === 'EDIT' || ACTION === 'SEARCH' || ACTION === 'VIEW'))
                $location.path('/');
        }
    };
});