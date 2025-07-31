package com.railway.booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationDAO {
    public List<Station> getAllStations() {
        List<Station> stations = new ArrayList<>();
        String sql = "SELECT * FROM Stations ORDER BY Name";
        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Station station = new Station();
                station.setStationID(rs.getInt("StationID"));
                station.setName(rs.getString("Name"));
                stations.add(station);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }
    public Station getStationById(int stationId) {
        Station station = null;
        String sql = "SELECT * FROM Stations WHERE StationID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                station = new Station();
                station.setStationID(rs.getInt("StationID"));
                station.setName(rs.getString("Name"));
                station.setCity(rs.getString("City"));
                station.setState(rs.getString("State"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return station;
    }
}