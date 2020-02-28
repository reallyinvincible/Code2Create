package com.exuberant.code2create4.models;

public class User {

    String email;
    String password;
    boolean externalParticipant;
    boolean selectedForNextRound;
    String wifiCoupon;

    public User() {
    }

    public User(String email, String password, boolean externalParticipant, boolean selectedForNextRound, String wifiCoupon) {
        this.email = email;
        this.password = password;
        this.externalParticipant = externalParticipant;
        this.selectedForNextRound = selectedForNextRound;
        this.wifiCoupon = wifiCoupon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExternalParticipant() {
        return externalParticipant;
    }

    public void setExternalParticipant(boolean externalParticipant) {
        this.externalParticipant = externalParticipant;
    }

    public boolean isSelectedForNextRound() {
        return selectedForNextRound;
    }

    public void setSelectedForNextRound(boolean selectedForNextRound) {
        this.selectedForNextRound = selectedForNextRound;
    }

    public String getWifiCoupon() {
        return wifiCoupon;
    }

    public void setWifiCoupon(String wifiCoupon) {
        this.wifiCoupon = wifiCoupon;
    }
}
