package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/toWardrobe")
public class ToWardrobeServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        //check se è loggato o no

        RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/wardrobe.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
