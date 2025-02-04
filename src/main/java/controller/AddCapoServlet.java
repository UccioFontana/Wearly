package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Utente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
@WebServlet("/addCapoServlet")
@MultipartConfig
public class AddCapoServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/Users/pietro/apache-tomcat-10.1.19/webapps/img";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Utente utente = (Utente) request.getSession(false).getAttribute("utente");
        int idUtente = 0;
        if (utente == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("front-end/jsp/login.jsp");
            dispatcher.forward(request, resp);
            return;
        } else {
            idUtente = utente.getId();
        }

        // Recupera i parametri dal form
        String nome = request.getParameter("name");
        String descrizione = request.getParameter("description");
        String materiale = request.getParameter("material");
        String colore = request.getParameter("color");
        String stile = request.getParameter("style");
        String categoria = request.getParameter("category");
        String parteDelCorpo = request.getParameter("bodyPart");

        Part filePart = request.getPart("image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Creazione cartella se non esiste
        File uploads = new File(UPLOAD_DIRECTORY);
        if (!uploads.exists()) {
            uploads.mkdirs();
        }

        String filePath = UPLOAD_DIRECTORY + File.separator + fileName;
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(filePart.getInputStream().readAllBytes());
        }

        // URL per accedere al file
        String fileUrl = "/img/" + fileName;

        CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
        CapoAbbigliamento capo = new CapoAbbigliamento(idUtente, nome, descrizione, materiale, colore, stile, "", "In Closet", fileUrl, categoria, parteDelCorpo);
        C.doSave(capo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("toWardrobe");
        dispatcher.forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
