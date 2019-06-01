package com.mobi.samsung.manausmobiadmin.util;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mobi.samsung.manausmobiadmin.MainActivity;
import com.mobi.samsung.manausmobiadmin.R;
import com.mobi.samsung.manausmobiadmin.listeners.OnNotificationListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;
import com.mobi.samsung.manausmobiadmin.persistences.AppDatabase;
import com.mobi.samsung.manausmobiadmin.services.ISharedService;
import com.mobi.samsung.manausmobiadmin.services.impl.ConcreteServiceFactory;

/**
 * Created by fabio.silva on 11/20/2017.
 */

public class NotificationService extends JobService implements OnNotificationListener {
    private ISharedService sharedService;
    private AppDatabase app;

    public NotificationService() {
        this.sharedService = new ConcreteServiceFactory().createSharedService();
        this.app = Room.databaseBuilder(this,
                AppDatabase.class, "database-mobi-admin").allowMainThreadQueries().build();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        sharedService.notification(this, this.app.sharedDAO().findAll());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

    @Override
    public void notification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification n;

        PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(p)
                .setSmallIcon(R.drawable.notification_admin)
                .setContentText(getString(R.string.newImage))
                .setContentTitle("Novas Imagens");
        n = builder.build();

        n.flags |= Notification.FLAG_AUTO_CANCEL;

        n.vibrate = new long[]{100, 250, 100, 500};

        nm.notify(R.string.app_name, n);
    }
}
