package com.shivam.tree.mousam.bgprogress;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.shivam.tree.mousam.Const;
import com.shivam.tree.mousam.ShowWeather;

public class FetchService extends Service {
    public FetchService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Syncer syncer = new Syncer(this, new ResponseCallback() {
            @Override
            public void onCompleted(String msg) {
                MyNoti myNoti = new MyNoti(getApplicationContext());
                myNoti.setBody("Weather Forcast Updated.\nClick to See");
                Intent tipDialogIn= new Intent(FetchService.this, ShowWeather.class);
                tipDialogIn.putExtra(Const.DIALOG_CODE, Const.DC_TIP);
                myNoti.setIntent(tipDialogIn);
                myNoti.show();

                stopSelf();
            }

            @Override
            public void sendMsg(String msg) {
            }

            @Override
            public void onFailed(String msg) {
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}//classEND
