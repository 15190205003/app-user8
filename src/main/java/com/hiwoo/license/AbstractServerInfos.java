package com.hiwoo.license;

import com.hiwoo.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 用于获取客户服务器的基本信息，如：IP、Mac地址、CPU序列号、主板序列号等
 */
public abstract class AbstractServerInfos {
    private static Logger logger = LoggerFactory.getLogger(AbstractServerInfos.class);

    /**
     * 组装需要额外校验的License参数
     * @return
     */
    public LicenseCheckModel getServerInfos(){
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setOSName(this.getOSName());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
            result.setIssuedDate(DateUtil.getStringDate());
        }catch (Exception e){
            logger.error("获取服务器硬件信息失败",e);
        }

        return result;
    }

    /**
     * 获取Mac地址
     * @return
     * @throws Exception
     */
    protected abstract String getMacAddress() throws Exception;

    /**
     * 获取CPU序列号
     * @return
     * @throws Exception
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * 获取主板序列号
     * @return
     * @throws Exception
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * 获取当前服务器所有符合条件的InetAddress
     * @return
     * @throws Exception
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = (InetAddress) inetAddresses.nextElement();

                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if(!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }

        return result;
    }

    /**
     * 获取某个网络接口的Mac地址
     * @param inetAddr
     * @return
     */
    protected String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();

            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }

            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @return
     */
    protected String getOSName(){
        String os = System.getProperty("os.name");
        return  os;
    }
}