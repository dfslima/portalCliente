package br.com.portalCliente.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaginationUtils {

    public static int calculateFirstResultQuery(int firstResult, int maxResults) {
        int value = firstResult - 1;
        if (value <= 0) {
            return 0;
        }
        value = value * maxResults;
        return value;
    }

    public static float getPercent(float value) {
        float result = value * 100;
        BigDecimal bd = new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN);
        float porcent = bd.floatValue();
        BigDecimal bdec = new BigDecimal(porcent).setScale(1, RoundingMode.HALF_EVEN);
        float valueFinal = bdec.floatValue();

        return valueFinal;
    }

}