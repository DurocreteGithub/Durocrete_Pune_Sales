package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 27/6/17.
 */

public class Sitesummary {
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    String date;
    String in_time="";
    String out_time="";
    String Response;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return "Sitesummary{" +
                "date='" + date + '\'' +
                ", in_time='" + in_time + '\'' +
                ", out_time='" + out_time + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }
}
