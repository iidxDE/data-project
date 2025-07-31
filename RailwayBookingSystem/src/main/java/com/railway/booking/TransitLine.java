package com.railway.booking;

/**
 * 模型类 (JavaBean)，对应数据库中的 TransitLines 表
 */
public class TransitLine {

    private String lineName;
    private int originStationID;
    private int destinationStationID;
    private double baseFare;

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public int getOriginStationID() {
        return originStationID;
    }

    public void setOriginStationID(int originStationID) {
        this.originStationID = originStationID;
    }

    public int getDestinationStationID() {
        return destinationStationID;
    }

    public void setDestinationStationID(int destinationStationID) {
        this.destinationStationID = destinationStationID;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }
}