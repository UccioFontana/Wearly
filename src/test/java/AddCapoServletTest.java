import controller.AddCapoServlet;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

public class AddCapoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Part filePart;

    private String nome = "T-Shirt";
    private String descrizione = "Cotton T-Shirt";
    private String materiale = "Cotton";
    private String colore = "Red";
    private String stile = "Casual";
    private String season = "Summer";
    private String categoria = "Tops";
    private String parteDelCorpo = "Torso";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockSession();

        // Mock del RequestDispatcher
        when(request.getRequestDispatcher("toWardrobe")).thenReturn(dispatcher);
    }

    private void mockSession() {
        // Crea un utente di test
        Utente u = new Utente("Mario", "Rossi", "mario.rossi@example.com", "password123");
        u.setId(1);

        // Mock della sessione
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(u);
    }

    @Test
    public void testAddCapoSuccess() throws Exception {
        // Mock del DAO e dei parametri della request
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = mock(CapoAbbigliamentoDAO.class);

        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("description")).thenReturn(descrizione);
        when(request.getParameter("material")).thenReturn(materiale);
        when(request.getParameter("color")).thenReturn(colore);
        when(request.getParameter("style")).thenReturn(stile);
        when(request.getParameter("season")).thenReturn(season);
        when(request.getParameter("category")).thenReturn(categoria);
        when(request.getParameter("bodyPart")).thenReturn(parteDelCorpo);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("boot.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);

        // Esegui la servlet
        AddCapoServlet servlet = new AddCapoServlet();
        servlet.doPost(request, response);

        // Verifica che la risposta venga reindirizzata correttamente
        verify(dispatcher).forward(request, response);
    }


    @Test
    public void testUserNotLoggedIn() throws Exception {
        // Mock del dispatcher per il login
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("front-end/jsp/login.jsp")).thenReturn(dispatcher);

        // Simula il comportamento in cui la sessione esiste ma non c'è l'attributo 'utente'
        when(request.getSession(false)).thenReturn(session); // Restituisce una sessione
        when(session.getAttribute("utente")).thenReturn(null); // L'attributo 'utente' è nullo (utente non loggato)

        // Esegui il test sulla servlet
        AddCapoServlet servlet = new AddCapoServlet();
        servlet.doGet(request, response);

        // Verifica che venga eseguito il forward verso la pagina di login
        verify(dispatcher).forward(request, response);

        // Verifica che non venga invocato il RequestDispatcher per "toWardrobe" (in caso di login)
        verify(request, never()).getRequestDispatcher("toWardrobe");
        //test
    }








}
