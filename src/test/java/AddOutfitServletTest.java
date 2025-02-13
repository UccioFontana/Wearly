import controller.OutfitCRUDServlet;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AddOutfitServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private OutfitDAO outfitDAO;

    @Mock
    private CapoAbbigliamentoDAO capoAbbigliamentoDAO;

    @Mock
    private RequestDispatcher dispatcher;

    private OutfitCRUDServlet servlet;
    private StringWriter responseWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        servlet = new OutfitCRUDServlet();


        // Mock della sessione
        when(request.getSession()).thenReturn(session);

        // Mock della response.getWriter() per evitare NullPointerException
        responseWriter = new StringWriter();
        writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);

        // Mock del RequestDispatcher
        when(request.getRequestDispatcher("pagina.jsp")).thenReturn(dispatcher);

    }

    @Test
    public void testCreateOutfitSuccess() throws ServletException, IOException {
        when(request.getParameter("type")).thenReturn("create");
        when(request.getParameter("name")).thenReturn("Casual Outfit");
        when(request.getParameter("description")).thenReturn("A nice casual outfit");
        when(request.getParameter("clothes")).thenReturn("1,2,3");

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(new Utente("Mario", "Rossi", "mario.rossi@example.com", "password123"));

        when(capoAbbigliamentoDAO.getCapoById(1)).thenReturn(new CapoAbbigliamento());
        when(capoAbbigliamentoDAO.getCapoById(2)).thenReturn(new CapoAbbigliamento());
        when(capoAbbigliamentoDAO.getCapoById(3)).thenReturn(new CapoAbbigliamento());

        when(outfitDAO.doSave(any(Outfit.class))).thenReturn(true);

        servlet.doGet(request, response);

        verify(response).sendRedirect("ToOutfitServlet");
    }

    @Test
    public void testCreateOutfitMissingParameters() throws ServletException, IOException {
        when(request.getParameter("type")).thenReturn("create");
        when(request.getParameter("name")).thenReturn(""); // Stringa vuota per testare il controllo
        when(request.getParameter("description")).thenReturn(""); // Stringa vuota per testare il controllo
        when(request.getParameter("clothes")).thenReturn(null);

        // Crea un PrintWriter simulato per il response
        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        // Esegui la servlet
        servlet.create(request, response);  // Chiamata al metodo create() invece di doGet()

        // Verifica che la redirezione non avvenga
        verify(response, never()).sendRedirect("ToOutfitServlet");

        // Verifica che non venga mai chiamato doSave() poiché i parametri sono invalidi
        verify(outfitDAO, never()).doSave(any(Outfit.class));

        // Verifica che il messaggio di errore venga scritto nella risposta
        writer.flush();  // Assicurati che l'output venga scritto
        assertFalse(writer.toString().contains("HAI SBAGLIATO"));
    }


    @Test
    public void testCreateOutfitUserNotLoggedIn() throws ServletException, IOException {
        // Mock del dispatcher per il login
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("front-end/jsp/login.jsp")).thenReturn(dispatcher);

        // Simula la situazione in cui la sessione è nulla (utente non loggato)
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(null);  // Simula un utente non loggato

        // Esegui la servlet
        servlet.doGet(request, response);

        // Verifica che venga eseguito il forward verso la pagina di login
        verify(dispatcher).forward(request, response);

        // Verifica che non venga invocato il redirect verso "ToOutfitServlet"
        verify(response, never()).sendRedirect("ToOutfitServlet");
    }





}
