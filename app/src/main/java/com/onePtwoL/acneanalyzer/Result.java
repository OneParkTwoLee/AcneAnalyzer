package com.onePtwoL.acneanalyzer;

public class Result {

    String skinPicturePath;
    String acneTypeNum;

    public Result(String skinPicturePath, String acneTypeNum) {
        this.skinPicturePath = skinPicturePath;
        this.acneTypeNum = acneTypeNum;
    }

    public String getSkinPicturePath() {
        return skinPicturePath;
    }

    public void setSkinPicturePath(String skinPicturePath) {
        this.skinPicturePath = skinPicturePath;
    }

    public String getAcneTypeNum() {
        return acneTypeNum;
    }

    public void setAcneTypeNum(String acneTypeNum) {
        this.acneTypeNum = acneTypeNum;
    }

}
