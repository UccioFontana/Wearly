package model;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationFacade {

    private HttpSession session;

    public AuthenticationFacade(HttpSession session) {
        this.session = session;
    }


    //metodo che effettua il login
    public Map<String, Integer> login(String email, String password) {
        UtenteDAO U = new UtenteDAO();
        Utente u = U.getUserByEmail(email);
        Map<String, Integer> returnValues = new HashMap<>();


        if (u != null && BCrypt.checkpw(password, u.getPassword())) {
            session.setAttribute("utente", u);

            // Reindirizzamento alla pagina utente
            CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
            List<CapoAbbigliamento> list = C.getCapoByUser(u.getId());
            int contCapi = 0;
            for (CapoAbbigliamento c : list)
                contCapi++;

            OutfitDAO O = new OutfitDAO();
            List<Outfit> lOut = O.getOutfitByUser(u.getId());
            int contOutfit = 0;
            for (Outfit o : lOut)
                contOutfit++;


            returnValues.put("numOutfit", contOutfit);
            returnValues.put("numCapi", contCapi);
            returnValues.put("loginType", 0); //utente
            return returnValues;


        } else {
            List<Utente> adminL = U.getAdmin();
            for (Utente user : adminL) {
                if (user.getEmail().equals(email) && BCrypt.checkpw(password, user.getPassword())) {

                    session.setAttribute("admin", user);

                    returnValues.put("loginType", 1); //admin
                    return returnValues;
                }
            }
            returnValues.put("loginType", 2); //not found
        }
       return returnValues;
    }


    public boolean signup(String nome, String cognome, String email, String password) {
        String hashPass = BCrypt.hashpw(password,BCrypt.gensalt());
        UtenteDAO U = new UtenteDAO();
        Utente user = new Utente(nome,cognome,email,hashPass);
        return U.doSave(user);
    }
}
