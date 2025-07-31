package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StopDAO {

    public List<StopDetail> getStopsForSchedule(int scheduleId) {
        List<StopDetail> stopDetails = new ArrayList<>();
        String sql = "SELECT st.Name, s.DepartureDateTime, stp.ArrivalTime, stp.DepartureTime " +
                     "FROM Schedules s " +
                     "JOIN Stops stp ON s.LineName = stp.LineName " +
                     "JOIN Stations st ON stp.StationID = st.StationID " +
                     "WHERE s.ScheduleID = ? " +
                     "ORDER BY stp.StopOrder ASC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, scheduleId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                StopDetail detail = new StopDetail();
                detail.setStationName(rs.getString("Name"));
                
                LocalDateTime scheduleStartDateTime = rs.getTimestamp("DepartureDateTime").toLocalDateTime();
                Time arrivalOffset = rs.getTime("ArrivalTime");
                Time departureOffset = rs.getTime("DepartureTime");

                if (arrivalOffset != null) {
                    detail.setArrivalTime(scheduleStartDateTime.toLocalDate().atTime(arrivalOffset.toLocalTime()));
                }
                if (departureOffset != null) {
                    detail.setDepartureTime(scheduleStartDateTime.toLocalDate().atTime(departureOffset.toLocalTime()));
                }
                
                stopDetails.add(detail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stopDetails;
    }
}