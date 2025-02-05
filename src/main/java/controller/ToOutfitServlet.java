package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Outfit;
import model.OutfitDAO;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/ToOutfitServlet")
public class ToOutfitServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        Utente u = (Utente) req.getSession(false).getAttribute("utente");
        OutfitDAO outfitDAO = new OutfitDAO();
        if(u != null){

            List<Outfit> listaOutfit =  outfitDAO.getOutfitByUser(u.getId());
            req.setAttribute("listaOutfit", listaOutfit);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/jsp/outfitPage.jsp");
            try {
                dispatcher.forward(req, resp);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }

        }



    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
