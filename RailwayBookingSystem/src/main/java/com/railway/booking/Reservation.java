package com.railway.booking;

import java.sql.Date;
import java.time.LocalDateTime;

public class Reservation {

    private int reservationID;
    private int customerID;
    private int scheduleID;
    private Date bookingDate;
    private double totalFare;
    private String originStationName;
    private String destinationStationName;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private int originStationID;
    private int destinationStationID;
    private String firstName;
    private String lastName;
    private int trainID;
    private String passengerType;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
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
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTrainID() {
        return trainID;
    }

    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }

    public String getPassengerType() {
    	return passengerType;
	}

	public void setPassengerType(String passengerType) {
		this.passengerType = passengerType;
	}
}