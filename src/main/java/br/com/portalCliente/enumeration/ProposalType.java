package br.com.portalCliente.enumeration;

public enum ProposalType {

    PROPOSAL(1),
    POLICY(2);

    private int value;

    private ProposalType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ProposalType fromValue(int value) {
        ProposalType result = null;
        for (ProposalType p : ProposalType.values()) {
            if (p.value == value) {
                result = p;
                break;
            }
        }
        return result;
    }

}
