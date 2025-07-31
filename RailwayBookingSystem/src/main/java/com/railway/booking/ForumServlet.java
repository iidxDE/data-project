package com.railway.booking;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/forum")
public class ForumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ForumDAO forumDAO;

    public void init() {
        forumDAO = new ForumDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");
        if (action == null) {
            if ("customer".equals(role)) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendRedirect("rep_dashboard.jsp");
            }
            return;
        }

        try {
            switch(action) {
                case "myQuestions":
                    if ("customer".equals(role)) {
                        showMyQuestions(request, response, session);
                    }
                    break;
                case "cr_view":
                    if (isEmployee(role)) {
                        showCrForumView(request, response, session);
                    }
                    break;
                case "view_thread":
                    viewThread(request, response, session);
                    break;
                default:
                     if ("customer".equals(role)) {
                        response.sendRedirect("success.jsp");
                    } else {
                        response.sendRedirect("rep_dashboard.jsp");
                    }
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String role = (String) session.getAttribute("role");
        String action = request.getParameter("action");

        if ("ask".equals(action) && "customer".equals(role)) {
            askQuestion(request, response, session);
        } else if ("reply".equals(action) && isEmployee(role)) {
            postReply(request, response, session);
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private boolean isEmployee(String role) {
        return "Manager".equalsIgnoreCase(role) || "CustomerRep".equalsIgnoreCase(role);
    }

    private void showMyQuestions(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        Customer customer = (Customer) session.getAttribute("user");
        String keywords = request.getParameter("keywords");
        
        ForumDAO forumDAO = new ForumDAO();
        List<Question> questionsList = forumDAO.getQuestionsByCustomerId(customer.getCustomerID(), keywords);
        
        request.setAttribute("questionsList", questionsList);
        request.getRequestDispatcher("myQuestions.jsp").forward(request, response);
    }

    private void askQuestion(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Customer customer = (Customer) session.getAttribute("user");
        Question question = new Question();
        question.setCustomerID(customer.getCustomerID());
        question.setTitle(request.getParameter("title"));
        question.setBody(request.getParameter("body"));
        
        if (forumDAO.askQuestion(question)) {
            session.setAttribute("message", "Your question has been submitted successfully!");
        } else {
            session.setAttribute("message", "Error: Could not submit your question.");
        }
        response.sendRedirect("forum?action=myQuestions");
    }
    
    private void showCrForumView(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        List<Question> questionsList = forumDAO.getAllUnansweredQuestions();
        request.setAttribute("questionsList", questionsList);
        request.getRequestDispatcher("cr_forum.jsp").forward(request, response);
    }
    
    private void viewThread(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        int questionId = Integer.parseInt(request.getParameter("id"));
        Question question = forumDAO.getQuestionById(questionId);
        List<Answer> answersList = forumDAO.getAnswersByQuestionId(questionId);

        request.setAttribute("question", question);
        request.setAttribute("answersList", answersList);
        String role = (String) session.getAttribute("role");
        if (isEmployee(role)) {
            request.getRequestDispatcher("reply.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("myQuestions.jsp").forward(request, response);
        }
    }

    private void postReply(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Employee employee = (Employee) session.getAttribute("user");
        int questionId = Integer.parseInt(request.getParameter("questionId"));
        String body = request.getParameter("body");

        Answer answer = new Answer();
        answer.setQuestionID(questionId);
        answer.setEmployeeID(employee.getEmployeeID());
        answer.setBody(body);

        if (forumDAO.postAnswer(answer)) {
            forumDAO.updateQuestionStatus(questionId, "Answered");
            session.setAttribute("message", "Reply posted successfully!");
        } else {
            session.setAttribute("message", "Error: Could not post reply.");
        }
        response.sendRedirect("forum?action=cr_view");
    }
}