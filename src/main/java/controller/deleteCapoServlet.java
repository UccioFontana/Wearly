package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamentoDAO;

import java.io.IOException;

@WebServlet("/deleteCapoServlet")
public class deleteCapoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("idCapo"));
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        capoAbbigliamentoDAO.deleteCapo(id);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("toWardrobe");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
