package com.railway.booking;

/**
 * 这是一个模型类 (JavaBean)，用于表示数据库中 Stations 表的一行数据。
 */
public class Station {

    private int stationID;
    private String name;
    private String city;
    private String state;

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}