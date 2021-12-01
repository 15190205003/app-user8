package com.hiwoo.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public class EncryptUtil {
    /**
     * Base64编码
     * @param str
     * @return
     */
    public static String encoderBase64(String str){
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(str.getBytes());
        return encode;
    }

    /**
     * Base64 解码
     * @param str
     * @return
     */
    public static String decoderBase64(String str){
        BASE64Decoder decoder = new BASE64Decoder();
        String resString = "";
        try {
            byte[] decode = decoder.decodeBuffer(str);
            resString = new String(decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resString;
    }
}
