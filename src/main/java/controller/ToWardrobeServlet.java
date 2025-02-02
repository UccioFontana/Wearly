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

@WebServlet("/toWardrobe")
public class ToWardrobeServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        //check se è loggato o no
        Utente user = (Utente) req.getSession(false).getAttribute("utente");

        if(user != null){
            CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
            List<CapoAbbigliamento> list = C.getCapoByUser(user.getId());
            req.setAttribute("listaCapi",list);
        }
        else{
            req.setAttribute("listaCapi",new ArrayList<>());
        }

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
