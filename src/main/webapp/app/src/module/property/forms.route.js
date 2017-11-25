function formsProperties(value, type) {
    if ('VESSEL' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Embarcação",
                template: "app/src/module/property/template/sub-template/vessel/vessel.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Embarcação",
                template: "app/src/module/property/template/sub-template/vessel/vesselList.html",
                type: value
            };
        }
    }
    else if ('COMPANY' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Empresa",
                template: "app/src/module/property/template/sub-template/company/company.form.html",
                address: "app/src/module/address/address.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Empresa",
                template: "app/src/module/property/template/sub-template/company/company.list.html",
                type: value
            };
        }
    }
    else if ('EQUIPMENT' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Equipamento",
                template: "app/src/module/property/template/sub-template/equipment/equipment.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Equipamento",
                template: "app/src/module/property/template/sub-template/equipment/equipment.list.html",
                type: value
            };
        }
    }
    else if ('RESIDENCE' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Residência",
                template: "app/src/module/property/template/sub-template/residence/residence.form.html",
                address: "app/src/module/address/address.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Residência",
                template: "app/src/module/property/template/sub-template/residence/residence.list.html",
                type: value
            };
        }
    }

    else if ('VEHICLE' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Auto",
                template: "app/src/module/property/template/sub-template/vehicle/vehicle.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Auto ",
                template: "app/src/module/property/template/sub-template/vehicle/vehicle.list.html",
                type: value
            };
        } else if (type === 3) {
            return {
                titulo: "Dados do Auto",
                template: "app/views/policy/autoForm.html",
                type: value
            };
        }
    }

    else if ('LIFE' === value) {
        if (type === 1) {
            return {
                titulo: "Cadastro de Vida",
                template: "app/src/module/property/template/sub-template/life/life.form.html",
                type: value
            };

        } else if (type === 2) {
            return {
                titulo: "Consulta de Vida",
                template: "app/src/module/property/template/sub-template/life/life.list.html",
                type: value
            };
        }
    }
}