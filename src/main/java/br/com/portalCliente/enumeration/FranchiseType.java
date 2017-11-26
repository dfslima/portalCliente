package br.com.portalCliente.enumeration;

public enum FranchiseType {

    HULL(1),
    GLASSES(2),
    HEADLIGHTS(3);

    private final int value;

    private FranchiseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FranchiseType getName(Integer value){
        if(value == null || value == 0){
            return null;
        }

        FranchiseType result = null;
        for(FranchiseType c : FranchiseType.values()){
            if(c.value == value){
                result = c;
                break;
            }
        }
        return result;
    }
}
