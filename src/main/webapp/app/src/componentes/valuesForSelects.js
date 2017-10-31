function states() {
    return [{value: 'AC', name: 'ACRE'},
        {value: 'AL', name: 'ALAGOAS'},
        {value: 'AP', name: 'AMAPÁ'},
        {value: 'AM', name: 'AMAZONAS'},
        {value: 'BA', name: 'BAHIA'},
        {value: 'CE', name: 'CEARÁ'},
        {value: 'DF', name: 'DISTRITO FEDERAL'},
        {value: 'ES', name: 'ESPÍRITO SANTO'},
        {value: 'GO', name: 'GOIÁS'},
        {value: 'MA', name: 'MARANHÃO'},
        {value: 'MT', name: 'MATO GROSSO'},
        {value: 'MS', name: 'MATO GROSSO DO SUL'},
        {value: 'MG', name: 'MINAS GERAIS'},
        {value: 'PA', name: 'PARÁ'},
        {value: 'PB', name: 'PARAIBA'},
        {value: 'PR', name: 'PARANÁ'},
        {value: 'PE', name: 'PERNAMBUCO'},
        {value: 'PI', name: 'PIAUÍ'},
        {value: 'RJ', name: 'RIO DE JANEIRO'},
        {value: 'RN', name: 'RIO GRANDE DO NORTE'},
        {value: 'RS', name: 'RIO GRANDE DO SUL'},
        {value: 'RO', name: 'RONDÔNIA'},
        {value: 'RR', name: 'RORÂIMA'},
        {value: 'SC', name: 'SANTA CATARINA'},
        {value: 'SP', name: 'SÃO PAULO'},
        {value: 'SE', name: 'SERGIPE'},
        {value: 'TO', name: 'TOCANTINS'}];
};

function propertiesForSelected() {
    return [
        {value: 'COMPANY', name: 'Empresa'},
        {value: 'EQUIPMENT', name: 'Equipamento'},
        {value: 'LIFE', name: 'Vida'},
        {value: 'RESIDENCE', name: 'Residência'},
        {value: 'VEHICLE', name: 'Veículo'},
        {value: 'VESSEL', name: 'Embarcação'},
    ];
};

function businessZone() {
    return [
        {value: 'CENTRO', name: 'Centro'},
        {value: 'LESTE', name: 'Leste'},
        {value: 'NORTE', name: 'Norte'},
        {value: 'OESTE', name: 'Oeste'},
        {value: 'SUL', name: 'Sul'}
    ];
};

function condominiumSubType() {
    return [
        {value: 'CONDOMINIO_SIMPLES', name: 'Condomínio simples'},
        {value: 'EXCLUSIVAMENTE_RESIDENCIAL', name: 'Exclusivamente residêncial'}
    ];
};

function condominiumType() {
    return [
        {value: 'CONDOMINIO_COMERCIAL', name: 'Condomínio comércial'},
        {value: 'CONDOMINIO_RESIDENCIAL', name: 'Condomínio residêncial'}
    ];
};

function homeBuildingType() {
    return [
        {value: 'ALVENARIA', name: 'Alvenaria'},
        {value: 'CASA_EM_CONDOMINIO', name: 'Casa em condomínio'},
        {value: 'CASA_HABITUAL', name: 'Casa habitual'},
        {value: 'EDIFICIO_HABITUAL', name: 'Edificio habitual'}
    ];
};

function homeType() {
    return [
        {value: 'APARTAMENTO_HABITUAL', name: 'Apartamento habitual'},
        {value: 'CASA_HABITUAL', name: 'Casa habitual'},
        {value: 'EDIFICIO_HABITUAL', name: 'Edificio habitual'}
    ];
};

function homeUseType() {
    return [
        {value: 'COMERCIO', name: 'Comércio'},
        {value: 'CONDOMINIO', name: 'Condomínio'},
        {value: 'RESIDENCIA', name: 'Residência'}
    ];
};

