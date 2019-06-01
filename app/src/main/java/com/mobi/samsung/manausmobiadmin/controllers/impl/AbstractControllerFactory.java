package com.mobi.samsung.manausmobiadmin.controllers.impl;

import android.content.Context;

import com.mobi.samsung.manausmobiadmin.controllers.ISharedController;


/**
 * Created by fabio.silva on 11/20/2017.
 */

public abstract class AbstractControllerFactory {
    public abstract ISharedController createSharedController(Context context);

}
