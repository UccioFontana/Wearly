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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("idCapo"));
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        capoAbbigliamentoDAO.deleteCapo(id);
        boolean success = true;

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (success) {
            resp.getWriter().write("{\"success\": true}");
        } else {
            resp.getWriter().write("{\"success\": false}");
        }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
