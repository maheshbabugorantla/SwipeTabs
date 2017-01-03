package com.example.ganga.swipetabs.Extra;

/**
 * Created by Mahesh Babu Gorantla on 1/1/17.
 */

public class FlagItem {

    private String countryName;
    private int flag;

    public FlagItem(String countryName, int flag) {
        this.countryName = countryName;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public String getCountryName() {
        return countryName;
    }
}
