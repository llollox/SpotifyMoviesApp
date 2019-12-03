package com.lorenzorigato.base.security;

import com.lorenzorigato.base.BuildConfig;
import com.lorenzorigato.base.security.antitampering.IAntiTampering;
import com.lorenzorigato.base.util.IAppInfo;

public class SecurityManager implements ISecurityManager {


    // Private class attributes ********************************************************************
    private IAntiTampering antiTampering;
    private IAppInfo appInfo;


    // Constructor *********************************************************************************
    public SecurityManager(IAntiTampering antiTampering, IAppInfo appInfo) {
        this.antiTampering = antiTampering;
        this.appInfo = appInfo;
    }


    // ISecurityManager methods ********************************************************************
    @Override
    public boolean canAppExecute() {
        if (!BuildConfig.DEBUG && appInfo.isProduction()) {
            return !this.antiTampering.isAppTampered();
        }

        return true;
    }
}
