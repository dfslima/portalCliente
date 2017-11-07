app.factory('routeProperty', function () {
    return {
        form: function (value) {
            return formsProperties(value, 1);
        },

        list: function (value) {
            return formsProperties(value, 2);
        }
    };
})
    .factory('propertyFactory', function () {
        return {
            description: function (value, type, policyObj) {

                if (value != undefined) {
                    if (type === 'RESIDENCE' || type === 'COMPANY') {
                        if (value.address != undefined) {
                            return value.address.street + ', ' + value.address.neighborhood + ', ' + value.address.city + ', ' + value.address.state;
                        }
                    }
                    else if (type === 'VESSEL') {
                        if (value.vessel != undefined) {
                            return value.vessel.boatName;
                        }
                    }
                    else if (type === 'LIFE') {
                        if (value.life != undefined) {
                            if (value.life.type == 1) {
                                return value.life.lifeProfessionalActivityName;
                            } else {
                                if (value.lifeIndividuals != undefined) {
                                    if (value.lifeIndividuals.length > 0) {
                                        var result = '';

                                        for (var int = 0; int < value.lifeIndividuals.length; int++) {
                                            if (int == 2)
                                                break;
                                            result += value.lifeIndividuals[int].nameBeneficiary;
                                            if (int == 0)
                                                result += ', ';
                                            else
                                                result += '...';
                                        }
                                        return result;
                                    }
                                } else {
                                    return 'Vida - Individual';
                                }
                            }
                        }
                    }
                    else if (type === 'VEHICLE') {
                        if (value.vehicle != undefined) {

                            if (policyObj != undefined) {
                                if (policyObj.fleet) {

                                    var plate = 'Frota';

                                    if (policyObj.fleets != undefined) {

                                        for (var i = 0; i < policyObj.fleets.length; i++) {

                                            if (i == 3) {
                                                plate = plate.concat('...');
                                                break;
                                            }
                                            plate = plate.concat(' ( ' + policyObj.fleets[i].property.vehicle.vehicleLicensePlate + ' )');
                                        }

                                    }

                                    return plate;
                                }
                            }

                            if (value.vehicle.vehicleLicensePlate == undefined) {
                                return '(Sem placa)';
                            }

                            return value.vehicle.vehicleLicensePlate;
                        }
                    }
                    else if (type === 'EQUIPMENT') {
                        if (value.equipment != undefined) {
                            return value.equipment.equipmentMake + ', ' + value.equipment.equipmentModel;
                        }
                    }
                }
            },

            propertyName: function (value) {
                if (value != undefined) {
                    if (value === 'RESIDENCE') {
                        return 'Residência';
                    } else if (value === 'COMPANY') {
                        return 'Empresa';
                    } else if (value === 'VESSEL') {
                        return 'Embarcação';
                    } else if (value === 'LIFE') {
                        return 'Vida';
                    } else if (value === 'VEHICLE') {
                        return 'Auto ';
                    } else if (value === 'EQUIPMENT') {
                        return 'Equipamento';
                    }
                }
            },

            vesselTypeName: function (value) {
                if (value != undefined) {
                    if (value === 'ALUMINUM_BOAT') {
                        return 'Barco de aluminio';
                    } else if (value === 'INFLATABLE_NIGHTCLUB') {
                        return 'Boate inflável';
                    } else if (value === 'KAYAKS') {
                        return 'Caiaques';
                    } else if (value === 'BOAT') {
                        return 'Canoas';
                    } else if (value === 'SCHOONER') {
                        return 'Escuna';
                    } else if (value === 'JET_BOAT') {
                        return 'Jet Boat';
                    } else if (value === 'JET_SKI') {
                        return 'Jet-Ski';
                    } else if (value === 'KITE_SURF') {
                        return 'Kite surf';
                    } else if (value === 'MOTOR_BOAT') {
                        return 'Lancha';
                    } else if (value === 'STAND_UP') {
                        return 'Stand-up paddle';
                    } else if (value === 'FISHING_BOAT') {
                        return 'Traineira/Pesqueiro';
                    } else if (value === 'TRAWLER') {
                        return 'Trawler';
                    } else if (value === 'SAILBOAT') {
                        return 'Veleiro';
                    } else if (value === 'WIND_SURF') {
                        return 'Wind surf';
                    }
                }
            },

            details: function (type) {
                if (type != undefined) {
                    if (type === 'COMPANY') {
                        return 'app/views/property/details/companyDetails.html';
                    }
                    else if (type === 'VESSEL') {
                        return 'app/views/property/details/vesselDetails.html';
                    }
                    else if (type === 'LIFE') {
                        return 'app/views/property/details/lifeDetails.html';
                    }
                    else if (type === 'VEHICLE') {
                        return 'app/views/property/details/vehicleDetails.html';
                    }
                    else if (type === 'RESIDENCE') {
                        return 'app/views/property/details/residenceDetails.html';
                    }
                    else if (type === 'EQUIPMENT') {
                        return 'app/views/property/details/equipmentDetails.html';
                    }
                }
            },

            propertyIdentifier: function (policyObj) {

                if (policyObj != undefined) {

                    var value = policyObj.property;
                    if (value.propertyType === 'RESIDENCE') {
                        return 'Residência ' + '(' + this.homeTypeDescription(value.residence.homeType) + ')';
                    }
                    else if (value.propertyType === 'COMPANY') {
                        return 'Empresa ' + '(' + value.company.businessZone + ')';
                    }
                    else if (value.propertyType === 'VESSEL') {
                        return 'Embarcação ' + '(' + value.vessel.boatName + ')';
                    }
                    else if (value.propertyType === 'LIFE') {
                        if (value.life.type == 2)
                            return 'Vida - Individual';
                        else
                            return 'Vida ' + '(' + value.life.lifeProfessionalActivityName + ')';
                    }
                    else if (value.propertyType === 'VEHICLE') {
                        var plate = 'Frota';

                        if (!policyObj.fleet) {
                            if (value.vehicle.vehicleLicensePlate != undefined) {
                                plate = 'Auto ' + '(' + value.vehicle.vehicleLicensePlate + ')';
                            }
                            else {
                                plate = 'Auto (Sem placa)';
                            }
                        }
                        else {

                            if (policyObj.fleets != undefined) {

                                for (var i = 0; i < policyObj.fleets.length; i++) {

                                    if (i == 3) {
                                        plate = plate.concat('...');
                                        break;
                                    }

                                    plate = plate.concat(' ( ' + policyObj.fleets[i].property.vehicle.vehicleLicensePlate + ' )');
                                }
                            }
                        }

                        return plate;

                    }
                    else if (value.propertyType === 'EQUIPMENT') {
                        return 'Equipamento ' + '(' + value.equipment.equipmentDetails + ')';
                    }
                }
            },

            typeVehicle: function (value) {
                if (value != undefined) {
                    if (value === 'CAR') {
                        return 'Carro';
                    } else if (value === 'MOTORBIKE') {
                        return 'Moto';
                    } else if (value === 'TRUCK') {
                        return 'Caminhão';
                    }
                }
            },

            typeVehicleInversive: function (value) {
                if (value != undefined) {
                    if (value === 'Carro') {
                        return 'CAR';
                    } else if (value === 'Moto') {
                        return 'MOTORBIKE';
                    } else if (value === 'Caminhão') {
                        return 'TRUCK';
                    }
                }
            },

            homeTypeDescription: function (value) {
                if (value != undefined) {
                    if (value === 'APARTAMENTO_HABITUAL') {
                        return 'Apt. habitual';
                    } else if (value === 'CASA_HABITUAL') {
                        return 'Casa habitual';
                    } else if (value === 'EDIFICIO_HABITUAL') {
                        return 'Edifício habitual';
                    }
                }
            },

            homeBuildingTypeDescription: function (value) {
                if (value != undefined) {
                    if (value === 'ALVENARIA') {
                        return 'Alvenaria';
                    } else if (value === 'CASA_EM_CONDOMINIO') {
                        return 'Casa em condomínio';
                    } else if (value === 'CASA_HABITUAL') {
                        return 'Casa habitual';
                    } else if (value === 'EDIFICIO_HABITUAL') {
                        return 'Edificio habitual';
                    }
                }
            },

            incidentProperty: function (value) {
                if (value != undefined) {
                    if (value === 'TOTAL_LOSS') {
                        return 'Perda total';
                    } else if (value === 'PARTIAL_LOSS') {
                        return 'Perda parcial';
                    } else if (value === 'THEFT') {
                        return 'Roubo';
                    }
                }
            },

            homeUseTypeDescription: function (value) {
                if (value != undefined) {
                    if (value === 'COMERCIO') {
                        return 'Comércio';
                    } else if (value === 'CONDOMINIO') {
                        return 'Condomínio';
                    } else if (value === 'RESIDENCIA') {
                        return 'Residência';
                    }
                }
            },

            enumProperty: function (value) {
                if (value != undefined) {
                    if (value === 'CONDOMINIUM') {
                        return 5;
                    } else if (value === 'RESIDENCE') {
                        return 4;
                    } else if (value === 'COMPANY') {
                        return 1;
                    } else if (value === 'VESSEL') {
                        return 7;
                    } else if (value === 'LIFE') {
                        return 3;
                    } else if (value === 'VEHICLE') {
                        return 6;
                    } else if (value === 'EQUIPMENT') {
                        return 2;
                    }
                }
            }
        };
    });