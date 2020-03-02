package com.acmvit.code2create.models;

public class Scannable {
    String scannableTitle;
    String scannableKey;
    String scannableValue;
    String scannableStartTime;
    String scannableEndTime;
    String scannableType;
    String scannableDate;

    public Scannable() {
    }

    public Scannable(String scannableTitle, String scannableKey, String scannableValue, String scannableStartTime, String scannableEndTime, String scannableType, String scannableDate) {
        this.scannableTitle = scannableTitle;
        this.scannableKey = scannableKey;
        this.scannableValue = scannableValue;
        this.scannableStartTime = scannableStartTime;
        this.scannableEndTime = scannableEndTime;
        this.scannableType = scannableType;
        this.scannableDate = scannableDate;
    }

    public String getScannableTitle() {
        return scannableTitle;
    }

    public void setScannableTitle(String scannableTitle) {
        this.scannableTitle = scannableTitle;
    }

    public String getScannableKey() {
        return scannableKey;
    }

    public void setScannableKey(String scannableKey) {
        this.scannableKey = scannableKey;
    }

    public String getScannableValue() {
        return scannableValue;
    }

    public void setScannableValue(String scannableValue) {
        this.scannableValue = scannableValue;
    }

    public String getScannableStartTime() {
        return scannableStartTime;
    }

    public void setScannableStartTime(String scannableStartTime) {
        this.scannableStartTime = scannableStartTime;
    }

    public String getScannableEndTime() {
        return scannableEndTime;
    }

    public void setScannableEndTime(String scannableEndTime) {
        this.scannableEndTime = scannableEndTime;
    }

    public String getScannableType() {
        return scannableType;
    }

    public void setScannableType(String scannableType) {
        this.scannableType = scannableType;
    }

    public String getScannableDate() {
        return scannableDate;
    }

    public void setScannableDate(String scannableDate) {
        this.scannableDate = scannableDate;
    }
}
