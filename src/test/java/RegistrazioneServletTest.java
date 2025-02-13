import controller.RegisterServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.AuthenticationFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    private StringWriter responseWriter;
    private PrintWriter writer;

    @Mock
    private AuthenticationFacade authFacade;  // Mock di AuthenticationFacade

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock della sessione
        when(request.getSession()).thenReturn(session);

        // Mock della response.getWriter() per evitare NullPointerException
        responseWriter = new StringWriter();
        writer = new PrintWriter(responseWriter);
        when(response.getWriter()).thenReturn(writer);

        // Configurazione del mock per l'autenticazione (AuthenticationFacade)
        authFacade = mock(AuthenticationFacade.class);
    }

    @Test
    public void testRegisterSuccess() throws Exception {
        String nome = "luca";
        String cognome = "Rossi";
        String email = "luca.rossi@example.com";
        String password = "password123";

        // Mock AuthenticationFacade per far passare il test solo se l'utente non è presente
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true); // Successo solo se l'utente non esiste

        // Esegui il test
        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("surname")).thenReturn(cognome);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        RegisterServlet servlet = new RegisterServlet();
        servlet.doGet(request, response);



        // Verifica che il redirect sia avvenuto (registrazione avvenuta)
        verify(response).sendRedirect("home.jsp");
    }

    @Test
    public void testRegisterFailure() throws Exception {
        String nome = "Luigi";
        String cognome = "Bianchi";
        String email = "luigi.bianchi@example.com";
        String password = "password123";

        // Mock del comportamento della chiamata signup in caso di fallimento (utente già presente)
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(false);  // Errore: utente già presente

        // Iniettare il mock di AuthenticationFacade nella servlet tramite costruttore (o metodi)
        RegisterServlet servlet = new RegisterServlet() {
            @Override
            public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
                // Usare il mock di AuthenticationFacade
                AuthenticationFacade mockAuthFacade = authFacade;
                super.doGet(req, resp);  // Chiamata al metodo originale
            }
        };

        // Configurazione dei parametri della richiesta
        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("surname")).thenReturn(cognome);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        // Esegui la servlet
        servlet.doGet(request, response);

        writer.flush();  // Assicura che l'output venga scritto

        // Verifica che la risposta contenga l'errore
        verify(response).getWriter();
        assert responseWriter.toString().contains("UTENTE PRESENTE NEL SISTEMA");
    }
}
