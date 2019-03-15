package com.exuberant.code2create.models;

public class UserModel {
    String userEmail;
    String password;
    boolean isExternal;
    boolean isSelected;
    String wifiCoupon;

    public UserModel(String userEmail, String password, boolean isExternal, boolean isSelected, String wifiCoupon) {
        this.userEmail = userEmail;
        this.password = password;
        this.isExternal = isExternal;
        this.isSelected = isSelected;
        this.wifiCoupon = wifiCoupon;
    }

    public UserModel() {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExternal() {
        return isExternal;
    }

    public void setExternal(boolean external) {
        isExternal = external;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getWifiCoupon() {
        return wifiCoupon;
    }

    public void setWifiCoupon(String wifiCoupon) {
        this.wifiCoupon = wifiCoupon;
    }
}
