package br.com.portalCliente.enumeration;

public enum VehicleType {

	CAR(1, "carros"),
	MOTORBIKE(2, "motos"),
	TRUCK(3, "caminhoes");
	
	private final int value;
	private final String valueName;

	private VehicleType(int value, String valueName) {
		this.value = value;
		this.valueName = valueName;
	}

	public int getValue() {
		return value;
	}

	public String getValueName() {
		return valueName;
	}
	
	public static VehicleType get(int value){		
		VehicleType result = null;
		for(VehicleType c : VehicleType.values()){			
			if(c.value == value){				
				result = c;
				break;
			}
		}
		return result;		
	}
}
