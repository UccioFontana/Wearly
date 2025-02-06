package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson; // Assicurati di avere questa libreria nel tuo classpath
import model.Utente;

@WebServlet("/retrieveCapiServlet")
public class RetrieveCapiServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = new CapoAbbigliamentoDAO();
        Utente u = (Utente) req.getSession(false).getAttribute("utente");
        List<CapoAbbigliamento> listaCapi =  capoAbbigliamentoDAO.getCapoByUser(u.getId());

        // Imposta il tipo di contenuto della risposta come JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Usa Gson per convertire la lista in JSON
        Gson gson = new Gson();
        String jsonResponse = gson.toJson(listaCapi);

        // Invia la risposta JSON al client
        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}