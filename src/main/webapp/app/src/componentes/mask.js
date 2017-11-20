function maskCpfCnpj(v) {
	  
	 v = v.replace(/\D/g, "");
    if (v.length <= 11) { //CPF
        v = v.replace(/(\d{3})(\d)/, "$1.$2");	
        v = v.replace(/(\d{3})(\d)/, "$1.$2");
        v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    } else { //CNPJ
        v = v.replace(/^(\d{2})(\d)/, "$1.$2");
        v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
        v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
        v = v.replace(/(\d{4})(\d)/, "$1-$2");
    }
    return v;
};

function maskPhone(v){
	var value = '(';
	if(v.length == 10){
		value = value + v.substring(0, 2) + ') ';
		value = value + v.substring(2, 6) + '-';
		value = value + v.substring(6, 10);
		return value;
	}
	else if(v.length > 10){
		value = value + v.substring(0, 2) + ') ';
		value = value + v.substring(2, 7) + '-';
		value = value + v.substring(7, 11);
		return value;
	}
};