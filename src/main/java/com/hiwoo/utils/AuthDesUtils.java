package com.hiwoo.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class AuthDesUtils {

    /**
     * 解密外部方法
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) throws Exception {
        return java.net.URLDecoder.decode(decrypt(message, "HIWOOSEC"), "utf-8");
    }
    /**
     * 解密
     * @param message 加密后的内容
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, String key) throws Exception {

        byte[] bytesrc = convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.toLowerCase().getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.toLowerCase().getBytes("UTF-8"));

        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }







/***以下代码在加密工具中实现**/


    public static String encrypt(String message) throws Exception{

        return toHexString(encrypt(message, "HIWOOSEC")).toUpperCase();
    }

    /**
     * 加密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.toLowerCase().getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.toLowerCase().getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }

        return hexString.toString();
    }


    public static void main(String[] args){

        String info = "{\"cpuSerial\":\"BFEBFBFF000906EA\",\"issuedDate\":\"2019-10-28 15:32:47\",\"macAddress\":\"FC-01-7C-59-73-51\",\"mainBoardSerial\":\"/7SNFTP2/CNWS20084G01CX/\",\"oSName\":\"Windows 10\",\"expiryDate\":\"2020-10-28 15:32:47\",\"maxPoint\":500}";

        try {
            String enCode = AuthDesUtils.encrypt(info);
            System.out.println("加密"+ enCode);

            System.out.println("解密"+ AuthDesUtils.decrypt(enCode));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