function vehicleType() {
    return [
        {value: 'CAR', name: 'Carro'},
        {value: 'MOTORBIKE', name: 'Moto'},
        {value: 'TRUCK', name: 'Caminhão'}
    ];
};

function vesselTypes() {
    return [
        {value:'ALUMINUM_BOAT', name:'Barco de aluminio'},
        {value:'INFLATABLE_NIGHTCLUB', name:'Boate inflável'},
        {value:'KAYAKS', name:'Caiaques'},
        {value:'BOAT', name:'Canoas'},
        {value:'SCHOONER', name:'Escuna'},
        {value:'JET_BOAT', name:'Jet Boat'},
        {value:'JET_SKI', name:'Jet-Ski'},
        {value:'KITE_SURF', name:'Kite surf'},
        {value:'MOTOR_BOAT', name:'Lancha'},
        {value:'STAND_UP', name:'Stand-up paddle'},
        {value:'FISHING_BOAT', name:'Traineira/Pesqueiro'},
        {value:'TRAWLER', name:'Trawler'},
        {value:'SAILBOAT', name:'Veleiro'},
        {value:'WIND_SURF', name:'Wind surf'}
    ];
};

function maritalStatusForSelected() {
    return [
        {value: 'SOLTEIRO', name: 'SOLTEIRO'},
        {value: 'CASADO', name: 'CASADO'},
        {value: 'DIVORCIADO', name: 'DIVORCIADO'},
        {value: 'VIÚVO', name: 'VIÚVO'}
    ];
};

function genreForSelected() {
    return [
        {value: 'MASCULINO', name: 'MASCULINO'},
        {value: 'FEMININO', name: 'FEMININO'}
    ];
};

function statusProducer() {
    return [
        {value: 'REGISTERED', name: 'Ativo'},
        {value: 'PENDING', name: 'Cadastro Pendente'}
    ];
};

function profileUser() {
    return [
        {value: 'ROLE_SEGOO', name: 'Segoo User'},
        {value: 'ROLE_ADMIN', name: 'Administrador'},
        {value: 'ROLE_USER', name: 'Usuário'},
        {value: 'ROLE_INCIDENT', name: 'Sinistro'}
    ];
};

function userStatus() {
    return [
        {value: 'ACTIVE', name: 'Ativo'},
        {value: 'INACTIVE', name: 'Inativo'}
    ];
};

function days() {
    return [
        {value: '01', name: '01'},
        {value: '02', name: '02'},
        {value: '03', name: '03'},
        {value: '04', name: '04'},
        {value: '05', name: '05'},
        {value: '06', name: '06'},
        {value: '07', name: '07'},
        {value: '08', name: '08'},
        {value: '09', name: '09'},
        {value: '10', name: '10'},
        {value: '11', name: '11'},
        {value: '12', name: '12'},
        {value: '13', name: '13'},
        {value: '14', name: '14'},
        {value: '15', name: '15'},
        {value: '16', name: '16'},
        {value: '17', name: '17'},
        {value: '18', name: '18'},
        {value: '19', name: '19'},
        {value: '20', name: '20'},
        {value: '21', name: '21'},
        {value: '22', name: '22'},
        {value: '23', name: '23'},
        {value: '24', name: '24'},
        {value: '25', name: '25'},
        {value: '26', name: '26'},
        {value: '27', name: '27'},
        {value: '28', name: '28'},
        {value: '29', name: '29'},
        {value: '30', name: '30'},
        {value: '31', name: '31'}
    ];
};

function month() {
    return [
        {value: '01', name: '01'},
        {value: '02', name: '02'},
        {value: '03', name: '03'},
        {value: '04', name: '04'},
        {value: '05', name: '05'},
        {value: '06', name: '06'},
        {value: '07', name: '07'},
        {value: '08', name: '08'},
        {value: '09', name: '09'},
        {value: '10', name: '10'},
        {value: '11', name: '11'},
        {value: '12', name: '12'}
    ];
};

