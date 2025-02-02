package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/authservlet")
public class AuthenticationServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession(false).getAttribute("utente") != null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/userPage.jsp");
            dispatcher.forward(req, resp);
        }

        else{
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/login.jsp");
            dispatcher.forward(req, resp);
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
