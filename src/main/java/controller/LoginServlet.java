package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UtenteDAO U = new UtenteDAO();
        Utente u = U.getUserByEmail(email);




        if(u != null && BCrypt.checkpw(password,u.getPassword())){
            HttpSession session = req.getSession();
            session.setAttribute("utente", u);

            // Reindirizzamento alla pagina utente
            CapoAbbigliamentoDAO C= new CapoAbbigliamentoDAO();
            List<CapoAbbigliamento> list = C.getCapoByUser(u.getId());
            int contCapi =0;
            for(CapoAbbigliamento c: list)
                contCapi++;

            OutfitDAO O = new OutfitDAO();
            List<Outfit> lOut = O.getOutfitByUser(u.getId());
            int contOutfit=0;
            for(Outfit o: lOut)
                contOutfit++;


            req.setAttribute("numOutfit",contOutfit);
            req.setAttribute("numCapi",contCapi);
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/userPage.jsp");
            dispatcher.forward(req, resp);

        }
        else{
            List<Utente> adminL = U.getAdmin();
            for(Utente user :adminL){
                System.out.println(user);

            }
            for(Utente user :adminL){
                if(BCrypt.checkpw(password,user.getPassword())){

                    HttpSession session = req.getSession();
                    session.setAttribute("admin", user);

                    // Reindirizzamento alla pagina utente
                    RequestDispatcher dispatcher = req.getRequestDispatcher("adminServlet");
                    dispatcher.forward(req, resp);

                }
            }

            resp.getWriter().write("HAI SBAGLIATO");

        }




    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}