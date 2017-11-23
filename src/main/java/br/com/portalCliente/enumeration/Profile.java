package br.com.portalCliente.enumeration;


public enum Profile {
	
	ROLE_ADMIN(1, "Administrador"),
	ROLE_USER(2, "Usuário");

	private final int value;
	private final String name;

	private Profile(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public static Profile getName(Integer value){	
		if(value == null) return null;
		
		Profile result = null;
		for(Profile c : Profile.values()){			
			if(c.value == value){				
				result = c;
				break;
			}
		}
		return result;		
	}
}
