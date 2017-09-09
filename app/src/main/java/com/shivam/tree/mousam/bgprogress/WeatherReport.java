package com.shivam.tree.mousam.bgprogress;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.R;
import com.shivam.tree.mousam.Share;
import com.shivam.tree.mousam.backup.Backup;
import com.shivam.tree.mousam.backup.BackupData;
import com.shivam.tree.mousam.myad.AdBhai;

import java.io.File;

/**
 * Created by Sittu Agrawal on 16-01-2017.
 */

public class WeatherReport {

    Context context;
    Activity activity;
    Backup backup;
    BackupData backupData;
    AdBhai adBhai;

    public WeatherReport(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        init();
    }

    private void init() {
        backup = new Backup(context);
        backupData = new BackupData();
        syncData();
    }

    private void syncData() {
        if (backup.isBackupFresh()) {
            if (backup.isBackupAvailable()) {
                C.d("last synced " + backup.getBackupDuration() + " ago");
                restore();
            }
        }
    }

    private void restore() {
        BackupData backupData = backup.restore();
        ProcessData pd = new ProcessData(context, backupData, ProcessData.TODAY);
        showTip(pd, ProcessData.TODAY);
    }


    public void showTip(ProcessData pd, int when) {
        final AlertDialog ad[] = new AlertDialog[2];
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.weather_tip_dialog_layout, null);

        final TextView titleTv = (TextView) layout.findViewById(R.id.tipDiaTitle);
        final TextView bodyTv = (TextView) layout.findViewById(R.id.tipDiaTv);
        final ImageView iconIv = (ImageView) layout.findViewById(R.id.tipDiaIV);
        final TextView timeTv = (TextView) layout.findViewById(R.id.tipDiaTimeTv);
        final Button btn = (Button) layout.findViewById(R.id.tipDiaBtn);
        final ImageButton leftIb = (ImageButton) layout.findViewById(R.id.tipDiaLeftIB);
        final ImageButton rightIb = (ImageButton) layout.findViewById(R.id.tipDiarightIB);
        final ImageButton shareIb= (ImageButton) layout.findViewById(R.id.tipDiaShareIB);

        titleTv.setTypeface(Const.getCustomTypeface(context, Const.TF_NERKSHIRE_SWASH));
        bodyTv.setTypeface(Const.getCustomTypeface(context, Const.TF_JUNGE_REGULAR));
        bodyTv.setText(pd.getDetails());
        iconIv.setImageResource(pd.getIcon());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad[0].dismiss();
                adBhai.showIntAdd();
            }
        });
        leftIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackupData backupData = backup.restore();
                ProcessData pd = new ProcessData(context, backupData, ProcessData.TODAY);
                bodyTv.setText(pd.getDetails());
                iconIv.setImageResource(pd.getIcon());
                timeTv.setText("Today");
                leftIb.setVisibility(View.GONE);
                rightIb.setVisibility(View.VISIBLE);
            }
        });
        rightIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackupData backupData = backup.restore();
                ProcessData pd = new ProcessData(context, backupData, ProcessData.TOMORROW);
                bodyTv.setText(pd.getDetails());
                iconIv.setImageResource(pd.getIcon());
                timeTv.setText("Tomorrow");
                leftIb.setVisibility(View.VISIBLE);
                rightIb.setVisibility(View.GONE);
            }
        });

        shareIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display("Sharing...");
                Share share= new Share(context);
                Bitmap bitmap= share.getScreenShot(view);
                File file= share.store(bitmap, "true_weather.png");
                share.shareImage(file);
            }
        });

        switch (when) {
            case ProcessData.TODAY:
                timeTv.setText("Today");
                leftIb.setVisibility(View.GONE);
                rightIb.setVisibility(View.VISIBLE);
                break;
            case ProcessData.TOMORROW:
                timeTv.setText("Tomorrow");
                leftIb.setVisibility(View.VISIBLE);
                rightIb.setVisibility(View.GONE);
                break;
            case ProcessData.DAY_AFTER_TOMORROW:
                timeTv.setText("Day after Tomorrow");
                leftIb.setVisibility(View.GONE);
                rightIb.setVisibility(View.GONE);
                break;
            default:
                timeTv.setVisibility(View.GONE);
                leftIb.setVisibility(View.GONE);
                rightIb.setVisibility(View.GONE);
                break;
        }

        builder.setView(layout);
        ad[0] = builder.create();
        ad[0].setCancelable(false);
        ad[0].show();
        //mp.stopProgress();
        adBhai = new AdBhai(context, activity);
    }


    public void display(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}//classEND

