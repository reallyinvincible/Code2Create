package com.exuberant.code2create.models;

import androidx.recyclerview.widget.LinearSmoothScroller;

public class Scannable {
    String scannableKey;
    String scannableValue;

    public Scannable(String scannableKey, String scannableValue) {
        this.scannableKey = scannableKey;
        this.scannableValue = scannableValue;
    }

    public Scannable() {
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
}
