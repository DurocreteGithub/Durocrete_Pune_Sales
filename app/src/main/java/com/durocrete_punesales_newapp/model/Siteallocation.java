package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 29/6/17.
 */

public class Siteallocation {

    private String SITE_Id;
    private String SITE_Name_var;
    private String SITE_Address_var;
    private String CL_Name_var;
    private String CL_OfficeTelNo_var;

    public String getSITE_Id() {
        return SITE_Id;
    }

    public void setSITE_Id(String SITE_Id) {
        this.SITE_Id = SITE_Id;
    }

    public String getSITE_Name_var() {
        return SITE_Name_var;
    }

    public void setSITE_Name_var(String SITE_Name_var) {
        this.SITE_Name_var = SITE_Name_var;
    }

    public String getSITE_Address_var() {
        return SITE_Address_var;
    }

    public void setSITE_Address_var(String SITE_Address_var) {
        this.SITE_Address_var = SITE_Address_var;
    }

    public String getCL_Name_var() {
        return CL_Name_var;
    }

    public void setCL_Name_var(String CL_Name_var) {
        this.CL_Name_var = CL_Name_var;
    }

    public String getCL_OfficeTelNo_var() {
        return CL_OfficeTelNo_var;
    }

    public void setCL_OfficeTelNo_var(String CL_OfficeTelNo_var) {
        this.CL_OfficeTelNo_var = CL_OfficeTelNo_var;
    }

    @Override
    public String toString() {
        return "Siteallocationactivity{" +
                "SITE_Id='" + SITE_Id + '\'' +
                ", SITE_Name_var='" + SITE_Name_var + '\'' +
                ", SITE_Address_var='" + SITE_Address_var + '\'' +
                ", CL_Name_var='" + CL_Name_var + '\'' +
                ", CL_OfficeTelNo_var='" + CL_OfficeTelNo_var + '\'' +
                '}';
    }
}
