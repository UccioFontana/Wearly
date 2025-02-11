import controller.AddCapoServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import model.CapoAbbigliamentoDAO;
import model.CapoAbbigliamento;
import model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddCapoServletTest {
    private AddCapoServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher dispatcher;
    private CapoAbbigliamentoDAO capoDAO;
    private Part filePart;

    @BeforeEach
    void setUp() {
        servlet = new AddCapoServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        dispatcher = mock(RequestDispatcher.class);
        filePart = mock(Part.class);
    }

    @Test
    void testDoGet_UtenteLoggato_AggiuntaCapo() throws ServletException, IOException {
        Utente utente = new Utente();
        utente.setId(1);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(utente);
        when(request.getParameter("name")).thenReturn("T-shirt");
        when(request.getParameter("description")).thenReturn("T-shirt in cotone");
        when(request.getParameter("material")).thenReturn("Cotone");
        when(request.getParameter("color")).thenReturn("Red");
        when(request.getParameter("style")).thenReturn("Casual");
        when(request.getParameter("season")).thenReturn("Summer");
        when(request.getParameter("category")).thenReturn("Top");
        when(request.getParameter("bodyPart")).thenReturn("Torso");
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("boot.jpg");
        InputStream imageStream = getClass().getClassLoader().getResourceAsStream("boot.jpg");
        when(filePart.getInputStream()).thenReturn(imageStream);
        when(request.getRequestDispatcher("toWardrobe")).thenReturn(dispatcher);
        servlet.doGet(request, response);

        ArgumentCaptor<CapoAbbigliamento> capoCaptor = ArgumentCaptor.forClass(CapoAbbigliamento.class);
        verify(capoDAO).doSave(capoCaptor.capture());
        CapoAbbigliamento savedCapo = capoCaptor.getValue();

        assertEquals(1, savedCapo.getIdUtente());
        assertEquals("T-shirt", savedCapo.getNome());
        assertEquals("T-shirt in cotone", savedCapo.getDescrizione());
        assertEquals("Cotone", savedCapo.getMateriale());
        assertEquals("[255,0,0]", savedCapo.getColore()); // Verifica conversione colore
        assertEquals("Casual", savedCapo.getStile());
        assertEquals("Summer", savedCapo.getStagione());
        assertEquals("Top", savedCapo.getCategoria());
        assertEquals("Torso", savedCapo.getParteDelCorpo());
        assertTrue(savedCapo.getImmagine().contains("boot.jpg"));

        verify(dispatcher).forward(request, response);
    }
}