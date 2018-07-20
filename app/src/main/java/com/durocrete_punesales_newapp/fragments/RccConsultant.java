package com.durocrete_punesales_newapp.fragments;

/**
 * Created by root on 18/7/17.
 */

public class RccConsultant {

    String Id;
    String RCC_name;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRCC_name() {
        return RCC_name;
    }

    public void setRCC_name(String RCC_name) {
        this.RCC_name = RCC_name;
    }

    @Override
    public String toString() {
        return RCC_name;
    }
}



