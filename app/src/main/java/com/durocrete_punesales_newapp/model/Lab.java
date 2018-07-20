package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 20/7/17.
 */

public class Lab {
    String Id;
    String lab_name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLab_name() {
        return lab_name;
    }

    public void setLab_name(String lab_name) {
        this.lab_name = lab_name;
    }

    @Override
    public String toString() {
        return lab_name;
    }
}
