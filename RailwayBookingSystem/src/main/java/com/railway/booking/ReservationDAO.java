package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;

public class ReservationDAO {

    public boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO Reservations (CustomerID, ScheduleID, BookingDate, TotalFare, TripType, OriginStationID, DestinationStationID, PassengerType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";        
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getCustomerID());
            stmt.setInt(2, reservation.getScheduleID());
            stmt.setDate(3, reservation.getBookingDate());
            stmt.setDouble(4, reservation.getTotalFare());
            stmt.setString(5, "One-Way");
            stmt.setInt(6, reservation.getOriginStationID());
            stmt.setInt(7, reservation.getDestinationStationID());
            stmt.setString(8, reservation.getPassengerType());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.BookingDate, r.TotalFare, r.PassengerType, " +
                     "s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "r.OriginStationID, r.DestinationStationID " +
                     "FROM Reservations r " +
                     "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                     "JOIN Stations origin ON r.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON r.DestinationStationID = dest.StationID " +
                     "WHERE r.CustomerID = ? " +
                     "ORDER BY s.DepartureDateTime DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationID(rs.getInt("ReservationID"));
                res.setBookingDate(rs.getDate("BookingDate"));
                res.setTotalFare(rs.getDouble("TotalFare"));
                res.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                res.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                res.setOriginStationName(rs.getString("OriginStationName"));
                res.setDestinationStationName(rs.getString("DestinationStationName"));
                res.setOriginStationID(rs.getInt("OriginStationID"));
                res.setDestinationStationID(rs.getInt("DestinationStationID"));
                res.setPassengerType(rs.getString("PassengerType"));
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public boolean cancelReservation(int reservationId) {
        String sql = "DELETE FROM Reservations WHERE ReservationID = ?";
        
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;
        String sql = "SELECT r.ReservationID, r.ScheduleID, r.BookingDate, r.TotalFare, r.CustomerID, r.PassengerType, " +
                     "c.FirstName, c.LastName, " +
                     "s.TrainID, s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "r.OriginStationID, r.DestinationStationID " +
                     "FROM Reservations r " +
                     "JOIN Customers c ON r.CustomerID = c.CustomerID " +
                     "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                     "JOIN Stations origin ON r.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON r.DestinationStationID = dest.StationID " +
                     "WHERE r.ReservationID = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setScheduleID(rs.getInt("ScheduleID")); 
                
                reservation.setBookingDate(rs.getDate("BookingDate"));
                reservation.setTotalFare(rs.getDouble("TotalFare"));
                reservation.setCustomerID(rs.getInt("CustomerID"));
                reservation.setFirstName(rs.getString("FirstName"));
                reservation.setLastName(rs.getString("LastName"));
                reservation.setTrainID(rs.getInt("TrainID"));
                reservation.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                reservation.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                reservation.setOriginStationName(rs.getString("OriginStationName"));
                reservation.setDestinationStationName(rs.getString("DestinationStationName"));
                reservation.setOriginStationID(rs.getInt("OriginStationID"));
                reservation.setDestinationStationID(rs.getInt("DestinationStationID"));
                reservation.setPassengerType(rs.getString("PassengerType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }
    
    public List<Customer> getCustomersOnLineByDate(String lineName, java.sql.Date date) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT c.CustomerID, c.FirstName, c.LastName, c.Email, c.Username " +
                     "FROM Customers c " +
                     "JOIN Reservations r ON c.CustomerID = r.CustomerID " +
                     "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                     "WHERE s.LineName = ? AND DATE(s.DepartureDateTime) = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lineName);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                customer.setUsername(rs.getString("Username"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }
    
    public List<Reservation> searchReservations(String lineName, String customerName) {
        List<Reservation> reservations = new ArrayList<>();
        String baseSql = "SELECT r.ReservationID, r.BookingDate, r.TotalFare, r.PassengerType, " +
                         "c.FirstName, c.LastName, s.TrainID, " +
                         "s.DepartureDateTime, s.ArrivalDateTime, " +
                         "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                         "r.OriginStationID, r.DestinationStationID, r.CustomerID " +
                         "FROM Reservations r " +
                         "JOIN Customers c ON r.CustomerID = c.CustomerID " +
                         "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                         "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                         "JOIN Stations origin ON r.OriginStationID = origin.StationID " +
                         "JOIN Stations dest ON r.DestinationStationID = dest.StationID " +
                         "WHERE 1=1 ";

        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(baseSql);

        if (lineName != null && !lineName.isEmpty()) {
            sqlBuilder.append("AND tl.LineName = ? ");
            params.add(lineName);
        }
        
        if (customerName != null && !customerName.isEmpty()) {
            sqlBuilder.append("AND CONCAT(c.FirstName, ' ', c.LastName) = ? ");
            params.add(customerName);
        }
        
        sqlBuilder.append("ORDER BY r.BookingDate DESC");

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationID(rs.getInt("ReservationID"));
                res.setBookingDate(rs.getDate("BookingDate"));
                res.setTotalFare(rs.getDouble("TotalFare"));
                res.setFirstName(rs.getString("FirstName"));
                res.setLastName(rs.getString("LastName"));
                res.setTrainID(rs.getInt("TrainID"));
                res.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                res.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                res.setOriginStationName(rs.getString("OriginStationName"));
                res.setDestinationStationName(rs.getString("DestinationStationName"));
                res.setOriginStationID(rs.getInt("OriginStationID"));
                res.setDestinationStationID(rs.getInt("DestinationStationID"));
                res.setCustomerID(rs.getInt("CustomerID"));
                res.setPassengerType(rs.getString("PassengerType"));
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    
    public double getTotalRevenue(String lineName, String customerName) {
        double totalRevenue = 0.0;
        String baseSql = "SELECT SUM(r.TotalFare) " +
                         "FROM Reservations r " +
                         "JOIN Customers c ON r.CustomerID = c.CustomerID " +
                         "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                         "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                         "WHERE 1=1 ";

        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(baseSql);

        if (lineName != null && !lineName.isEmpty()) {
            sqlBuilder.append("AND tl.LineName = ? ");
            params.add(lineName);
        }
        
        if (customerName != null && !customerName.isEmpty()) {
            sqlBuilder.append("AND CONCAT(c.FirstName, ' ', c.LastName) = ? ");
            params.add(customerName);
        }

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }
    public Map<String, Double> getMonthlySalesReport() {
    	Map<String, Double> salesReport = new LinkedHashMap<>();
    	String sql = "SELECT DATE_FORMAT(BookingDate, '%Y-%m') AS month, SUM(TotalFare) AS monthly_revenue " +
    				 "FROM Reservations " +
    				 "GROUP BY month " +
    				 "ORDER BY month DESC";
    	try (Connection conn = DbUtil.getConnection();
    			PreparedStatement stmt = conn.prepareStatement(sql);
    			ResultSet rs = stmt.executeQuery()) {
    		while (rs.next()) {
    			String month = rs.getString("month");
    			double monthlyRevenue = rs.getDouble("monthly_revenue");
    			salesReport.put(month, monthlyRevenue);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return salesReport;
	}
    public Map<String, Object> getTopRevenueCustomer() {
        Map<String, Object> topCustomerData = new HashMap<>(); 
        String sql = "SELECT c.CustomerID, c.FirstName, c.LastName, SUM(r.TotalFare) AS total_revenue " +
                     "FROM Reservations r " +
                     "JOIN Customers c ON r.CustomerID = c.CustomerID " +
                     "GROUP BY c.CustomerID, c.FirstName, c.LastName " +
                     "ORDER BY total_revenue DESC " +
                     "LIMIT 1";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                
                double totalRevenue = rs.getDouble("total_revenue");
                
                topCustomerData.put("customer", customer);
                topCustomerData.put("totalRevenue", totalRevenue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topCustomerData;
    }

    public Map<String, Integer> getTopActiveTransitLines() {
    	Map<String, Integer> topLinesReport = new LinkedHashMap<>();
    	String sql = "SELECT s.LineName, COUNT(*) AS reservation_count " +
    				"FROM Reservations r " +
    				"JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
    				"GROUP BY s.LineName " +
    				"ORDER BY reservation_count DESC " +
    				"LIMIT 5";
    	try (Connection conn = DbUtil.getConnection();
    			PreparedStatement stmt = conn.prepareStatement(sql);
    			ResultSet rs = stmt.executeQuery()) {
    		while (rs.next()) {
    			String lineName = rs.getString("LineName");
    			int reservationCount = rs.getInt("reservation_count");
    			topLinesReport.put(lineName, reservationCount);
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return topLinesReport;
    }
    public List<Reservation> getCurrentReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.BookingDate, r.TotalFare, r.PassengerType, " +
                     "s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "r.OriginStationID, r.DestinationStationID " +
                     "FROM Reservations r " +
                     "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                     "JOIN Stations origin ON r.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON r.DestinationStationID = dest.StationID " +
                     "WHERE r.CustomerID = ? AND s.DepartureDateTime >= NOW() " +
                     "ORDER BY s.DepartureDateTime ASC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationID(rs.getInt("ReservationID"));
                res.setBookingDate(rs.getDate("BookingDate"));
                res.setTotalFare(rs.getDouble("TotalFare"));
                res.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                res.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                res.setOriginStationName(rs.getString("OriginStationName"));
                res.setDestinationStationName(rs.getString("DestinationStationName"));
                res.setOriginStationID(rs.getInt("OriginStationID"));
                res.setDestinationStationID(rs.getInt("DestinationStationID"));
                res.setPassengerType(rs.getString("PassengerType"));
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
    public List<Reservation> getPastReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.ReservationID, r.BookingDate, r.TotalFare, r.PassengerType, " +
                     "s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "r.OriginStationID, r.DestinationStationID " +
                     "FROM Reservations r " +
                     "JOIN Schedules s ON r.ScheduleID = s.ScheduleID " +
                     "JOIN Stations origin ON r.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON r.DestinationStationID = dest.StationID " +
                     "WHERE r.CustomerID = ? AND s.DepartureDateTime < NOW() " +
                     "ORDER BY s.DepartureDateTime DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationID(rs.getInt("ReservationID"));
                res.setBookingDate(rs.getDate("BookingDate"));
                res.setTotalFare(rs.getDouble("TotalFare"));
                res.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                res.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                res.setOriginStationName(rs.getString("OriginStationName"));
                res.setDestinationStationName(rs.getString("DestinationStationName"));
                res.setOriginStationID(rs.getInt("OriginStationID"));
                res.setDestinationStationID(rs.getInt("DestinationStationID"));
                res.setPassengerType(rs.getString("PassengerType"));
                reservations.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}