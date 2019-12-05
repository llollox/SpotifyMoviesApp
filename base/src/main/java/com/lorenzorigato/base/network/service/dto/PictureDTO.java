package com.lorenzorigato.base.network.service.dto;

public class PictureDTO {


    // Class attributes ****************************************************************************
    String thumb;
    String medium;
    String big;


    // Constructor *********************************************************************************
    public PictureDTO(String thumb, String medium, String big) {
        this.thumb = thumb;
        this.medium = medium;
        this.big = big;
    }


    // Class methods *******************************************************************************
    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    @Override
    public String toString() {
        return "PictureDTO{" +
                "thumb='" + thumb + '\'' +
                ", medium='" + medium + '\'' +
                ", big='" + big + '\'' +
                '}';
    }
}
