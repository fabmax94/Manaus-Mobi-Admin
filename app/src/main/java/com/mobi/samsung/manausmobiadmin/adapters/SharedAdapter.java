package com.mobi.samsung.manausmobiadmin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobi.samsung.manausmobiadmin.R;
import com.mobi.samsung.manausmobiadmin.models.Shared;

import java.util.List;

/**
 * Created by christian.s on 11/20/2017.
 */

public class SharedAdapter extends BaseAdapter {

    Context ctx;
    List<Shared> sharedList;
    private ImageView imageView, imageView1;

    public SharedAdapter(Context ctx, List<Shared> sharedList) {
        this.ctx = ctx;
        this.sharedList = sharedList;

    }

    @Override
    public int getCount() {
        return sharedList.size();
    }

    @Override
    public Object getItem(int i) {
        return sharedList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Shared shared = sharedList.get(position);
        View viewContext = LayoutInflater.from(ctx).inflate(R.layout.item, viewGroup, false);

        TextView textView = viewContext.findViewById(R.id.txtDescription);
        textView.setText(shared.type.getDescription() + "-" + shared.intensity.getDescription());

        imageView = viewContext.findViewById(R.id.imgView);
        byte[] decodedString = Base64.decode(shared.image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);
        imageView1 = viewContext.findViewById(R.id.imgIcon);
        if (shared.type.getDescription() == "Trânsito") {
            imageView1.setImageResource(R.drawable.traffic);

        }else if (shared.type.getDescription() == "Perigo"){
            imageView1.setImageResource(R.drawable.warning);

        }

        return viewContext;
    }
}
