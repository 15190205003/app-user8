package com.hiwoo.license;


import com.hiwoo.utils.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class LinuxServerInfos extends AbstractServerInfos{
    @Override
    protected String getMacAddress() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        byte [] mac = null;
        while (networkInterfaces.hasMoreElements()){
            NetworkInterface  networkInterface = (NetworkInterface)networkInterfaces.nextElement();
            if(networkInterface.isLoopback() || networkInterface.isPointToPoint() || networkInterface.isVirtual() || !networkInterface.isUp()){
                continue;
            }else{
                mac = networkInterface.getHardwareAddress();
                if(null != mac){
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mac.length; i++){
                        stringBuilder.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    if(stringBuilder.length() > 0){
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
        String [] xshell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(xshell);
        process.getOutputStream().close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = bufferedReader.readLine().trim();
        if(StringUtils.isNotBlank(line)){
            serialNum = line;
        }
        return serialNum;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        String serialNum = "";
        String[] xshell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        Process process = Runtime.getRuntime().exec(xshell);
        process.getOutputStream().close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line  = bufferedReader.readLine().trim();
        if(StringUtils.isNotBlank(line)){
            serialNum = line;
        }
        return serialNum;
    }
}
