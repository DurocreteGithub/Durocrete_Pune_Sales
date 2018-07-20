package com.durocrete_punesales_newapp.model;

import java.io.Serializable;

/**
 * Created by root on 24/5/17.
 */
public class MaterialDetailModel implements Serializable {
    private String materialId ="";
    private String materialName ="";
    private String materialQuantity = "";
    private String quantityId="";

    public String getQuantityId() {
        return quantityId;
    }

    public void setQuantityId(String quantityId) {
        this.quantityId = quantityId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialQuantity() {
        return materialQuantity;
    }

    public void setMaterialQuantity(String materialQuantity) {
        this.materialQuantity = materialQuantity;
    }


    @Override
    public String toString() {
        return "MaterialDetailModel  materialId : " +materialId +  " materialName : " + materialName + " materialQuantity "+ materialQuantity+" QuantityId "+ quantityId;
    }
}
