package com.mobi.samsung.manausmobiadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.samsung.manausmobiadmin.controllers.ISharedController;
import com.mobi.samsung.manausmobiadmin.controllers.impl.AbstractControllerFactory;
import com.mobi.samsung.manausmobiadmin.controllers.impl.ConcreteControllerFactory;
import com.mobi.samsung.manausmobiadmin.models.Shared;

public class SharedDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ISharedController sharedController;
    private TextView txtDetails;
    private ImageView imageView;
    private ImageButton imageButton;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_details);


        Intent in = getIntent();
        String detail = in.getStringExtra("DETAILS");
        txtDetails = (TextView) findViewById(R.id.txtDetails);
        txtDetails.setText(detail.toString());

        key = in.getStringExtra("KEY");

        String image = getIntent().getStringExtra("FOTO");

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageView = (ImageView) findViewById(R.id.imgTeste);
        imageView.setImageBitmap(decodedByte);

        imageButton = (ImageButton) findViewById(R.id.btnRemove);
        imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRemove:
                AbstractControllerFactory controllerFactory = new ConcreteControllerFactory();
                sharedController = controllerFactory.createSharedController(getApplicationContext());
                sharedController.removeLocal(key);
                this.finish();
                break;

        }
    }
}
