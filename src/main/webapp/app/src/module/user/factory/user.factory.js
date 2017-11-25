app.factory('userFactory', function($rootScope) {

	var userFactory = {};

    userFactory.profileName = function(value) {

        // PARA EXIBIR O NOME DO TIPO DE PERFIL E NÃO O ENUM

        if (value == 'ROLE_ADMIN') {
            return 'Usuário administrador';
        }
        else {
            return 'Usuário comum';
        }
    };

    // PARA EXIBIR O NOME DO STATUS
    userFactory.statusName = function (status){
        if (status !== undefined) {
            if (status) {
                return 'Ativo';
            } else {
                return 'Inativo';
            }
        }
    };

    userFactory.getUser = function () {

        if ($rootScope.userAuth.user != undefined) {
            return $rootScope.userAuth.user;
        }
        else {
            return $rootScope.userAuth;
        }
    };

    return userFactory;

});