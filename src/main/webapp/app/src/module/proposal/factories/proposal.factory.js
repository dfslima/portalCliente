app.factory('proposalFactory', function ($rootScope) {

    var proposalFactory = {};

    // Para mostrar na listagem
    proposalFactory.getStatusPolicy = function (value) {
        if (value == 'PROPOSAL') {
            return 'P';
        } else if (value == 'ACTIVE') {
            return 'A';
        } else if (value == 'ENDORSED') {
            return 'E';
        } else if (value == 'CANCELLED') {
            return 'C';
        } else if (value == 'EXPIRED') {
            return 'E';
        } else if (value == 'PROPOSAL_WITH_MOVEMENTS') {
            return 'PM';
        }
    };

// Para mostrar no tooltip
    proposalFactory.getStatusDescriptionPolicy = function (value) {
        if (value == 'PROPOSAL') {
            return 'Proposta';
        } else if (value == 'ACTIVE') {
            return 'Apólice';
        } else if (value == 'ENDORSED') {
            return 'Endossada';
        } else if (value == 'CANCELLED') {
            return 'Cancelada';
        } else if (value == 'EXPIRED') {
            return 'Expirada';
        } else if (value == 'PROPOSAL_WITH_MOVEMENTS') {
            return 'Proposta com movimentação';
        }
    };

// Listagem
    proposalFactory.getTypePolicy = function (value) {
        if (value == 'PROPOSAL') {
            return 'P';
        } else if (value == 'POLICY') {
            return 'A';
        }
    };

// Tooltip
    proposalFactory.getTypeDescriptionPolicy = function (value) {
        if (value == 'PROPOSAL') {
            return 'Proposta';
        } else if (value == 'POLICY') {
            return 'Apólice';
        }
    };

    return proposalFactory;
});