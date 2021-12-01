package com.hiwoo.license;


import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

public class WindowsServerInfos extends AbstractServerInfos {
    @Override
    protected String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        byte [] mac = null;
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface networkInterface =  (NetworkInterface)networkInterfaces.nextElement();
            if(networkInterface.isLoopback() || networkInterface.isPointToPoint() || networkInterface.isVirtual() || !networkInterface.isUp()){
                continue;
            }else{
                mac = networkInterface.getHardwareAddress();
                if(null != mac){
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0 ; i < mac.length; i++){
                        stringBuilder.append(String.format("%02X%s",mac[i],(i < mac.length -1) ? "-" : ""));
                    }
                    if (stringBuilder.length() > 0 ){
                        return stringBuilder.toString();
                    }
                }
            }
        }
        return "";
    }

    @Override
    protected String getCPUSerial() throws Exception {
        String serialNum = "";
        Process process =   Runtime.getRuntime().exec("wmic cpu get processorid");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if(null != scanner && scanner.hasNext()){
            scanner.next();
        }
        if(scanner.hasNext()){
            serialNum = scanner.next().trim();
        }
        return serialNum;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        String serialNum = "";
        Process process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());
        if(null != scanner && scanner.hasNext()){
            scanner.next();
        }
        if(scanner.hasNext()){
            serialNum = scanner.next().trim();
        }
        return serialNum;
    }
}
