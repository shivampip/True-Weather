package com.shivam.tree.mousam.bgprogress;

import java.util.Date;

/**
 * Created by Sittu Agrawal on 17-01-2017.
 */

public class Condition {
    String name;
    int iconRes;
    int count;
    int prayority;
    Date dt;
    String main;

    public Condition() {
        this.count = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrayority() {
        return prayority;
    }

    public void setPrayority(int prayority) {
        this.prayority = prayority;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrCount() {
        this.count++;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getDt() {
        return dt;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMain() {
        return main;
    }
}//classEND
