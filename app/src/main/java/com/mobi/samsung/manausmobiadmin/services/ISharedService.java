package com.mobi.samsung.manausmobiadmin.services;


import com.mobi.samsung.manausmobiadmin.listeners.OnMainListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;
import com.mobi.samsung.manausmobiadmin.util.NotificationService;

import java.util.List;

/**
 * Created by fabio.silva on 11/16/2017.
 */

public interface ISharedService {

    void requestList(final OnMainListener listener);

    void removeOldShared();

    void removeList(String key);

    void notification(NotificationService notificationService, List<Shared> sharedList);
}
