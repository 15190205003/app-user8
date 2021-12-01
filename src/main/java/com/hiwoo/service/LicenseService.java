package com.hiwoo.service;

import com.hiwoo.license.LicenseCheckModel;

public interface LicenseService {

    LicenseCheckModel getServerLicInfo();

    String getLicenseFileInfo();

    String decrypthAuthCode(String authCode);

    String getApplyCode();

    void clearLicenseMap();

    void  createLicenseFile(String authorizationCode);

}
