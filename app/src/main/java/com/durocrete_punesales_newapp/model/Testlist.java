package com.durocrete_punesales_newapp.model;

/**
 * Created by root on 20/5/17.
 */

public class Testlist {

    String testName;
    int testNo;
    String Quantity;
    boolean ischecked;

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }


    public boolean ischecked(boolean b) {
        return ischecked;
    }

    public boolean getIsChecked() {
        return this.ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestNo() {
        return testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }

    @Override
    public String toString() {
        return Quantity;
    }
}