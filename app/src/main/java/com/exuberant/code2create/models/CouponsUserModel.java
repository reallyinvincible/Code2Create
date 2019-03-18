package com.exuberant.code2create.models;

import java.util.List;

public class CouponsUserModel {

    List<CouponsUser> couponsUserModeList;

    public CouponsUserModel() {
    }

    public CouponsUserModel(List<CouponsUser> couponsUserModeList) {
        this.couponsUserModeList = couponsUserModeList;
    }

    public List<CouponsUser> getCouponsUserModeList() {
        return couponsUserModeList;
    }

    public void setCouponsUserModeList(List<CouponsUser> couponsUserModeList) {
        this.couponsUserModeList = couponsUserModeList;
    }
}
