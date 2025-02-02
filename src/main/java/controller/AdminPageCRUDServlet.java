package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.UtenteDAO;

import java.io.IOException;

@WebServlet("/adminPageCRUDServlet")
public class AdminPageCRUDServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
            if(type.equalsIgnoreCase("delete"))
                delete(req,resp);
            else if(type.equalsIgnoreCase("add"))
                add(req,resp);
            else if(type.equalsIgnoreCase("update"))
                update(req,resp);



    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idUtente = Integer.parseInt(req.getParameter("idUtente"));
        UtenteDAO U = new UtenteDAO();
        U.deleteUser(idUtente);

    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
