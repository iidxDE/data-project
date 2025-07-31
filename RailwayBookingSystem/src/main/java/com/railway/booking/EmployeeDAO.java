package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public Employee validateLogin(String username, String password) {
        String sql = "SELECT * FROM Employees WHERE Username = ? AND Password = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllCustomerReps() {
        List<Employee> reps = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE Role = 'CustomerRep'";
        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reps.add(extractEmployeeFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reps;
    }

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (SSN, FirstName, LastName, Username, Password, Role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getSsn());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getUsername());
            stmt.setString(5, employee.getPassword());
            stmt.setString(6, "CustomerRep");
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Employee getEmployeeById(int employeeId) {
        String sql = "SELECT * FROM Employees WHERE EmployeeID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractEmployeeFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE Employees SET SSN = ?, FirstName = ?, LastName = ?, Username = ?, Password = ? WHERE EmployeeID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getSsn());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getUsername());
            stmt.setString(5, employee.getPassword());
            stmt.setInt(6, employee.getEmployeeID());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeID(rs.getInt("EmployeeID"));
        employee.setFirstName(rs.getString("FirstName"));
        employee.setLastName(rs.getString("LastName"));
        employee.setUsername(rs.getString("Username"));
        employee.setRole(rs.getString("Role"));
        employee.setSsn(rs.getString("SSN"));
        return employee;
    }
}