package com.durocrete_punesales_newapp.fragments;

/**
 * Created by root on 18/7/17.
 */

public class Architecture {

    String Id;
    String architecture_name;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getArchitecture_name() {
        return architecture_name;
    }

    public void setArchitecture_name(String architecture_name) {
        this.architecture_name = architecture_name;
    }

    @Override
    public String toString() {
        return  architecture_name ;

    }
}



