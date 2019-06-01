package com.mobi.samsung.manausmobiadmin.services.impl;


import com.mobi.samsung.manausmobiadmin.services.ISharedService;

/**
 * Created by fabio.silva on 11/20/2017.
 */

public class ConcreteServiceFactory extends AbstractServiceFactory {

    @Override
    public ISharedService createSharedService() {
        if (sharedService == null) {
            sharedService = new SharedService();
        }
        return sharedService;
    }
}
