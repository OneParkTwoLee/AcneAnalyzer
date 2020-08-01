package com.onePtwoL.acneanalyzer;

public class Skin {
    public String getSkinPictureName() {
        return SkinPictureName;
    }

    public void setSkinPictureName(String skinPictureName) {
        SkinPictureName = skinPictureName;
    }

    public Skin(String skinPictureName) {
        SkinPictureName = skinPictureName;
    }

    String SkinPictureName;

}
