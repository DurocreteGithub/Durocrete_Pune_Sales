package com.durocrete_punesales_newapp.model;

import java.util.ArrayList;

/**
 * Created by root on 30/6/17.
 */

public class Sightkyc {

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private String clientName;
    String siteId;
    String Completion_Date;
    String RCC_Consultant;
    String Architect;
    String ConstructionManagement;
    String Geo_tech_Invest;
    String No_of_Buildings;
    String status;
    String Block_Work_Plaster;
    String Finishes;
    String RCC;
    String siteaddress;
    String Build_Up_Area;
    String Construction_Period;
    String proposed_date;
    String proposed_building;
    String under_const_building;
    String Rcc_flag;
    String Architecture_flag;
    String Construnction_flag;
    String rerano;

    public String getRerano() {
        return rerano;
    }

    public void setRerano(String rerano) {
        this.rerano = rerano;
    }

    public String getArchitecture_flag() {
        return Architecture_flag;
    }

    public void setArchitecture_flag(String architecture_flag) {
        Architecture_flag = architecture_flag;
    }

    public String getConstrunction_flag() {
        return Construnction_flag;
    }

    public void setConstrunction_flag(String construnction_flag) {
        Construnction_flag = construnction_flag;
    }

    public String getRcc_flag() {
        return Rcc_flag;
    }

    public void setRcc_flag(String rcc_flag) {
        Rcc_flag = rcc_flag;
    }

    private ArrayList<MaterialDetailModel> materialDetailModelArrayList = null;

    public ArrayList<MaterialDetailModel> getMaterialDetailModelArrayList() {
        return materialDetailModelArrayList;
    }

    public void setMaterialDetailModelArrayList(ArrayList<MaterialDetailModel> materialDetailModelArrayList) {
        this.materialDetailModelArrayList = materialDetailModelArrayList;
    }

    public String getProposed_date() {
        return proposed_date;
    }

    public void setProposed_date(String proposed_date) {
        this.proposed_date = proposed_date;
    }

    public String getProposed_building() {
        return proposed_building;
    }

    public void setProposed_building(String proposed_building) {
        this.proposed_building = proposed_building;
    }

    public String getUnder_const_building() {
        return under_const_building;
    }

    public void setUnder_const_building(String under_const_building) {
        this.under_const_building = under_const_building;
    }

    private ArrayList<MaterialDetailModel> addedMaterialList = null;


    public ArrayList<MaterialDetailModel> getAddedMaterialList() {
        return addedMaterialList;
    }

    public void setCompletion_Date(String completion_Date) {
        Completion_Date = completion_Date;
    }

    public void setAddedMaterialList(ArrayList<MaterialDetailModel> addedMaterialList) {
        this.addedMaterialList = addedMaterialList;
    }


    public String getBuild_Up_Area() {
        return Build_Up_Area;
    }

    public void setBuild_Up_Area(String build_Up_Area) {
        Build_Up_Area = build_Up_Area;
    }


    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        this.siteaddress = siteaddress;
    }


    public String getConstruction_Period() {
        return Construction_Period;
    }

    public void setConstruction_Period(String construction_Period) {
        Construction_Period = construction_Period;
    }

    public String getCompletion_Date() {
        return Completion_Date;
    }

    public String getRCC_Consultant() {
        return RCC_Consultant;
    }

    public void setRCC_Consultant(String RCC_Consultant) {
        this.RCC_Consultant = RCC_Consultant;
    }

    public String getArchitect() {
        return Architect;
    }

    public void setArchitect(String architect) {
        Architect = architect;
    }

    public String getConstructionManagement() {
        return ConstructionManagement;
    }

    public void setConstructionManagement(String constructionManagement) {
        ConstructionManagement = constructionManagement;
    }

    public String getGeo_tech_Invest() {
        return Geo_tech_Invest;
    }

    public void setGeo_tech_Invest(String geo_tech_Invest) {
        Geo_tech_Invest = geo_tech_Invest;
    }

    public String getNo_of_Buildings() {
        return No_of_Buildings;
    }

    public void setNo_of_Buildings(String no_of_Buildings) {
        No_of_Buildings = no_of_Buildings;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlock_Work_Plaster() {
        return Block_Work_Plaster;
    }

    public void setBlock_Work_Plaster(String block_Work_Plaster) {
        Block_Work_Plaster = block_Work_Plaster;
    }

    public String getFinishes() {
        return Finishes;
    }

    public void setFinishes(String finishes) {
        Finishes = finishes;
    }

    public String getRCC() {
        return RCC;
    }

    public void setRCC(String RCC) {
        this.RCC = RCC;
    }

    @Override
    public String toString() {
        return "Sightkyc{" +
                "siteId='" + siteId + '\'' +
                ", Completion_Date='" + Completion_Date + '\'' +
                ", RCC_Consultant='" + RCC_Consultant + '\'' +
                ", Architect='" + Architect + '\'' +
                ", ConstructionManagement='" + ConstructionManagement + '\'' +
                ", Geo_tech_Invest='" + Geo_tech_Invest + '\'' +
                ", No_of_Buildings='" + No_of_Buildings + '\'' +
                ", status='" + status + '\'' +
                ", Block_Work_Plaster='" + Block_Work_Plaster + '\'' +
                ", Finishes='" + Finishes + '\'' +
                ", RCC='" + RCC + '\'' +
                ", siteaddress='" + siteaddress + '\'' +
                ", Build_Up_Area='" + Build_Up_Area + '\'' +
                ", Construction_Period='" + Construction_Period + '\'' +
                ", addedMaterialList=" + addedMaterialList +
                '}';
    }
}