function year() {
    return [
        {value: '1920', name: '1920'},
        {value: '1921', name: '1921'},
        {value: '1922', name: '1922'},
        {value: '1923', name: '1923'},
        {value: '1924', name: '1924'},
        {value: '1925', name: '1925'},
        {value: '1926', name: '1926'},
        {value: '1927', name: '1927'},
        {value: '1928', name: '1928'},
        {value: '1929', name: '1929'},
        {value: '1930', name: '1930'},
        {value: '1931', name: '1931'},
        {value: '1932', name: '1932'},
        {value: '1933', name: '1933'},
        {value: '1934', name: '1934'},
        {value: '1935', name: '1935'},
        {value: '1936', name: '1936'},
        {value: '1937', name: '1937'},
        {value: '1938', name: '1938'},
        {value: '1939', name: '1939'},
        {value: '1940', name: '1940'},
        {value: '1941', name: '1941'},
        {value: '1942', name: '1942'},
        {value: '1943', name: '1943'},
        {value: '1944', name: '1944'},
        {value: '1945', name: '1945'},
        {value: '1946', name: '1946'},
        {value: '1947', name: '1947'},
        {value: '1948', name: '1948'},
        {value: '1949', name: '1949'},
        {value: '1950', name: '1950'},
        {value: '1951', name: '1951'},
        {value: '1952', name: '1952'},
        {value: '1953', name: '1953'},
        {value: '1954', name: '1954'},
        {value: '1955', name: '1955'},
        {value: '1956', name: '1956'},
        {value: '1957', name: '1957'},
        {value: '1958', name: '1958'},
        {value: '1959', name: '1959'},
        {value: '1960', name: '1960'},
        {value: '1961', name: '1961'},
        {value: '1962', name: '1962'},
        {value: '1963', name: '1963'},
        {value: '1964', name: '1964'},
        {value: '1965', name: '1965'},
        {value: '1966', name: '1966'},
        {value: '1967', name: '1967'},
        {value: '1968', name: '1968'},
        {value: '1969', name: '1969'},
        {value: '1970', name: '1970'},
        {value: '1971', name: '1971'},
        {value: '1972', name: '1972'},
        {value: '1973', name: '1973'},
        {value: '1974', name: '1974'},
        {value: '1975', name: '1975'},
        {value: '1976', name: '1976'},
        {value: '1977', name: '1977'},
        {value: '1978', name: '1978'},
        {value: '1979', name: '1979'},
        {value: '1980', name: '1980'},
        {value: '1981', name: '1981'},
        {value: '1982', name: '1982'},
        {value: '1983', name: '1983'},
        {value: '1984', name: '1984'},
        {value: '1985', name: '1985'},
        {value: '1986', name: '1986'},
        {value: '1987', name: '1987'},
        {value: '1988', name: '1988'},
        {value: '1989', name: '1989'},
        {value: '1990', name: '1990'},
        {value: '1991', name: '1991'},
        {value: '1992', name: '1992'},
        {value: '1993', name: '1993'},
        {value: '1994', name: '1994'},
        {value: '1995', name: '1995'},
        {value: '1996', name: '1996'},
        {value: '1997', name: '1997'},
        {value: '1998', name: '1998'},
        {value: '1999', name: '1999'},
        {value: '2000', name: '2000'},
        {value: '2001', name: '2001'},
        {value: '2002', name: '2002'},
        {value: '2003', name: '2003'},
        {value: '2004', name: '2004'},
        {value: '2005', name: '2005'},
        {value: '2006', name: '2006'},
        {value: '2007', name: '2007'},
        {value: '2008', name: '2008'},
        {value: '2009', name: '2009'},
        {value: '2010', name: '2010'},
        {value: '2011', name: '2011'},
        {value: '2012', name: '2012'},
        {value: '2013', name: '2013'},
        {value: '2014', name: '2014'},
        {value: '2015', name: '2015'},
        {value: '2016', name: '2016'},
        {value: '2017', name: '2017'},
        {value: '2018', name: '2018'}
    ];
}