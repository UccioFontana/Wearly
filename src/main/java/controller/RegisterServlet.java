package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AuthenticationFacade;
import model.Utente;
import model.UtenteDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nome= req.getParameter("name");
        String cognome = req.getParameter("surname");
        String email = req.getParameter("email");
        String password= req.getParameter("password");

        AuthenticationFacade authFacade = new AuthenticationFacade(req.getSession(false));
        boolean checkUser = authFacade.signup(nome, cognome, email, password);


       if(checkUser){
           resp.sendRedirect("home.jsp");
       }
       else{
           resp.getWriter().write("UTENTE PRESENTE NEL SISTEMA");
       }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
