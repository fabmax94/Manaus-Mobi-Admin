package com.mobi.samsung.manausmobiadmin.controllers.impl;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.mobi.samsung.manausmobiadmin.controllers.ISharedController;
import com.mobi.samsung.manausmobiadmin.listeners.OnMainListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;
import com.mobi.samsung.manausmobiadmin.persistences.AppDatabase;
import com.mobi.samsung.manausmobiadmin.services.ISharedService;
import com.mobi.samsung.manausmobiadmin.services.impl.AbstractServiceFactory;
import com.mobi.samsung.manausmobiadmin.services.impl.ConcreteServiceFactory;

/**
 * Created by fabio.silva on 11/16/2017.
 */

class SharedController implements ISharedController {
    private ISharedService sharedService;
    private AppDatabase app;

    public SharedController(Context context) {
        AbstractServiceFactory serviceFactory = new ConcreteServiceFactory();
        sharedService = serviceFactory.createSharedService();

        this.app = Room.databaseBuilder(context,
                AppDatabase.class, "database-mobi-admin").allowMainThreadQueries().build();
    }


    @Override
    public void setList(final OnMainListener listener) {
        sharedService.requestList(listener);
    }

    @Override
    public void removeLocal(String key) {
        this.app.sharedDAO().delete(this.app.sharedDAO().get(key));
        sharedService.removeList(key);
    }

    @Override
    public void addLocal(Shared shared) {
        if (this.app.sharedDAO().get(shared.key) == null) {
            this.app.sharedDAO().save(shared);
        }
    }
}
