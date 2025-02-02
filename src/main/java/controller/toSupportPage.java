package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Utente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/toSupportPage")
public class toSupportPage extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        //check se è loggato o no
        Utente user = (Utente) req.getSession(false).getAttribute("utente");

        if(user != null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/supportPage.jsp");

            try {
                dispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            RequestDispatcher dispatcherLogin = req.getRequestDispatcher("front-end/jsp/login.jsp");
            try {
                dispatcherLogin.forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }

}
