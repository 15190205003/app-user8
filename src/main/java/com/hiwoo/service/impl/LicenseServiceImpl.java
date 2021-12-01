package com.hiwoo.service.impl;


import com.alibaba.fastjson.JSON;
import com.hiwoo.license.LicenseCheckModel;
import com.hiwoo.license.LicenseUtil;
import com.hiwoo.service.LicenseService;
import com.hiwoo.utils.AuthDesUtils;
import com.hiwoo.utils.DESUtils;
import com.hiwoo.utils.FileUtil;
import com.hiwoo.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class LicenseServiceImpl implements LicenseService {

    private String FILEPATH = "cert.lic";
    private static String LICNAME = "SYSLICNAME";
    Map<String, String> licContentCacheMap = new HashMap<String, String>();
    Map<String, LicenseCheckModel> licCheckModelMap = new HashMap<String, LicenseCheckModel>();

    @Override
    public LicenseCheckModel getServerLicInfo() {
        if (licCheckModelMap.containsKey(LICNAME)) {
            return licCheckModelMap.get(LICNAME);
        }
        LicenseCheckModel licenseCheckModel = LicenseUtil.getServerInfos();
        if (StringUtils.isNotEmpty(licenseCheckModel.getMacAddress())) {
            licCheckModelMap.get(LICNAME);
        }
        return licenseCheckModel;
    }

    @Override
    public String getLicenseFileInfo() {
        if (licCheckModelMap.containsKey(LICNAME)) {
            return licContentCacheMap.get(LICNAME);
        }
        String authCodeJson = "";
//        String source = this.getClass().getResource("/").getPath();
//        String authDeCode = FileUtil.byte2StringFun(source + FILEPATH);
        try {
            InputStream inputStream = LicenseServiceImpl.class.getClassLoader().getResourceAsStream("cert.lic");
            String authDeCode =  IOUtils.toString(inputStream, "UTF-8");
            authCodeJson = AuthDesUtils.decrypt(authDeCode);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (StringUtils.isNotEmpty(authCodeJson)) {
            licContentCacheMap.put(LICNAME, authCodeJson);
        }
        return authCodeJson;
    }

    @Override
    public String decrypthAuthCode(String authCode) {
        try {
            String authCodeJson = AuthDesUtils.decrypt(authCode);
            return authCodeJson;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String getApplyCode() {
        String encrypt = "";
        try {
            LicenseCheckModel licenseCheckModel = LicenseUtil.getServerInfos();
            encrypt = DESUtils.encrypt(JSON.toJSONString(licenseCheckModel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return encrypt;
    }

    @Override
    public void clearLicenseMap() {
        licContentCacheMap.clear();
        licCheckModelMap.clear();
    }

    @Override
    public void createLicenseFile(String authorizationCode) {
        String source = this.getClass().getResource("/").getPath();
        FileUtil.writeFile(source + FILEPATH, authorizationCode);
    }
}
