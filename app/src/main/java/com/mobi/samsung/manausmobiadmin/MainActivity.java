package com.mobi.samsung.manausmobiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.mobi.samsung.manausmobiadmin.adapters.SharedAdapter;
import com.mobi.samsung.manausmobiadmin.controllers.ISharedController;
import com.mobi.samsung.manausmobiadmin.controllers.impl.AbstractControllerFactory;
import com.mobi.samsung.manausmobiadmin.controllers.impl.ConcreteControllerFactory;
import com.mobi.samsung.manausmobiadmin.listeners.OnMainListener;
import com.mobi.samsung.manausmobiadmin.models.Shared;
import com.mobi.samsung.manausmobiadmin.util.NotificationService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMainListener {
    private SharedAdapter sharedAdapter;
    private ISharedController sharedController;
    private List<Shared> sharedList;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
        sharedList = new ArrayList<Shared>();

        AbstractControllerFactory controllerFactory = new ConcreteControllerFactory();
        sharedController = controllerFactory.createSharedController(getApplicationContext());

        listView = (ListView) findViewById(R.id.listView);
        TextView emptyView = (TextView) findViewById(android.R.id.empty);
        listView.setEmptyView(emptyView);
        cleanAdapter();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shared shared = (Shared) listView.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SharedDetailsActivity.class);


                intent.putExtra("KEY", shared.key);
                intent.putExtra("DETAILS", shared.type.getDescription() + "-" + shared.intensity.getDescription());
                intent.putExtra("FOTO", shared.image);
                startActivity(intent);
            }
        });
    }

    @Override
    public void addShared(List<Shared> sList) {
        sharedList = sList;
        cleanAdapter();
        for (Shared shared : sharedList) {
            sharedController.addLocal(shared);
        }
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    private void cleanAdapter() {
        sharedAdapter = new SharedAdapter(this, sharedList);
        sharedAdapter.notifyDataSetChanged();
        listView.setAdapter(sharedAdapter);
    }

    private void initService() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(NotificationService.class)
                // uniquely identifies the job
                .setTag("notification")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(1, 1800))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                // constraints that need to be satisfied for the job to run
                .setConstraints(Constraint.ON_UNMETERED_NETWORK)
                .build();

        dispatcher.mustSchedule(myJob);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        sharedList = new ArrayList<Shared>();
        cleanAdapter();
        sharedController.setList(this);
    }
}
