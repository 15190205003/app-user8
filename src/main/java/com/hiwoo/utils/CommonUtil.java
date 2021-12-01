package com.hiwoo.utils;


import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

public class CommonUtil {

    /**
     * 去除list重复数据
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }


    public static double getdecimalDigit(int num){
        double resNum = 0;
        switch (num){
            case 0:
                resNum = 1;
                break;
            case 1:
                resNum = 0.1;
                break;
            case 2:
                resNum = 0.01;
                break;
            case 3:
                resNum = 0.001;
                break;
            case 4:
                resNum = 0.0001;
                break;
        }
        return resNum;
    }

    public static String formatDouble(Double d) {
        if(null == d){
            d = 0d;
        }
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    public static Integer transDataType(Integer dataType){

        int res = dataType;
        if(4 == dataType){
            res = 1;
        }else if(5 == dataType){
            res = 3;
        }
        return  res;
    }

    public String nullValueTransSpace(String str){
        if(StringUtils.isNotEmpty(str)){
            return str;
        }
        return "";
    }

    /**
     * 根据deviceID截取deviceIndex
     * @param deviceId
     * @return
     */
    public static Integer getDeviceIndex(String deviceId){
        int deviceIndex = 0;
        if(StringUtils.isNotEmpty(deviceId)){
            deviceIndex = Integer.parseInt(deviceId.substring(deviceId.indexOf("_")+1));
        }
        return deviceIndex;
    }


    public static boolean isNumeric(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }


    //通过UUID生成16位唯一订单号
    public static String getOrderIdByUUId() {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }

    public static String getGroupIdByUUId() {
        int first = new Random(8).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%010d", hashCodeV);
    }



    public static boolean isJson(String content) {
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 根据小数位数转换double类型数据
     * @param d
     * @param decimalDigit
     * @return
     */
    public static Double formatDouble(Double d, Integer decimalDigit) {
        if(null == d){
            d = 0d;
        }
        BigDecimal bg = new BigDecimal(d).setScale(decimalDigit, RoundingMode.HALF_UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return Double.valueOf((long) num);
        }
        return Double.valueOf(num);
    }

    // 获取mac地址
    public static String getMacAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            byte[] mac = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    mac = netInterface.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        if (sb.length() > 0) {
                            return sb.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取ip地址
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
