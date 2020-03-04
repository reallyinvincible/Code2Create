package com.acmvit.code2create.models;

import java.util.Map;

public class CouponsUser {

    Map<String, String> couponUidList;

    public CouponsUser() {
    }

    public Map<String, String> getCouponUidList() {
        return couponUidList;
    }

    public void setCouponUidList(Map<String, String> couponUidList) {
        this.couponUidList = couponUidList;
    }
}
