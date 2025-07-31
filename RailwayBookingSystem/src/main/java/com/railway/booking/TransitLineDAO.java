package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransitLineDAO {

    public List<TransitLine> getAllTransitLines() {
        List<TransitLine> lines = new ArrayList<>();
        String sql = "SELECT * FROM TransitLines ORDER BY LineName";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TransitLine line = new TransitLine();
                line.setLineName(rs.getString("LineName"));
                line.setOriginStationID(rs.getInt("OriginStationID"));
                line.setDestinationStationID(rs.getInt("DestinationStationID"));
                line.setBaseFare(rs.getDouble("BaseFare"));
                lines.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lines;
    }
}