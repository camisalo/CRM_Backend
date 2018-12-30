package com.patterns.crm.api;





import java.sql.Timestamp;


public class Account implements IRecords {
    private int accountid;
    private String name;
    private String address;
    private String phone;
    private Timestamp date;

    public Account(){}

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp date) { this.date = date; }
}