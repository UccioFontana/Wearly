package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/filterCapiServlet")
public class filterCapiServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        String filtro = req.getParameter("filtro"); // Ottieni il filtro dalla richiesta AJAX

        List<CapoAbbigliamento> listaFiltrata = null;

        if(filtro.equals("None")){
            CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
           listaFiltrata = capoAbbigliamentoDAO.getCapi(); // Metodo per ottenere la lista filtrata

        }
        else{
            CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
            listaFiltrata = capoAbbigliamentoDAO.getFilteredCapi(filtro); // Metodo per ottenere la lista filtrata

        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // Converte la lista in JSON e invia la risposta
        Gson gson = new Gson();
        String json = gson.toJson(listaFiltrata);

        try {
            resp.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
