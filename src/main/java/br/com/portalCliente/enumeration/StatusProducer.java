package br.com.portalCliente.enumeration;

public enum StatusProducer {
	
	PENDING(0),
	REGISTERED(1);
	
	private final int value;

	private StatusProducer(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static StatusProducer getName(int value){		
		StatusProducer result = null;
		for(StatusProducer sp : StatusProducer.values()){			
			if(sp.value == value){				
				result = sp;
				break;
			}
		}
		return result;		
	}	
}