package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ticket;
import model.TicketDAO;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/adminServlet")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Utente admin = (Utente) req.getSession(false).getAttribute("admin");
        UtenteDAO U = new UtenteDAO();
        List<Utente> user = U.getUsers();
        req.setAttribute("users",user);

        TicketDAO T = new TicketDAO();
        List<Ticket> listTicket = T.getTicketNotClaimed();
        req.setAttribute("ticket",listTicket);

        List<Ticket> ticketAdmin= T.getTicketByAdmin(admin.getId());
        req.setAttribute("ticketAdmin",ticketAdmin);

        RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/adminPage.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
