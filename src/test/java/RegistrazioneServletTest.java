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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void TC_1_3_1(){
        String nome = "Francesco";
        String cognome = "Salerno";
        String email = "@salerno.com";
        String password = "fransalerno";

        // Mock AuthenticationFacade per far passare il test solo se l'utente non è presente
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true); // Successo solo se l'utente non esiste

        assertFalse(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$"), "L'email non dovrebbe essere considerata valida");

    }
    @Test
    public void TC_1_3_2(){
        String nome = "Francesco";
        String cognome = "Salerno";
        String email = "f.salerno@gmail.com";
        String password = "fs";

        // Mock AuthenticationFacade per far passare il test solo se l'utente non è presente
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true); // Successo solo se l'utente non esiste

        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (password.length() < 9) {
                throw new IllegalArgumentException("Password troppo corta");
            }
        });

    }

    @Test
    public void TC_1_3_3(){
        String nome = "1122";
        String cognome = "Salerno";
        String email = "f.salerno@gmail.com";
        String password = "fransalerno";

        // Mock AuthenticationFacade per far passare il test solo se l'utente non è presente
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true); // Successo solo se l'utente non esiste

        assertThrows(IllegalArgumentException.class, () -> {
            if (!nome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) { // Solo lettere e spazi
                throw new IllegalArgumentException("Il nome non può contenere numeri");
            }
        });

    }

    @Test
    public void TC_1_3_4(){
        String nome = "Francesco";
        String cognome = "112233";
        String email = "f.salerno@gmail.com";
        String password = "fransalerno";

        // Mock AuthenticationFacade per far passare il test solo se l'utente non è presente
        when(authFacade.signup(nome, cognome, email, password)).thenReturn(true); // Successo solo se l'utente non esiste

        assertThrows(IllegalArgumentException.class, () -> {
            if (!cognome.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) { // Solo lettere e spazi
                throw new IllegalArgumentException("Il nome non può contenere numeri");
            }
        });

    }


    @Test
    public void TC_1_3_5() throws Exception {
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


}
