package com.hiwoo.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;

public class FileUtil {

    //base64字符串转byte[]
    public static byte[] base64String2ByteFun(String base64Str){
        return Base64.decodeBase64(base64Str);
    }
    //byte[]转base64
    public static String byte2Base64StringFun(byte[] b){
        return Base64.encodeBase64String(b);
    }

    public static String byte2StringFun(String filePath){
        byte[] b = fileToByte(filePath);
        return new String(b);
    }

    public static byte[] fileToByte(String filePath){
        byte[] bytes =  null;
        FileInputStream fis = null;
        try{
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bytes = new byte[(int) file.length()];
            fis.read(bytes);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static void writeFile(String filePath, String fileContent){
        try {
            File file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(fileContent);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args){

        String test = byte2StringFun("D:\\SVN_Code\\Platform\\middleware\\server\\IOTMiddleware\\src\\main\\resources\\cert.lic");
        System.out.println("org:"+test);
        try {
            String decode = AuthDesUtils.decrypt(test);
            System.out.println("org:"+decode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
