package controller;

import imagesManagement.ImageProcessor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.CapoAbbigliamento;
import model.CapoAbbigliamentoDAO;
import model.Utente;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/editCapoServlet")
@MultipartConfig
public class EditCapoServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/Users/pietro/apache-tomcat-10.1.19/webapps/img";


    private static final Map<String, String> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("Red", "[255,0,0]");
        COLOR_MAP.put("Green", "[0,255,0]");
        COLOR_MAP.put("Blue", "[0,0,255]");
        COLOR_MAP.put("Yellow", "[255,255,0]");
        COLOR_MAP.put("Cyan", "[0,255,255]");
        COLOR_MAP.put("Magenta", "[255,0,255]");
        COLOR_MAP.put("Black", "[0,0,0]");
        COLOR_MAP.put("White", "[255,255,255]");
        COLOR_MAP.put("Gray", "[128,128,128]");
        COLOR_MAP.put("Orange", "[255,165,0]");
        COLOR_MAP.put("Purple", "[128,0,128]");
        COLOR_MAP.put("Brown", "[165,42,42]");
        COLOR_MAP.put("Pink", "[255,192,203]");
        COLOR_MAP.put("Turquoise", "[64,224,208]");
        COLOR_MAP.put("Beige", "[245,245,220]");
        COLOR_MAP.put("Lavender", "[230,230,250]");
        COLOR_MAP.put("Maroon", "[128,0,0]");
        COLOR_MAP.put("Olive", "[128,128,0]");
        COLOR_MAP.put("Navy", "[0,0,128]");
        COLOR_MAP.put("Teal", "[0,128,128]");
        COLOR_MAP.put("Silver", "[192,192,192]");
        COLOR_MAP.put("Gold", "[255,215,0]");
        COLOR_MAP.put("Indigo", "[75,0,130]");
        COLOR_MAP.put("Coral", "[255,127,80]");
        COLOR_MAP.put("Salmon", "[250,128,114]");
        COLOR_MAP.put("Chocolate", "[210,105,30]");
        COLOR_MAP.put("Peru", "[205,133,63]");
        COLOR_MAP.put("Crimson", "[220,20,60]");
        COLOR_MAP.put("Sky Blue", "[135,206,235]");
        COLOR_MAP.put("Lime", "[0,255,0]");
    }

    private String convertColorToRGB(String colorName) {
        return COLOR_MAP.getOrDefault(colorName, "[0,0,0]"); // Default: Nero
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        Utente utente = (Utente) request.getSession(false).getAttribute("utente");
        int idUtente = -1;
        if (utente == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("front-end/jsp/login.jsp");
            dispatcher.forward(request, resp);
            return;
        } else {
            idUtente = utente.getId();
        }

        // Recupera i parametri dal form
        int idCapo = Integer.parseInt(request.getParameter("idCapo"));
        String nome = request.getParameter("name");
        String descrizione = request.getParameter("description");
        String materiale = request.getParameter("material");
        String colore = request.getParameter("color");
        String stile = request.getParameter("style");
        String season = request.getParameter("season");
        String categoria = request.getParameter("category");
        String parteDelCorpo = request.getParameter("bodyPart");
        String state = request.getParameter("state");
        String flag = (request.getParameter("flag"));

        System.out.println(flag);

        CapoAbbigliamento capo;

        if(flag.equals("imageYes")){
            // Gestione caricamento immagine
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Creazione cartella se non esiste
            File uploads = new File(UPLOAD_DIRECTORY);
            if (!uploads.exists()) {
                uploads.mkdirs();
            }

            // Percorso del file compresso (senza salvare l'originale)
            File compressedFile = new File(UPLOAD_DIRECTORY + File.separator + fileName);

            // Comprimere e ridimensionare direttamente l'input stream
            try (InputStream fileInputStream = filePart.getInputStream()) {
                ImageProcessor.compressAndResizeImage(fileInputStream, compressedFile);
            }

            // URL dell'immagine compressa
            String fileUrl = "/img/" + fileName;

            if (colore == null || colore.isEmpty()) {
                capo = new CapoAbbigliamento(idUtente, nome, descrizione, materiale, colore, stile, season, state, fileUrl, categoria, parteDelCorpo);
            }
            else{
                // Converti colore in formato RGB
                String coloreRGB = convertColorToRGB(colore);
                capo = new CapoAbbigliamento(idUtente, nome, descrizione, materiale, coloreRGB, stile, season, state, fileUrl, categoria, parteDelCorpo);
            }

        }
        else{

            if (colore.equals("-")) {
                capo = new CapoAbbigliamento(idUtente, nome, descrizione, materiale, "", stile, season, state, "", categoria, parteDelCorpo);
            }
            else{
                // Converti colore in formato RGB
                String coloreRGB = convertColorToRGB(colore);
                capo = new CapoAbbigliamento(idUtente, nome, descrizione, materiale, coloreRGB, stile, season, state, "", categoria, parteDelCorpo);
            }

        }
        capo.setId(idCapo);
        //stile, bodypart,categoria
        if(stile.equals("-"))
            capo.setStile("");

        if(parteDelCorpo.equals("-"))
            capo.setParteDelCorpo("");

        if(categoria.equals("-"))
            capo.setCategoria("");

        CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
        C.updateCapoAbbigliamento(capo);

        // Redirect a una nuova pagina
        RequestDispatcher dispatcher = request.getRequestDispatcher("toWardrobe");
        dispatcher.forward(request, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
