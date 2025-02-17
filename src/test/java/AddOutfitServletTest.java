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

import static org.junit.jupiter.api.Assertions.*;
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
    public void TC_3_1_1(){

        String n="112233";
        String desc="A nice casual outfit";
        when(request.getParameter("type")).thenReturn("create");
        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(desc);
        when(request.getParameter("clothes")).thenReturn("1,2,3");

        assertThrows(IllegalArgumentException.class, () -> {
            if (!n.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Nome contiene numeri");
            }
        });

    }

    @Test
    public void TC_3_1_2(){

        String n="Winter Outfit";
        String desc="112233";
        String capi="";
        when(request.getParameter("type")).thenReturn("create");
        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(desc);
        when(request.getParameter("clothes")).thenReturn( capi);

        assertThrows(IllegalArgumentException.class, () -> {
            if (capi.isEmpty()) {
                throw new IllegalArgumentException("Non ci sono capi");
            }
        });

    }





    @Test
    public void TC_3_1_3() throws ServletException, IOException {
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




}
