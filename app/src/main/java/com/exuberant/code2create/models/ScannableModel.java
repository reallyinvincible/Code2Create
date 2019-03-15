package com.exuberant.code2create.models;

import java.util.List;

public class ScannableModel {

    List<Scannable> scannableList;

    public ScannableModel(List<Scannable> scannableList) {
        this.scannableList = scannableList;
    }

    public ScannableModel() {
    }

    public List<Scannable> getScannableList() {
        return scannableList;
    }

    public void setScannableList(List<Scannable> scannableList) {
        this.scannableList = scannableList;
    }
}
