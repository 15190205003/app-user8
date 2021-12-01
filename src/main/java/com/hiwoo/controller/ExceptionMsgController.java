package com.hiwoo.controller;

import com.hiwoo.model.ReponseMessage;
import com.hiwoo.service.LicenseService;
import com.hiwoo.utils.ErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/MSG")
public class ExceptionMsgController {

    @Autowired
    private LicenseService licenseService;

    @RequestMapping("/excepMsg")
    public ReponseMessage excpMsg(int errorType) {
        ReponseMessage reponseMessage = ReponseMessage.Create(ReponseMessage.S_Success, ErrorCodeEnum.getMsg(errorType));

        if (errorType == ErrorCodeEnum.AUTHCODE_INVALID.getCode() || errorType == ErrorCodeEnum.AUTGCODE_EXPIRE.getCode()) {
            reponseMessage = ReponseMessage.Create(ReponseMessage.S_AuthCodeFail,ErrorCodeEnum.getMsg(errorType));

            String applyCode = licenseService.getApplyCode();
            Map<String,String> map = new HashMap<String, String>();
            map.put("applyCode",applyCode);
            reponseMessage.setAttachObject(map);
        }
        return reponseMessage;
    }
}
