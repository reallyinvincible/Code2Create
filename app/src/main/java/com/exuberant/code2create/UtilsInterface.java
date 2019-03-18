package com.exuberant.code2create;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilsInterface {

    public static String transformString(String string) {
        return string.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    public static String get_SHA_512_password(String password_to_hash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password_to_hash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static Date getDateObject(String dateString, String timeString){
        String completeDate = dateString + " " + timeString;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        try {
            return simpleDateFormat.parse(completeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int compareDates(Date date){
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate.compareTo(date);
    }

}