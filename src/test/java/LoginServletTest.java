
import controller.LoginServlet;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    private CapoAbbigliamentoDAO capoAbbigliamentoDAO;

    @Mock
    private OutfitDAO outfitDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the necessary objects
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        // Mock DAO methods for login
        when(utenteDAO.getUserByEmail(anyString())).thenReturn(new Utente());  // simulate a user found
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String email = "mario.rossi@example.com";
        String password = "$2a$10$Zy74KLIER0csBFRR7FaeGemlz/r4c7WUSvQ.5gCcYU6ldogFN5hHS";

        Utente u = new Utente();
        u.setEmail(email);
        u.setPassword(password);  // set a correct password

        when(utenteDAO.getUserByEmail(email)).thenReturn(u);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Simulate successful login
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        verify(response, never()).getWriter();  // no error should be written to the response
        verify(request).getRequestDispatcher("front-end/jsp/userPage.jsp");  // userPage.jsp should be forwarded
    }

    @Test
    public void testLoginFailure() throws Exception {
        String email = "test@example.com";
        String password = "incorrectPassword";

        Utente u = new Utente();
        u.setEmail(email);
        u.setPassword("correctPassword");  // set a correct password, but it won't match

        when(utenteDAO.getUserByEmail(email)).thenReturn(u);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Simulate failed login
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        // Ensure the failure message is written to the response
        verify(response).getWriter();
    }

    @Test
    public void testAdminLogin() throws Exception {
        String email = "admin@example.com";
        String password = "adminPassword";

        Utente adminUser = new Utente();
        adminUser.setEmail(email);
        adminUser.setPassword(password);  // set admin credentials

        when(utenteDAO.getUserByEmail(email)).thenReturn(adminUser);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Simulate successful admin login
        LoginServlet servlet = new LoginServlet();
        servlet.doGet(request, response);

        // Ensure the admin page redirect is happening
        verify(request).getRequestDispatcher("adminServlet");
    }
}