package com.onePtwoL.acneanalyzer;

import java.io.Serializable;

public class Skin implements Serializable {


    public Skin(String skinPictureName, String skinPicturePath) {
        SkinPictureName = skinPictureName;
        SkinPicturePath = skinPicturePath;
    }

    public String getSkinPictureName() {
        return SkinPictureName;
    }

    public void setSkinPictureName(String skinPictureName) {
        SkinPictureName = skinPictureName;
    }

    public String getSkinPicturePath() {
        return SkinPicturePath;
    }

    public void setSkinPicturePath(String skinPicturePath) {
        SkinPicturePath = skinPicturePath;
    }

    String SkinPictureName;
    String SkinPicturePath;

}
