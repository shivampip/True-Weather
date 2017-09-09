package com.shivam.tree.mousam.shiv;

/**
 * Created by Sittu Agrawal on 06-01-2017.
 */

public interface ResponseListener {
    abstract public void onResponse(String response);
    abstract public void onFail(String reason);
}
