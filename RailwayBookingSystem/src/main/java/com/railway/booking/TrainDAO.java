package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    public List<Train> getAllTrains() {
        List<Train> trains = new ArrayList<>();
        String sql = "SELECT * FROM Trains ORDER BY TrainID";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Train train = new Train();
                train.setTrainID(rs.getInt("TrainID"));
                trains.add(train);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trains;
    }
}