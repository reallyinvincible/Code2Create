package com.acmvit.code2create.models;

import java.util.List;

public class CouponsUser {

    List<String> couponsUserList;

    public CouponsUser() {
    }

    public CouponsUser(List<String> couponsUserList) {
        this.couponsUserList = couponsUserList;
    }

    public List<String> getCouponsUserList() {
        return couponsUserList;
    }

    public void setCouponsUserList(List<String> couponsUserList) {
        this.couponsUserList = couponsUserList;
    }
}
