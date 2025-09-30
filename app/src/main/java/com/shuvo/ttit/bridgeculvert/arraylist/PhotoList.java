package com.shuvo.ttit.bridgeculvert.arraylist;

import android.graphics.Bitmap;

public class PhotoList {
    private String photoName;
    private String uploadDate;
    private String stage;
    private Bitmap bitmap;
    private boolean imageNotAvailable;

    public PhotoList(String photoName, String uploadDate, String stage, Bitmap bitmap, boolean imageNotAvailable) {
        this.photoName = photoName;
        this.uploadDate = uploadDate;
        this.stage = stage;
        this.bitmap = bitmap;
        this.imageNotAvailable = imageNotAvailable;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isImageNotAvailable() {
        return imageNotAvailable;
    }

    public void setImageNotAvailable(boolean imageNotAvailable) {
        this.imageNotAvailable = imageNotAvailable;
    }
}
