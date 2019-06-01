package com.mobi.samsung.manausmobiadmin.services.impl;


import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobi.samsung.manausmobiadmin.listeners.OnMainListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;
import com.mobi.samsung.manausmobiadmin.services.ISharedService;
import com.mobi.samsung.manausmobiadmin.util.NotificationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by fabio.silva on 11/16/2017.
 */

class SharedService implements ISharedService {

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public SharedService() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("mobilidade").child("shared");
    }

    @Override
    public void requestList(final OnMainListener listener) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Shared> sharedList = new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Shared shared = snap.getValue(Shared.class);
                    if (shared != null && shared.image != null) {
                        shared.key = snap.getKey();
                        sharedList.add(shared);
                    }

                }
                listener.addShared(sharedList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void removeOldShared() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Shared shared = dataSnapshot.getValue(Shared.class);
                if (shared != null) {
                    Date currentDate = new Date();
                    long diffInMillies = currentDate.getTime() - shared.date;
                    long normalizeMonth = 600000 * 43800;
                    long normalizeDay = 600000 * 1440;
                    Log.i("Shared", String.valueOf(diffInMillies) + " - " + String.valueOf(normalizeMonth) + " - " + String.valueOf((diffInMillies / normalizeMonth)));

                    if ((diffInMillies / normalizeMonth) >= 1) {
                        reference.child(dataSnapshot.getKey()).removeValue();
                    }

                    if ((diffInMillies / normalizeDay) >= 1) {
                        shared.image = null;
                        reference.child(dataSnapshot.getKey()).setValue(shared);
                    }

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void removeList(String key) {
        reference.child(key).removeValue();
    }

    @Override
    public void notification(final NotificationService notificationService, final List<Shared> sharedList) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, String>> td = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                ArrayList<String> keys = new ArrayList<>();
                for (Shared s : sharedList) {
                    keys.add(s.key);
                }
                if (td != null) {
                    for (Map.Entry<String, HashMap<String, String>> s : td.entrySet()) {
                        if (s.getValue().get("image") != null) {
                            if (!keys.contains(s.getKey())) {
                                notificationService.notification();
                                break;
                            }
                        }
                    }
                }

                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
