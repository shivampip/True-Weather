package com.shivam.tree.mousam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.marcoscg.easylicensesdialog.EasyLicensesDialogCompat;
import com.shivam.tree.mousam.about.About;
import com.shivam.tree.mousam.security_tips.SecurityTips;

public class Setting extends AppCompatActivity {

    CheckBox showMapCB, showNotiCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        showMapCB= (CheckBox) findViewById(R.id.showMapCB);
        showNotiCB= (CheckBox) findViewById(R.id.showNotiCB);

        showMapCB.setChecked(isShowMap(this));
        showNotiCB.setChecked(isNotiEnabled(this));

        showMapCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setShowMap(b);
            }
        });

        showNotiCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setNotiEnabled(b);
            }
        });


    }//onCreateEND



    public static boolean isShowMap(Context context){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);
        boolean is= sp.getBoolean(Const.SHOULD_SHOW_MAP, true);
        return is;
    }

    private void setShowMap(boolean is){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean(Const.SHOULD_SHOW_MAP, is);
        editor.commit();
    }


    public static boolean isNotiEnabled(Context context){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(context);
        boolean is= sp.getBoolean(Const.SHOULD_SHOW_NOTI, true);
        return is;
    }

    private void setNotiEnabled(boolean is){
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean(Const.SHOULD_SHOW_NOTI, is);
        editor.commit();
    }




    public void about(View v){

        Intent aboutIn= new Intent(this, About.class);
        startActivity(aboutIn);

    }

    public void license(View v){

        new EasyLicensesDialogCompat(this)
                .setTitle("Licenses")
                .setPositiveButton(android.R.string.ok, null)
                .show();

    }

    public void showSecurityTips(View v){
        startActivity(new Intent(this , SecurityTips.class));
    }


}//classEND


