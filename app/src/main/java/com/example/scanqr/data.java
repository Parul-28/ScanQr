package com.example.scanqr;

public class data {

    private int mid;

    private String mcode;

    private String mtype;



    public data(int id, String code, String type) {
        mid = id;
        mcode = code;
        mtype = type;
    }


    public int getid() {
        return mid;
    }


    public String getMcode() {
        return mcode;
    }

    public String  getmtype() {
        return mtype;
    }


}
