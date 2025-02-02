package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/authservlet")
public class AuthenticationServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Utente u = (Utente) req.getSession(false).getAttribute("utente");
        if( u!= null){
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
            RequestDispatcher dispatcher = req.getRequestDispatcher("front-end/jsp/login.jsp");
            dispatcher.forward(req, resp);
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
