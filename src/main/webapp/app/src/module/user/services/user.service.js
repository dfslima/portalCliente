app.service('userService', function (Restangular, $rootScope, userFactory) {

    this.search = function (name, login, profile, firstResult, maxResults) {
        return Restangular.all('users').getList({
            name: name,
            login: login,
            profile: profile,
            userId: userFactory.getUser().id,
            id: $rootScope.userAuth.id,
            firstResult: firstResult,
            maxResults: maxResults
        });
    };

    this.count = function (name, login, profile) {
        return Restangular.one('users/count').get({
            name: name,
            login: login,
            profile: profile,
            userId: userFactory.getUser().id,
            id: $rootScope.userAuth.id
        });
    };

    this.findAutoComplete = function (value, profile) {
        return Restangular.all('users/autoComplete').getList({
            value: value,
            profile: profile,
            userId: userFactory.getUser().id
        });
    };

    this.save = function (user) {
        return Restangular.all('users').post(user);
    };

    this.edit = function (user) {
        user.route = "users";
        return user.put();
    };

    this.remove = function (user) {
        return user.remove();
    };
});