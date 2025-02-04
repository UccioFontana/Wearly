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

import java.io.IOException;

@WebServlet("/ticketServlet")
public class TicketServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        Utente u = (Utente) req.getSession(false).getAttribute("utente");
        System.out.println(u);

        if(u != null){
            String oggetto = req.getParameter("ticketObject");
            String problema = req.getParameter("ticketIssue");

            Ticket ticket = new Ticket(u.getId(), oggetto, problema);
            TicketDAO T = new TicketDAO();
            T.doSave(ticket);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("toSupportPage");
            try {
                requestDispatcher.forward(req, resp);
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
