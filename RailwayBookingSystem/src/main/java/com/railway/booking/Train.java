package com.railway.booking;

/**
 * 模型类 (JavaBean)，对应数据库中的 Trains 表
 */
public class Train {

    private int trainID;
    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }
}