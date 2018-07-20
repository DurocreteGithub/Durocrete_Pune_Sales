package com.durocrete_punesales_newapp.fragments;

/**
 * Created by root on 18/7/17.
 */

public class Testlab {

    String id;
    String  testlabname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestlabname() {
        return testlabname;
    }

    public void setTestlabname(String testlabname) {
        this.testlabname = testlabname;
    }

    @Override
    public String toString() {
        return testlabname;
    }
}
