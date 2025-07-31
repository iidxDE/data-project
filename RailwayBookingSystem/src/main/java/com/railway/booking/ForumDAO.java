package com.railway.booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ForumDAO {

    public boolean askQuestion(Question question) {
        String sql = "INSERT INTO Questions (CustomerID, Title, Body, QuestionDate, Status) VALUES (?, ?, ?, ?, 'Unanswered')";
        
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, question.getCustomerID());
            stmt.setString(2, question.getTitle());
            stmt.setString(3, question.getBody());
            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Question> getQuestionsByCustomerId(int customerId, String keywords) {
        List<Question> questions = new ArrayList<>();
        String baseSql = "SELECT * FROM Questions WHERE CustomerID = ? ";
        
        List<Object> params = new ArrayList<>();
        params.add(customerId);
        
        StringBuilder sqlBuilder = new StringBuilder(baseSql);

        if (keywords != null && !keywords.isEmpty()) {
            sqlBuilder.append("AND (Title LIKE ? OR Body LIKE ?) ");
            String searchPattern = "%" + keywords + "%";
            params.add(searchPattern);
            params.add(searchPattern);
        }
        
        sqlBuilder.append("ORDER BY QuestionDate DESC");

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Question question = new Question();
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setCustomerID(rs.getInt("CustomerID"));
                question.setTitle(rs.getString("Title"));
                question.setBody(rs.getString("Body"));
                question.setQuestionDate(rs.getTimestamp("QuestionDate").toLocalDateTime());
                question.setStatus(rs.getString("Status"));
                question.setAnswers(getAnswersByQuestionId(question.getQuestionID()));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
	public List<Answer> getAnswersByQuestionId(int questionId) {
		List<Answer> answers = new ArrayList<>();
		String sql = "SELECT a.*, e.FirstName, e.LastName FROM Answers a " +
					 "JOIN Employees e ON a.EmployeeID = e.EmployeeID " +
					 "WHERE a.QuestionID = ? ORDER BY a.AnswerDate ASC";
		try (Connection conn = DbUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, questionId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setAnswerID(rs.getInt("AnswerID"));
				answer.setQuestionID(rs.getInt("QuestionID"));
				answer.setEmployeeID(rs.getInt("EmployeeID"));
				answer.setBody(rs.getString("Body"));
				answer.setAnswerDate(rs.getTimestamp("AnswerDate").toLocalDateTime());
				answer.setEmployeeFirstName(rs.getString("FirstName"));
				answer.setEmployeeLastName(rs.getString("LastName"));
				answers.add(answer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answers;
	}
	public List<Question> getAllUnansweredQuestions() {
	    List<Question> questions = new ArrayList<>();
	    String sql = "SELECT q.*, c.FirstName, c.LastName FROM Questions q " +
	                 "JOIN Customers c ON q.CustomerID = c.CustomerID " +
	                 "WHERE q.Status = 'Unanswered' ORDER BY q.QuestionDate ASC";
	                 
	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            Question question = new Question();
	            question.setQuestionID(rs.getInt("QuestionID"));
	            question.setCustomerID(rs.getInt("CustomerID"));
	            question.setTitle(rs.getString("Title"));
	            question.setBody(rs.getString("Body"));
	            question.setQuestionDate(rs.getTimestamp("QuestionDate").toLocalDateTime());
	            question.setStatus(rs.getString("Status"));
	            question.setFirstName(rs.getString("FirstName"));
	            question.setLastName(rs.getString("LastName"));
	            
	            questions.add(question);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return questions;
	}
	public boolean postAnswer(Answer answer) {
	    String sql = "INSERT INTO Answers (QuestionID, EmployeeID, Body, AnswerDate) VALUES (?, ?, ?, ?)";
	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, answer.getQuestionID());
	        stmt.setInt(2, answer.getEmployeeID());
	        stmt.setString(3, answer.getBody());
	        stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean updateQuestionStatus(int questionId, String newStatus) {
	    String sql = "UPDATE Questions SET Status = ? WHERE QuestionID = ?";
	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, newStatus);
	        stmt.setInt(2, questionId);
	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public Question getQuestionById(int questionId) {
	    Question question = null;
	    String sql = "SELECT q.*, c.FirstName, c.LastName FROM Questions q " +
	                 "JOIN Customers c ON q.CustomerID = c.CustomerID " +
	                 "WHERE q.QuestionID = ?";

	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, questionId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            question = new Question();
	            question.setQuestionID(rs.getInt("QuestionID"));
	            question.setCustomerID(rs.getInt("CustomerID"));
	            question.setTitle(rs.getString("Title"));
	            question.setBody(rs.getString("Body"));
	            question.setQuestionDate(rs.getTimestamp("QuestionDate").toLocalDateTime());
	            question.setStatus(rs.getString("Status"));
	            question.setFirstName(rs.getString("FirstName"));
	            question.setLastName(rs.getString("LastName"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return question;
	}
}