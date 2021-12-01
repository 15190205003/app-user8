package com.hiwoo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiwoo.license.LicenseCheckModel;
import com.hiwoo.model.ReponseMessage;
import com.hiwoo.service.LicenseService;
import com.hiwoo.utils.HttpUtil;
import com.hiwoo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/License")
public class LicenseController
{

    @Autowired
    private LicenseService licenseService;

    @PostMapping("/authCheck")
    public ReponseMessage authCheck(String authCode){

        ReponseMessage reponseMessage = new ReponseMessage(ReponseMessage.S_Success,"授权通过");

        String authCodeDecrypth = licenseService.decrypthAuthCode(authCode);
        if(StringUtils.isNotEmpty(authCodeDecrypth)){
            JSONObject authCodeJson = JSONObject.parseObject(authCodeDecrypth);

            String osName = authCodeJson.getString("oSName");
            String macAddress = authCodeJson.getString("macAddress");
            String mainBoardSerial = authCodeJson.getString("mainBoardSerial");
            String cpuSerial = authCodeJson.getString("cpuSerial");
            String issuedDate = authCodeJson.getString("issuedDate");
            String expiryDate = authCodeJson.getString("expiryDate");

            if(StringUtils.isEmpty(osName) || StringUtils.isEmpty(macAddress) || StringUtils.isEmpty(mainBoardSerial)
                    || StringUtils.isEmpty(cpuSerial) || StringUtils.isEmpty(issuedDate) || StringUtils.isEmpty(expiryDate)){
                return ReponseMessage.Create(ReponseMessage.S_Fail,"无效授权码");
            }

            LicenseCheckModel licenseCheckModel = licenseService.getServerLicInfo();

            if(osName.equals(licenseCheckModel.getOSName()) && macAddress.equals(licenseCheckModel.getMacAddress())
                    && mainBoardSerial.equals(licenseCheckModel.getMainBoardSerial()) && cpuSerial.equals(licenseCheckModel.getCpuSerial())){

                try {
                    JSONObject paramObj = new JSONObject();
                    paramObj.put("authCode", authCode);
                    String body = JSON.toJSONString(paramObj);
                    String responseBody = HttpUtil.sendHttpPostForMW("/Middleware/createLicense", body, null,null,null);
                    if (null != responseBody) {
                        JSONObject result = JSONObject.parseObject(responseBody);
                        if (null != result) {
                            String code = result.getString("code");
                            if ("0000".equals(code)) {
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                licenseService.createLicenseFile(authCode);
                licenseService.clearLicenseMap();
            }else{
                return  reponseMessage.Create(ReponseMessage.S_Fail,"无效授权码");
            }
        }else{
            return reponseMessage.Create(ReponseMessage.S_Fail,"无效授权码");
        }

        return  reponseMessage;
    }

}
