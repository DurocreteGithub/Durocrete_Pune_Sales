package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 27/6/17.
 */

public class Summary {
    String SITE_Name_var;
    String in_time;
    String out_time;
    String Response;


    public String getSITE_Name_var() {
        return SITE_Name_var;
    }

    public void setSITE_Name_var(String SITE_Name_var) {
        this.SITE_Name_var = SITE_Name_var;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }



    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "SITE_Name_var='" + SITE_Name_var + '\'' +
                ", in_time='" + in_time + '\'' +
                ", out_time='" + out_time + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }
}

