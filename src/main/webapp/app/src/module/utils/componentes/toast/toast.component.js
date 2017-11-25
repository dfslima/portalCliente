app.service('toast', function ($mdToast) {

    var toastPosition = {bottom: false, top: true, left: false, right: true};

    function getToastPosition() {
        return Object.keys(toastPosition)
            .filter(function (pos) {
                return toastPosition[pos];
            })
            .join(' ');
    }

    this.open = function (type, msg) {
        this.showMsg(type, msg, undefined);
    };

    this.showMsg = function (type, msg, hideDelay) {

        var time = hideDelay != undefined ? hideDelay : 4000;

        $mdToast.show({
            controller: 'ToastController',
            templateUrl: 'app/src/componentes/toast/toast.template.html',
            hideDelay: time,
            position: getToastPosition(),
            resolve: {
                messege: function () {
                    return {type: type, msg: msg};
                }
            }
        });
    };

}).controller('ToastController', function ($scope, $mdToast, messege) {

    $scope.msg = messege.msg;
    $scope.type = messege.type;

    $scope.closeToast = function () {
        $mdToast.hide();
    };

});
