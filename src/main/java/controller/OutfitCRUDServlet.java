package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CapoAbbigliamento;
import model.Outfit;
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
        if (type.equals("delete")) {
            delete(req, resp);
        } else if (type.equals("update")) {
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
            System.out.println("Sono qui dopo l id"+id);

            String nome = req.getParameter("nome");

            System.out.println("Sono qui"+id+nome);
            String descrizione = req.getParameter("descrizione");
            System.out.println("prima della assegnazione capi");
            String idCapiString = req.getParameter("idCapi");

            System.out.println("id: "+id+"nome: "+nome+" descrizione: "+descrizione+" idCapiString: "+idCapiString);
            // Se idCapiString è null o vuoto, crea una lista vuota
            List<Integer> idCapi = (idCapiString == null || idCapiString.isEmpty())
                    ? List.of()
                    : Arrays.stream(idCapiString.split(","))
                    .map(String::trim) // Rimuovi eventuali spazi
                    .filter(s -> !s.isEmpty()) // Rimuovi stringhe vuote
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            OutfitDAO O = new OutfitDAO();
            Outfit outfit =O.getOutfitById(id);
            // Ottieni la lista degli ID dei capi attualmente presenti nell'outfit
            List<Integer> idCapiAttuali = outfit.getListaCapi()
                    .stream()
                    .map(CapoAbbigliamento::getId) // Supponiamo che CapoAbbigliamento abbia un metodo getId()
                    .collect(Collectors.toList());

            // Trova gli ID che erano nell'outfit ma non sono più nella richiesta
            List<Integer> idCapiDaRimuovere = idCapiAttuali.stream()
                    .filter(idCapo -> !idCapi.contains(idCapo))
                    .collect(Collectors.toList());

            // Rimuovi i capi non più presenti uno per volta usando il metodo esistente
            for (int idCapo : idCapiDaRimuovere) {
                O.rimuoviCapDaOutfit(id, idCapo);
            }

            outfit.setDescrizione(descrizione);
            outfit.setNome(nome);
            O.updateOutfit(outfit);




            resp.sendRedirect("ToOutfitServlet"); // Redirect alla pagina degli outfit dopo l'aggiornamento
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
