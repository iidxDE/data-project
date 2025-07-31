package com.railway.booking;

import java.time.LocalDateTime;

public class Schedule {
    private int scheduleID;
    private int trainID;
    private String lineName;
    private String originStationName;
    private String destinationStationName;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private int travelTimeMinutes;
    private int originStationID;
    private int destinationStationID;
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
	public int getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}
	public int getTrainID() {
		return trainID;
	}
	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getOriginStationName() {
		return originStationName;
	}
	public void setOriginStationName(String originStationName) {
		this.originStationName = originStationName;
	}
	public String getDestinationStationName() {
		return destinationStationName;
	}
	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}
	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}
	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}
	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	public int getTravelTimeMinutes() {
		return travelTimeMinutes;
	}
	public void setTravelTimeMinutes(int travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}
}