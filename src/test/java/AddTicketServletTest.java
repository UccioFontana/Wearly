import controller.OutfitCRUDServlet;
import controller.TicketServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddTicketServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private TicketDAO ticketDAO;
    private TicketServlet servlet;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new TicketServlet();

    }


    @Test
    public void TC_4_1_1(){
        String titolo="112233";
        String descrizione="Non riesco ad accedere a Wearly.";
        when(request.getParameter("ticketObject")).thenReturn(titolo);
        when(request.getParameter("ticketIssue")).thenReturn(descrizione);

        assertThrows(IllegalArgumentException.class, () -> {
            if (!titolo.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
                throw new IllegalArgumentException("Titolo contiene numeri");
            }
        });
    }

    @Test
    public void TC_4_1_2(){
        String titolo="Errore nel login";
        String descrizione="112233";
        when(request.getParameter("ticketObject")).thenReturn(titolo);
        when(request.getParameter("ticketIssue")).thenReturn(descrizione);

        assertThrows(IllegalArgumentException.class, () -> {
            if (!descrizione.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
                throw new IllegalArgumentException("Descrizione contiene numeri");
            }
        });
    }



    @Test
    public void TC_4_1_3() throws ServletException, IOException {

        String titolo="Errore nel login";
        String descrizione="Non riesco ad accedere a Wearly.";
        when(request.getParameter("ticketObject")).thenReturn(titolo);
        when(request.getParameter("ticketIssue")).thenReturn(descrizione);



        Ticket ticket = new Ticket(1,titolo,descrizione);
        TicketDAO T = new TicketDAO();

        assertTrue(T.doSave(ticket));
    }




}
