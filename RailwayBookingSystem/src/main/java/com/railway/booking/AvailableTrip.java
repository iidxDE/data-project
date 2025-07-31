package com.railway.booking;

import java.time.LocalDateTime;

public class AvailableTrip {

    private int scheduleID;
    private int trainID;
    private String lineName;
    
    private int originStationID;
    private String originStationName;
    private LocalDateTime departureTime;
    
    private int destinationStationID;
    private String destinationStationName;
    private LocalDateTime arrivalTime;
    
    private double calculatedFare;

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

	public int getOriginStationID() {
		return originStationID;
	}

	public void setOriginStationID(int originStationID) {
		this.originStationID = originStationID;
	}

	public String getOriginStationName() {
		return originStationName;
	}

	public void setOriginStationName(String originStationName) {
		this.originStationName = originStationName;
	}

	public LocalDateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}

	public int getDestinationStationID() {
		return destinationStationID;
	}

	public void setDestinationStationID(int destinationStationID) {
		this.destinationStationID = destinationStationID;
	}

	public String getDestinationStationName() {
		return destinationStationName;
	}

	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}

	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public double getCalculatedFare() {
		return calculatedFare;
	}

	public void setCalculatedFare(double calculatedFare) {
		this.calculatedFare = calculatedFare;
	}
}