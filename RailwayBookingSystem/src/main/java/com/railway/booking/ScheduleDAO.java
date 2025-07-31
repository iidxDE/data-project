package com.railway.booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleDAO {
    public List<Schedule> getAllSchedules(String sortBy) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT s.ScheduleID, s.TrainID, s.LineName, s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "tl.OriginStationID, tl.DestinationStationID " +
                     "FROM Schedules s " +
                     "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                     "JOIN Stations origin ON tl.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON tl.DestinationStationID = dest.StationID ";

        String orderByClause = "ORDER BY s.DepartureDateTime";
        if ("arrival".equalsIgnoreCase(sortBy)) {
            orderByClause = "ORDER BY s.ArrivalDateTime";
        }
        sql += orderByClause;

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setTrainID(rs.getInt("TrainID"));
                schedule.setLineName(rs.getString("LineName"));
                schedule.setOriginStationName(rs.getString("OriginStationName"));
                schedule.setDestinationStationName(rs.getString("DestinationStationName"));
                schedule.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                schedule.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                schedule.setOriginStationID(rs.getInt("OriginStationID"));
                schedule.setDestinationStationID(rs.getInt("DestinationStationID"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
    public List<Schedule> searchSchedules(String originId, String destinationId, String date, String sortBy) {
        List<Schedule> schedules = new ArrayList<>();
        String baseSql = "SELECT s.ScheduleID, s.TrainID, s.LineName, s.DepartureDateTime, s.ArrivalDateTime, " +
                         "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                         "tl.OriginStationID, tl.DestinationStationID " +
                         "FROM Schedules s " +
                         "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                         "JOIN Stations origin ON tl.OriginStationID = origin.StationID " +
                         "JOIN Stations dest ON tl.DestinationStationID = dest.StationID " +
                         "WHERE 1=1 ";

        List<Object> params = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder(baseSql);

        if (originId != null && !originId.isEmpty()) {
            sqlBuilder.append("AND tl.OriginStationID = ? ");
            params.add(Integer.parseInt(originId));
        }
        if (destinationId != null && !destinationId.isEmpty()) {
            sqlBuilder.append("AND tl.DestinationStationID = ? ");
            params.add(Integer.parseInt(destinationId));
        }
        if (date != null && !date.isEmpty()) {
            sqlBuilder.append("AND DATE(s.DepartureDateTime) = ? ");
            params.add(date);
        }

        String orderByClause = " ORDER BY s.DepartureDateTime";
        if ("arrival".equalsIgnoreCase(sortBy)) {
            orderByClause = " ORDER BY s.ArrivalDateTime";
        }
        sqlBuilder.append(orderByClause);

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setTrainID(rs.getInt("TrainID"));
                schedule.setLineName(rs.getString("LineName"));
                schedule.setOriginStationName(rs.getString("OriginStationName"));
                schedule.setDestinationStationName(rs.getString("DestinationStationName"));
                schedule.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                schedule.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                schedule.setOriginStationID(rs.getInt("OriginStationID"));
                schedule.setDestinationStationID(rs.getInt("DestinationStationID"));
                schedules.add(schedule);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public Schedule getScheduleById(int scheduleId) {
        Schedule schedule = null;
        String sql = "SELECT s.ScheduleID, s.TrainID, s.LineName, s.DepartureDateTime, s.ArrivalDateTime, " +
                     "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                     "tl.OriginStationID, tl.DestinationStationID " +
                     "FROM Schedules s " +
                     "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                     "JOIN Stations origin ON tl.OriginStationID = origin.StationID " +
                     "JOIN Stations dest ON tl.DestinationStationID = dest.StationID " +
                     "WHERE s.ScheduleID = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, scheduleId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                schedule = new Schedule();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setTrainID(rs.getInt("TrainID"));
                schedule.setLineName(rs.getString("LineName"));
                schedule.setOriginStationName(rs.getString("OriginStationName"));
                schedule.setDestinationStationName(rs.getString("DestinationStationName"));
                schedule.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                schedule.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                schedule.setOriginStationID(rs.getInt("OriginStationID"));
                schedule.setDestinationStationID(rs.getInt("DestinationStationID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public boolean addSchedule(Schedule schedule) {
        String sql = "INSERT INTO Schedules (TrainID, LineName, DepartureDateTime, ArrivalDateTime, TravelTimeMinutes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, schedule.getTrainID());
            stmt.setString(2, schedule.getLineName());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(schedule.getDepartureDateTime()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getArrivalDateTime()));
            stmt.setInt(5, schedule.getTravelTimeMinutes());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateSchedule(Schedule schedule) {
        String sql = "UPDATE Schedules SET TrainID = ?, LineName = ?, DepartureDateTime = ?, ArrivalDateTime = ?, TravelTimeMinutes = ? WHERE ScheduleID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, schedule.getTrainID());
            stmt.setString(2, schedule.getLineName());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(schedule.getDepartureDateTime()));
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getArrivalDateTime()));
            stmt.setInt(5, schedule.getTravelTimeMinutes());
            stmt.setInt(6, schedule.getScheduleID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSchedule(int scheduleId) {
        String sql = "DELETE FROM Schedules WHERE ScheduleID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, scheduleId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Schedule> getSchedulesByStation(int stationId, String type) {
        List<Schedule> schedules = new ArrayList<>();
        String baseSql = "SELECT s.ScheduleID, s.TrainID, s.LineName, s.DepartureDateTime, s.ArrivalDateTime, " +
                         "origin.Name AS OriginStationName, dest.Name AS DestinationStationName, " +
                         "tl.OriginStationID, tl.DestinationStationID " +
                         "FROM Schedules s " +
                         "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                         "JOIN Stations origin ON tl.OriginStationID = origin.StationID " +
                         "JOIN Stations dest ON tl.DestinationStationID = dest.StationID ";

        String finalSql;
        if ("origin".equalsIgnoreCase(type)) {
            finalSql = baseSql + "WHERE tl.OriginStationID = ? ORDER BY s.DepartureDateTime";
        } else if ("destination".equalsIgnoreCase(type)) {
            finalSql = baseSql + "WHERE tl.DestinationStationID = ? ORDER BY s.DepartureDateTime";
        } else {
            return schedules;
        }

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(finalSql)) {
            
            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setScheduleID(rs.getInt("ScheduleID"));
                schedule.setTrainID(rs.getInt("TrainID"));
                schedule.setLineName(rs.getString("LineName"));
                schedule.setDepartureDateTime(rs.getTimestamp("DepartureDateTime").toLocalDateTime());
                schedule.setArrivalDateTime(rs.getTimestamp("ArrivalDateTime").toLocalDateTime());
                schedule.setOriginStationName(rs.getString("OriginStationName"));
                schedule.setDestinationStationName(rs.getString("DestinationStationName"));
                schedule.setOriginStationID(rs.getInt("OriginStationID"));
                schedule.setDestinationStationID(rs.getInt("DestinationStationID"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    private StationDAO stationDAO = new StationDAO();
 private Map<String, Object> getStopDetails(String lineName, int stationId) {
     Map<String, Object> details = new HashMap<>();
     String sql = "SELECT StopOrder, ArrivalTime, DepartureTime FROM Stops WHERE LineName = ? AND StationID = ?";
     try (Connection conn = DbUtil.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {
         stmt.setString(1, lineName);
         stmt.setInt(2, stationId);
         ResultSet rs = stmt.executeQuery();
         if (rs.next()) {
             details.put("order", rs.getInt("StopOrder"));
             details.put("arrival", rs.getTime("ArrivalTime"));
             details.put("departure", rs.getTime("DepartureTime"));
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     return details;
 }

 public List<AvailableTrip> searchAvailableTrips(String originIdStr, String destinationIdStr, String dateStr) {
     List<AvailableTrip> trips = new ArrayList<>();

     String baseSql = "SELECT s.ScheduleID, s.TrainID, s.LineName, tl.BaseFare, " +
                      "   tl.OriginStationID AS DefaultOriginID, tl.DestinationStationID AS DefaultDestinationID, " +
                      "   origin_stat.Name AS DefaultOriginName, dest_stat.Name AS DefaultDestinationName, " +
                      "   s.DepartureDateTime, s.ArrivalDateTime, " +
                      "   (SELECT COUNT(*) FROM Stops WHERE LineName = tl.LineName) AS total_stops " +
                      "FROM Schedules s " +
                      "JOIN TransitLines tl ON s.LineName = tl.LineName " +
                      "JOIN Stations origin_stat ON tl.OriginStationID = origin_stat.StationID " +
                      "JOIN Stations dest_stat ON tl.DestinationStationID = dest_stat.StationID " +
                      "WHERE s.DepartureDateTime >= CURDATE() ";

     List<Object> params = new ArrayList<>();
     StringBuilder sqlBuilder = new StringBuilder(baseSql);

     if (dateStr != null && !dateStr.isEmpty()) {
         sqlBuilder.append("AND DATE(s.DepartureDateTime) = ? ");
         params.add(dateStr);
     }
     if (originIdStr != null && !originIdStr.isEmpty()) {
         sqlBuilder.append("AND EXISTS (SELECT 1 FROM Stops st WHERE st.LineName = s.LineName AND st.StationID = ?) ");
         params.add(Integer.parseInt(originIdStr));
     }
     if (destinationIdStr != null && !destinationIdStr.isEmpty()) {
         sqlBuilder.append("AND EXISTS (SELECT 1 FROM Stops st WHERE st.LineName = s.LineName AND st.StationID = ?) ");
         params.add(Integer.parseInt(destinationIdStr));
     }

     try (Connection conn = DbUtil.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
         
         for (int i = 0; i < params.size(); i++) {
             stmt.setObject(i + 1, params.get(i));
         }
         
         ResultSet rs = stmt.executeQuery();

         while (rs.next()) {
             AvailableTrip trip = new AvailableTrip();
             String lineName = rs.getString("LineName");
             
             int originId = (originIdStr != null && !originIdStr.isEmpty()) ? Integer.parseInt(originIdStr) : rs.getInt("DefaultOriginID");
             int destinationId = (destinationIdStr != null && !destinationIdStr.isEmpty()) ? Integer.parseInt(destinationIdStr) : rs.getInt("DefaultDestinationID");

             Map<String, Object> originStopDetails = getStopDetails(lineName, originId);
             Map<String, Object> destStopDetails = getStopDetails(lineName, destinationId);

             if (originStopDetails.isEmpty() || destStopDetails.isEmpty() || (int)originStopDetails.get("order") >= (int)destStopDetails.get("order")) {
                 continue;
             }

             trip.setScheduleID(rs.getInt("ScheduleID"));
             trip.setTrainID(rs.getInt("TrainID"));
             trip.setLineName(lineName);
             
             trip.setOriginStationID(originId);
             trip.setDestinationStationID(destinationId);
             trip.setOriginStationName((originIdStr != null && !originIdStr.isEmpty()) ? stationDAO.getStationById(originId).getName() : rs.getString("DefaultOriginName"));
             trip.setDestinationStationName((destinationIdStr != null && !destinationIdStr.isEmpty()) ? stationDAO.getStationById(destinationId).getName() : rs.getString("DefaultDestinationName"));

             LocalDateTime scheduleStart = rs.getTimestamp("DepartureDateTime").toLocalDateTime();
             Time originDepartureOffset = (Time) originStopDetails.get("departure");
             Time destArrivalOffset = (Time) destStopDetails.get("arrival");
             trip.setDepartureTime(scheduleStart.toLocalDate().atTime(originDepartureOffset.toLocalTime()));
             trip.setArrivalTime(scheduleStart.toLocalDate().atTime(destArrivalOffset.toLocalTime()));
             
             double baseFare = rs.getDouble("BaseFare");
             int totalStops = rs.getInt("total_stops");
             int numSegments = (int)destStopDetails.get("order") - (int)originStopDetails.get("order");
             if (totalStops > 1) {
                 double farePerSegment = baseFare / (totalStops - 1);
                 trip.setCalculatedFare(farePerSegment * numSegments);
             } else {
                 trip.setCalculatedFare(baseFare);
             }
             
             trips.add(trip);
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
     return trips;
 }

 public AvailableTrip getAvailableTripDetails(int scheduleId, int originId, int destinationId) {

	 String dateStr = null;

	 String originIdStr = String.valueOf(originId);
	 String destinationIdStr = String.valueOf(destinationId);
  
	 List<AvailableTrip> trips = searchAvailableTrips(originIdStr, destinationIdStr, null);

	 for (AvailableTrip trip : trips) {
		 if (trip.getScheduleID() == scheduleId) {
			 return trip;
		 }
	 }
	 return null;
 }
}