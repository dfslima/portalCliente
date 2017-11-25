package br.com.portalCliente.enumeration;

public enum VesselType {

    ALUMINUM_BOAT(1),
    INFLATABLE_NIGHTCLUB(2),
    KAYAKS(3),
    BOAT(4),
    SCHOONER(5),
    JET_BOAT(6),
    JET_SKI(7),
    KITE_SURF(8),
    MOTOR_BOAT(9),
    STAND_UP(10),
    FISHING_BOAT(11),
    TRAWLER(12),
    SAILBOAT(13),
    WIND_SURF(14);

    private final int value;

    VesselType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static VesselType getName(Integer value) {
        if (value == null || value == 0) {
            return null;
        }

        VesselType result = null;
        for (VesselType c : VesselType.values()) {
            if (c.value == value) {
                result = c;
                break;
            }
        }
        return result;
    }

}
