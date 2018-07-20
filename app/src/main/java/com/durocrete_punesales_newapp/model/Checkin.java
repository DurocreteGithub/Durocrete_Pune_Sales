package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 5/7/17.
 */

public class Checkin {
    String Clientname;
    String Sitename;
    String Siteaddress;
    String Contactpersonname;
    String Contactno;
    Double latitude;
    Double Longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getClientname() {
        return Clientname;
    }

    public void setClientname(String clientname) {
        Clientname = clientname;
    }

    public String getSitename() {
        return Sitename;
    }

    public void setSitename(String sitename) {
        Sitename = sitename;
    }

    public String getSiteaddress() {
        return Siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        Siteaddress = siteaddress;
    }

    public String getContactpersonname() {
        return Contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        Contactpersonname = contactpersonname;
    }

    public String getContactno() {
        return Contactno;
    }

    public void setContactno(String contactno) {
        Contactno = contactno;
    }

    @Override
    public String toString() {
        return "Checkin{" +
                "Clientname='" + Clientname + '\'' +
                ", Sitename='" + Sitename + '\'' +
                ", Siteaddress='" + Siteaddress + '\'' +
                ", Contactpersonname='" + Contactpersonname + '\'' +
                ", Contactno='" + Contactno + '\'' +
                '}';
    }
}
