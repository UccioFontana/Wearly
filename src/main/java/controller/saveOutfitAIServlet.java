package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Outfit;
import model.OutfitDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/saveOutfitAIServlet")
public class saveOutfitAIServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        int id1 = Integer.parseInt(req.getParameter("id1"));
        int id2 = Integer.parseInt(req.getParameter("id2"));
        int id3 = Integer.parseInt(req.getParameter("id3"));
        String name = req.getParameter("name");
        String desc = req.getParameter("desc");

        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        List<CapoAbbigliamento> listaCapi = new ArrayList<>();

        listaCapi.add(capoAbbigliamentoDAO.getCapoById(id1));
        listaCapi.add(capoAbbigliamentoDAO.getCapoById(id2));
        listaCapi.add(capoAbbigliamentoDAO.getCapoById(id3));


        OutfitDAO outfitDAO = new OutfitDAO();
        Outfit outfit = new Outfit(name, desc);
        outfit.setListaCapi(listaCapi);
        outfitDAO.doSave(outfit);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("ToOutfitServlet");
        try {
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
