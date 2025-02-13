import controller.RegisterServlet;
import jakarta.servlet.ServletException;
import model.AuthenticationFacade;
import model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class RegistrazioneServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private AuthenticationFacade authFacade;

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
        when(request.getRequestDispatcher("home.jsp")).thenReturn(dispatcher);
    }



    @Test
    public void testRegisterSuccess() throws Exception {
        String nome = "Mario";
        String cognome = "Rossi";
        String email = "mario.rossi@example.com";
        String password = "password123";

        // Mock AuthenticationFacade
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true);

        // Mock del comportamento della servlet
        RegisterServlet servlet = new RegisterServlet();
        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("surname")).thenReturn(cognome);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        servlet.doGet(request, response);

        writer.flush();  // Assicura che l'output venga scritto

        // Verifica che non ci sia stato un redirect
        verify(response, never()).sendRedirect("home.jsp");


    }


    @Test
    public void testRegisterFailure() throws Exception {
        String nome = "Luigi";
        String cognome = "Bianchi";
        String email = "luigi.bianchi@example.com";
        String password = "password123";

        // Mock AuthenticationFacade
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(false);
        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("surname")).thenReturn(cognome);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Esegui la servlet
        RegisterServlet servlet = new RegisterServlet();
        servlet.doGet(request, response);

        writer.flush();  // Assicura che l'output venga scritto

        // Verifica che la risposta contenga l'errore
        verify(response).getWriter();
        assert responseWriter.toString().contains("UTENTE PRESENTE NEL SISTEMA");

        // Se ci aspettiamo che venga effettuato un redirect in caso di successo, modifichiamo la logica di test
        // in base a quello che fa effettivamente la servlet.
    }
}
