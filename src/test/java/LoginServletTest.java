import controller.LoginServlet;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private UtenteDAO utenteDAO;

    private StringWriter responseWriter;
    private PrintWriter writer;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock della sessione
        when(request.getSession()).thenReturn(session);

        // Mock della response.getWriter() per evitare NullPointerException
        responseWriter = new StringWriter();
        writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);

        // Mock del RequestDispatcher
        when(request.getRequestDispatcher("front-end/jsp/userPage.jsp")).thenReturn(dispatcher);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String email = "mario.rossi@example.com";
        String password = "password123";  // Password BCrypt

        Utente u = new Utente();
        u.setId(1);  // Imposta un ID fittizio per il test
        u.setEmail(email);
        u.setPassword(password);

        // Simula il comportamento del DAO
        when(utenteDAO.getUserByEmail(email)).thenReturn(u);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Mock della sessione
        when(request.getSession()).thenReturn(session);

        // Esegui la servlet
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        // Verifica che il forward alla pagina utente sia stato effettuato
        verify(dispatcher).forward(request, response);
        verify(response, never()).getWriter();  // Non deve scrivere errori

    }

    @Test
    public void testLoginFailure() throws Exception {
        String email = "test@example.com";
        String password = "incorrectPassword";

        Utente u = new Utente();
        u.setEmail(email);
        u.setPassword("correctPassword");

        when(utenteDAO.getUserByEmail(email)).thenReturn(u);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Esegui la servlet
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        writer.flush();  // Assicura che l'output venga scritto

        // Verifica che la risposta contenga "HAI SBAGLIATO"
        verify(response).getWriter();
        assert responseWriter.toString().contains("HAI SBAGLIATO");
    }

    @Test
    public void testAdminLogin() throws Exception {
        String email = "luigi.bianchi@example.com";
        String password = "password123";

        Utente adminUser = new Utente();
        adminUser.setEmail(email);
        adminUser.setPassword(password);

        when(utenteDAO.getUserByEmail(email)).thenReturn(adminUser);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestDispatcher("adminServlet")).thenReturn(dispatcher);

        // Esegui la servlet
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);  // Verifica reindirizzamento all'adminServlet
    }
}