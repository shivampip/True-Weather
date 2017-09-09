package com.shivam.tree.mousam.fcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.messaging.RemoteMessage;
import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.fcm.ProcessFCM;

import java.util.Map;

/**
 * Created by Sittu Agrawal on 18-01-2017.
 */

public class ShowDialog {



    public static void show(Context context, ProcessFCM.DialogData dd) {
        String title= dd.getTitle();
        String desc= dd.getDesc();
        String iconUrl= dd.getIconUrl();

        final AlertDialog ad[]= new AlertDialog[2];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.wish_dialog_layout, null);

        TextView titleTv = (TextView) layout.findViewById(R.id.wishTitleTv);
        TextView descTv = (TextView) layout.findViewById(R.id.wishDescTv);
        ImageView iconIv = (ImageView) layout.findViewById(R.id.wishIconIv);
        Button btn= (Button) layout.findViewById(R.id.wishBtn);

        titleTv.setTypeface(Const.getCustomTypeface(context, Const.TF_NERKSHIRE_SWASH));
        descTv.setTypeface(Const.getCustomTypeface(context, Const.TF_JUNGE_REGULAR));
        titleTv.setText(title);
        descTv.setText(desc);
        Glide.with(context).load(iconUrl).into(iconIv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad[0].dismiss();
            }
        });

        builder.setView(layout);
        ad[0] = builder.create();

        ad[0].show();
        ProcessFCM.removeDialogData(context);
    }


}//classEND
