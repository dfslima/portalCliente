package br.com.portalCliente.security;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

public class Crypt {

    public static String generatorPassword() {
        int passwordLenght = 8;
        char[] chart = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        char[] password = new char[passwordLenght];

        int chartLenght = chart.length;
        Random rdm = new Random();

        for (int i = 0; i < passwordLenght; i++) {
            password[i] = chart[rdm.nextInt(chartLenght)];
        }
        return new String(password);
    }

    public static String decrypt(String encrypted) {
        byte[] clearbyte = null;
        try {
            byte byte16[] = new byte[16];
            SecretKeySpec secretKeySpec = new SecretKeySpec(byte16, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(byte16));
            clearbyte = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));
        } catch (Exception e) {
            Logger.getLogger(Crypt.class.getName())
                    .warn("DECRYPT - ERROR: " + e.getMessage());
        }

        return new String(clearbyte);
    }

    public static String encrypt(String content) {
        byte[] cipherText = null;
        try {
            byte byte16[] = new byte[16];
            byte[] input = content.getBytes("utf-8");
            SecretKeySpec secretKeySpec = new SecretKeySpec(byte16, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(byte16));
            cipherText = new byte[cipher.getOutputSize(input.length)];
            int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
        } catch (Exception e) {
            Logger.getLogger(Crypt.class.getName())
                    .warn("ENCRYPT - ERROR: " + e.getMessage());
        }
        return DatatypeConverter.printHexBinary(cipherText);
    }

}
