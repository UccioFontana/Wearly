package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Outfit;
import model.Utente;
import org.hibernate.annotations.DialectOverride;
import service.OutfitService;
import service.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

@WebServlet("/outfitAIServlet")
public class outfitAIServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        Utente u = (Utente) req.getSession(false).getAttribute("utente");

        System.out.println("Ho preso l'utente dalla sessione " + u.getNome());

        OutfitService outfitService = new OutfitService(new WeatherService());
        List<CapoAbbigliamento> listaCapiNonFiltrata = capoAbbigliamentoDAO.getCapoByUser(u.getId());
        List<CapoAbbigliamento> listaCapiFiltrata = new ArrayList<>();

        for(CapoAbbigliamento c : listaCapiNonFiltrata){
            if(c.getStato().equalsIgnoreCase("To Wash")){
                continue;
            }
            else listaCapiFiltrata.add(c);
        }



        List<Outfit> outfitList = outfitService.getOutfits(listaCapiFiltrata);


        // Impostare il tipo di contenuto della risposta
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Convertire la lista degli outfit in JSON
        Gson gson = new Gson();
        String json = gson.toJson(outfitList);


        // Scrivere la risposta JSON nel body della risposta
        resp.getWriter().write(json);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}