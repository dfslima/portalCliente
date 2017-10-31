package br.com.portalCliente.enumeration;

public enum MaritalStatus {
	
	SINGLE(0),
	MARRIED(1),
	DIVORCED(2),
	SEPARATED(4),
	WIDOWED(5);
	
	private final int value;

	private MaritalStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static MaritalStatus getName(int value){		
		MaritalStatus result = null;
		for(MaritalStatus mt : MaritalStatus.values()){			
			if(mt.value == value){				
				result = mt;
				break;
			}
		}
		return result;		
	}	
}
