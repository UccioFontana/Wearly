package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        String hashPass = BCrypt.hashpw(password,BCrypt.gensalt());
        UtenteDAO U = new UtenteDAO();
        Utente user = new Utente(nome,cognome,email,hashPass);
       if( U.doSave(user)){
           resp.sendRedirect("front-end/jsp/home.jsp");
       }
       else{
           resp.getWriter().write("UTENTE PRESENTE");
       }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
