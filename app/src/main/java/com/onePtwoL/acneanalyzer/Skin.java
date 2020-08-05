package com.onePtwoL.acneanalyzer;

import java.io.Serializable;

public class Skin implements Serializable {
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
