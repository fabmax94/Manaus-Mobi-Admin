package com.mobi.samsung.manausmobiadmin.persistences;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mobi.samsung.manausmobiadmin.models.Shared;

/**
 * Created by fabio.silva on 11/6/2017.
 */

@Database(entities = {Shared.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ISharedDAO sharedDAO();
}
