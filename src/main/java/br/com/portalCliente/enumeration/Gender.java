package br.com.portalCliente.enumeration;

public enum Gender {
	
	MALE(0),
	FEMALE(1);
	
	private final int value;

	private Gender(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Gender getName(int value){		
		Gender result = null;
		for(Gender g : Gender.values()){			
			if(g.value == value){				
				result = g;
				break;
			}
		}
		return result;		
	}	
}
