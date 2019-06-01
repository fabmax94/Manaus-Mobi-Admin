package com.mobi.samsung.manausmobiadmin.controllers;


import com.mobi.samsung.manausmobiadmin.listeners.OnMainListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;

/**
 * Created by fabio.silva on 11/16/2017.
 */

public interface ISharedController {
    void setList(final OnMainListener listener);

    void removeLocal(String key);

    void addLocal(Shared shared);
}
