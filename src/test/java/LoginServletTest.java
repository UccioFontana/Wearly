import controller.LoginServlet;
import jakarta.servlet.ServletException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
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

    //UTENTE

    @Test
    public void TC_1_1_1() throws IOException, ServletException {
        String email = "mario@.com";
        String password = "mario2003";  // Password BCrypt

        assertFalse(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$"), "L'email non dovrebbe essere considerata valida");

    }

    @Test
    public void TC_1_1_2() throws IOException, ServletException {
        String email = "luigi@gmail.com";
        String password = "mario2003";  // Password BCrypt

        UtenteDAO U = new UtenteDAO();
        Utente user = U.getUserByEmail(email);
        assertNull(user);// Deve restituire un utente nullo

    }

    @Test
    public void TC_1_1_3() throws IOException, ServletException {
        String email = "mario.rossi@example.com";
        String password = "luigi2003";  // Password BCrypt


        UtenteDAO U = new UtenteDAO();
        Utente user = U.getUserByEmail(email);
        assertNotNull(user);// Deve restituire un utente non nullo

        assertFalse(BCrypt.checkpw(password, user.getPassword()));
    }

    @Test
    public void TC_1_1_4() throws Exception {
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



    //AMMINISTRATORE
    @Test
    public void TC_1_2_1() throws IOException, ServletException {
        String email = "admin@.com";
        String password = "administrator";  // Password BCrypt

        assertFalse(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$"), "L'email non dovrebbe essere considerata valida");

    }

    @Test
    public void TC_1_2_2() throws IOException, ServletException {
        String email = "luigi@gmail.com";
        String password = "administrator";  // Password BCrypt

        UtenteDAO U = new UtenteDAO();
        Utente user = U.getAdminByEmail(email);
        assertNull(user);// Deve restituire un utente nullo

    }
    @Test
    public void TC_1_2_3() throws IOException, ServletException {
        String email = "luigi.bianchi@example.com";
        String password = "luigi2003";  // Password BCrypt


        UtenteDAO U = new UtenteDAO();
        Utente user = U.getAdminByEmail(email);
        assertNotNull(user);// Deve restituire un utente non nullo

        assertFalse(BCrypt.checkpw(password, user.getPassword()));
    }


    @Test
    public void TC_1_2_4() throws Exception {
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