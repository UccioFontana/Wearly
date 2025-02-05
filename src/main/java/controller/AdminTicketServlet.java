package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TicketDAO;
import model.Utente;

import java.io.IOException;

@WebServlet("/adminTicketServlet")

public class AdminTicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if(type.equals("claim")){
            claim(req,resp);
        }
        else if(type.equals("close")){
            close(req,resp);
        }
    }

    private void claim(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int idT= Integer.parseInt(req.getParameter("idT"));
        Utente admin = (Utente) req.getSession(false).getAttribute("admin");

        TicketDAO T = new TicketDAO();
        T.assegnaTicket(admin.getId(),idT);
        RequestDispatcher dispatcher = req.getRequestDispatcher("adminServlet");
        dispatcher.forward(req, resp);
    }

    private void close(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        int idT= Integer.parseInt(req.getParameter("idT"));
        TicketDAO T = new TicketDAO();
        T.deleteTicket(idT);
        RequestDispatcher dispatcher = req.getRequestDispatcher("adminServlet");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
