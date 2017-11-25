app.factory('paginationFactory', function () {

    var paginationFactory = {};

    paginationFactory.pagination = this.pagination = 'app/src/componentes/view/pagination.html';
    paginationFactory.firstResult = this.firstResult = 1;
    paginationFactory.maxResults = this.maxResults = 10;
    paginationFactory.isShow = this.isShow = false;

    paginationFactory.validateSize = function (value) {
        var total = parseInt(value);
        if (total > 0) {
            paginationFactory.isShow = this.isShow = true;
            paginationFactory.totalItens = this.totalItens = total;
            paginationFactory.maxSize = this.maxSize = maxSizePagination(total);
            return true;
        } else {
            return false;
        }
    };
    return paginationFactory;
});