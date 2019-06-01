package com.mobi.samsung.manausmobiadmin.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by christian.s on 11/20/2017.
 */

@Entity(tableName = "shared")
public class Shared {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @Ignore
    public long date;
    @Ignore
    public double latitude;
    @Ignore
    public double longitude;
    @Ignore
    public String userId;
    @Ignore
    public String image;

    @Ignore
    public String thoroughfare;

    @Ignore
    public String subLocality;

    public String key;
    @Ignore
    public SharedIntensity intensity;
    @Ignore
    public SharedType type;

    public Shared() {
    }
}
