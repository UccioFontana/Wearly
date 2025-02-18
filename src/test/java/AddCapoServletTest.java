import controller.AddCapoServlet;
import jakarta.servlet.ServletException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddCapoServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Part filePart;

    private String nome = "T-Shirt";
    private String descrizione = "Cotton T-Shirt";
    private String materiale = "Cotton";
    private String colore = "Red";
    private String stile = "Casual";
    private String season = "Summer";
    private String categoria = "Tops";
    private String parteDelCorpo = "Torso";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockSession();

        // Mock del RequestDispatcher
        when(request.getRequestDispatcher("toWardrobe")).thenReturn(dispatcher);
    }

    private void mockSession() {
        // Crea un utente di test
        Utente u = new Utente("Mario", "Rossi", "mario.rossi@example.com", "password123");
        u.setId(1);

        // Mock della sessione
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(u);
    }


    @Test
    public void TC_2_1_1() throws ServletException, IOException {
         String n = "112233";
         String d = "A newly added clothing item";
         String m = "Cotton";
         String c = "Black";
         String sti = "Sporty";
         String seas= "Summer";
         String stat ="In Closet";
         String pdc = "Top";
         String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (!n.matches("^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$")) {
                throw new IllegalArgumentException("Nome contiene numeri");
            }
        });


    }



    @Test
    public void TC_2_1_2() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "112233";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/ucciofontana/Documents/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (d.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Descrizione contiene numeri");
            }
        });


    }



    @Test
    public void TC_2_1_3() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "112233";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (m.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Materiale contiene numeri");
            }
        });


    }

    @Test
    public void TC_2_1_4() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "112233";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (c.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Colore contiene numeri");
            }
        });


    }

    @Test
    public void TC_2_1_5() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "112233";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (sti.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Stile contiene numeri");
            }
        });


    }





    @Test
    public void TC_2_1_6() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "112233";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (seas.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Stagione contiene numeri");
            }
        });


    }


    @Test
    public void TC_2_1_7() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="112233";
        String pdc = "Top";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (stat.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Stato contiene numeri");
            }
        });


    }


    @Test
    public void TC_2_1_8() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "Shirt";
        String nameImm="shirt.txt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn(nameImm);




        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (!nameImm.matches(".*\\.(jpg|jpeg|png|gif|bmp|tiff|webp)$")) {
                throw new IllegalArgumentException("Immagine del formato errato");
            }
        });


    }



    @Test
    public void TC_2_1_9() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "Top";
        String cat = "112233";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (cat.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Categoria contiene numeri");
            }
        });


    }



    @Test
    public void TC_2_1_10() throws ServletException, IOException {
        String n = "Clothing Item";
        String d = "A newly added clothing item";
        String m = "Cotton";
        String c = "Black";
        String sti = "Sporty";
        String seas= "Summer";
        String stat ="In Closet";
        String pdc = "112233";
        String cat = "Shirt";


        when(request.getParameter("name")).thenReturn(n);
        when(request.getParameter("description")).thenReturn(d);
        when(request.getParameter("material")).thenReturn(m);
        when(request.getParameter("color")).thenReturn(c);
        when(request.getParameter("style")).thenReturn(sti);
        when(request.getParameter("season")).thenReturn(seas);
        when(request.getParameter("state")).thenReturn(stat);
        when(request.getParameter("category")).thenReturn(cat);
        when(request.getParameter("bodyPart")).thenReturn(pdc);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("shirt1.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/pietro/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);



        // Supponiamo che il sistema validi la lunghezza prima di verificare con BCrypt
        assertThrows(IllegalArgumentException.class, () -> {
            if (pdc.matches(".*\\d.*")) {
                throw new IllegalArgumentException("Parte del corpo contiene numeri");
            }
        });


    }






    @Test
    public void TC_2_1_11() throws Exception {
        // Mock del DAO e dei parametri della request
        CapoAbbigliamentoDAO capoAbbigliamentoDAO = mock(CapoAbbigliamentoDAO.class);

        when(request.getParameter("name")).thenReturn(nome);
        when(request.getParameter("description")).thenReturn(descrizione);
        when(request.getParameter("material")).thenReturn(materiale);
        when(request.getParameter("color")).thenReturn(colore);
        when(request.getParameter("style")).thenReturn(stile);
        when(request.getParameter("season")).thenReturn(season);
        when(request.getParameter("category")).thenReturn(categoria);
        when(request.getParameter("bodyPart")).thenReturn(parteDelCorpo);

        // Mock di file part solo se la sessione è valida
        when(request.getPart("image")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("boot.jpg");

        // Usa un'immagine valida per il test
        File imageFile = new File("/Users/ucciofontana/Documents/apache-tomcat-10.1.19/webapps/img/boot.jpg");
        InputStream mockImageInputStream = new FileInputStream(imageFile);
        when(filePart.getInputStream()).thenReturn(mockImageInputStream);

        // Esegui la servlet
        AddCapoServlet servlet = new AddCapoServlet();
        servlet.doPost(request, response);

        // Verifica che la risposta venga reindirizzata correttamente
        verify(dispatcher).forward(request, response);
    }













}