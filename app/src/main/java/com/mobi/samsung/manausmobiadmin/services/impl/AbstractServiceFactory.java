package com.mobi.samsung.manausmobiadmin.services.impl;


import com.mobi.samsung.manausmobiadmin.services.ISharedService;

/**
 * Created by fabio.silva on 11/20/2017.
 */

public abstract class AbstractServiceFactory {

    protected static ISharedService sharedService;

    public abstract ISharedService createSharedService();

}
