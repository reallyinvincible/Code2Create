package com.acmvit.code2create.models;

import java.util.List;
import java.util.Map;

public class CouponsUser {

    //List<String> couponsUserList;
    Map<String, String> couponUidList;

    public CouponsUser() {
    }

   /* public CouponsUser(List<String> couponsUserList) {
        this.couponsUserList = couponsUserList;
    }*/

    public Map<String, String> getCouponUidList() {
        return couponUidList;
    }

    public void setCouponUidList(Map<String, String> couponUidList) {
        this.couponUidList = couponUidList;
    }

    /*public List<String> getCouponsUserList() {
        return couponsUserList;
    }

    public void setCouponsUserList(List<String> couponsUserList) {
        this.couponsUserList = couponsUserList;
    }*/
}
