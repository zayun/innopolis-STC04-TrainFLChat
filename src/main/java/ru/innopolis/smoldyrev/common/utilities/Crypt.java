package ru.innopolis.smoldyrev.common.utilities;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс для кастомного шифрования паролей
 * объявлен @Deprecated  в связи с переходомна SpringSecurity
 */
@Deprecated
public class Crypt {

    private static Logger logger = Logger.getLogger(Crypt.class);

    /**
     * @param st строка шифруемого пароля*/
    private static String md5(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }
        return md5Hex;
    }

    /**Возвращает зашифрованный пароль
     * @param password - пароль
     * @param salt - соль для шифрования пароля*/
    public static String getCriptedPassword(String password, String salt) {
        return Crypt.md5(salt+password);
    }
}
