package com.shivam.tree.mousam.bgprogress;

/**
 * Created by Sittu Agrawal on 16-01-2017.
 */

public interface ResponseCallback {

    public abstract void onCompleted(String msg);

    public abstract void sendMsg(String msg);

    public abstract void onFailed(String msg);

}
