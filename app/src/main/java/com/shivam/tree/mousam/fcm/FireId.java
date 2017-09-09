package com.shivam.tree.mousam.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.shivam.tree.mousam.bgprogress.C;

/**
 * Created by Sittu Agrawal on 17-01-2017.
 */

public class FireId extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        C.d("Firebase Id Token is :- "+refreshedToken);

    }



}//classEND
