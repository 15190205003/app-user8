package com.hiwoo.license;

import com.alibaba.fastjson.JSON;
import com.hiwoo.utils.DESUtils;

public class LicenseUtil {


    /**
     * 获取当前服务器需要额外校验的License参数
     * @return
     */
    public static LicenseCheckModel getServerInfos(){
        //操作系统类型
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }


    public static void main(String[] args){
        LicenseCheckModel model = getServerInfos();
        System.out.println("--: "+ JSON.toJSON(model));

        try {
            String encryptStr = DESUtils.encrypt(JSON.toJSONString(model));
            System.out.println("加密: "+ encryptStr);


            String decryptStr = DESUtils.decrypt(encryptStr);
            System.out.println("解密: "+ decryptStr);



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
