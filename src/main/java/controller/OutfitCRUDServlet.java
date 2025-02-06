package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OutfitDAO;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/outfitCRUDServlet")
public class OutfitCRUDServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if ("delete".equals(type)) {
            delete(req, resp);
        } else if ("update".equals(type)) {
            update(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        OutfitDAO outfitDAO = new OutfitDAO();
        outfitDAO.deleteOutfit(id);
        resp.sendRedirect("outfits.jsp"); // Redirect alla pagina degli outfit dopo l'eliminazione
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String nome = req.getParameter("nome");
            String descrizione = req.getParameter("descrizione");
            String idCapiString = req.getParameter("idCapi");

            // Converte la stringa "1,3,13" in una lista di interi
            List<Integer> idCapi = Arrays.stream(idCapiString.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            OutfitDAO outfitDAO = new OutfitDAO();
            outfitDAO.updateOutfit(id, nome, descrizione, idCapi);

            resp.sendRedirect("outfits.jsp"); // Redirect alla pagina degli outfit dopo l'aggiornamento
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Errore nell'aggiornamento dell'outfit.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
