app.service('userService', function (Restangular) {

    this.search = function (name, login, profile, firstResult, maxResults) {
        return Restangular.all('users').getList({
            name: name,
            login: login,
            profile: profile,
            firstResult: firstResult,
            maxResults: maxResults
        });
    };

    this.count = function (name, login, profile) {
        return Restangular.one('users/count').get({name: name, login: login, profile: profile});
    };

    this.findAutoComplete = function (value, profile) {
        return Restangular.all('users/autoComplete').getList({value: value, profile: profile});
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