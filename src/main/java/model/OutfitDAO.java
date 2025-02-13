package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OutfitDAO {
    private static final Logger LOGGER = Logger.getLogger(OutfitDAO.class.getName());


    public boolean doSave(Outfit out) {
        if(out.getNome().isEmpty() || out.getDescrizione().isEmpty()){
            return false;
        }
        // Creazione outfit
        try (Connection con = ConPool.getConnection()) {
            // Preparazione della query con ritorno della chiave generata
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Outfit (Nome, Descrizione) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, out.getNome());
            ps.setString(2, out.getDescrizione());

            int test = ps.executeUpdate();
            if (test != 1) {
                return false;
            }

            // Ottieni l'ID generato per l'outfit
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                out.setId(rs.getInt(1));  // Imposta l'ID ottenuto dal database
            } else {
                return false;  // Se non è stato possibile ottenere l'ID
            }
            LOGGER.info("id: " + out.getId());
            System.out.println("iddddddddddddd"+out.getId());


            ConPool.closeConnection(con);

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Codice di errore per duplicati in MySQL
                return false;
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Associazione idCapo a idOutfit
        try (Connection con = ConPool.getConnection()) {
            for (CapoAbbigliamento c : out.getListaCapi()) {
                int idC = c.getId();

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO DettagliOutfit (IdOutfit, IdCapo) VALUES (?, ?)"
                );
                ps.setInt(1, out.getId());  // Ora l'ID dell'outfit è disponibile
                ps.setInt(2, idC);

                int test = ps.executeUpdate();
                if (test != 1) {
                    return false;
                }
            }

            ConPool.closeConnection(con);
            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Codice di errore per duplicati in MySQL
                return false;
            }
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Outfit> getOutfits(){
       List<Outfit> list = new ArrayList<>();

       try (Connection con = ConPool.getConnection()){

           PreparedStatement ps = con.prepareStatement("SELECT * FROM Outfit ");

           ResultSet rs = ps.executeQuery();
           while (rs.next()){
               int id = rs.getInt("Id");
               String nome= rs.getString("Nome");
               String descrizione= rs.getString("Descrizione");
               Outfit out = new Outfit(nome,descrizione);
               out.setId(id);
               CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
               out.setListaCapi(C.getCapiByIdOufit(out.getId()));

               list.add(out);
           }

           ConPool.closeConnection(con);
           return list;

       }catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

   public void deleteOutfit(int id){
       try (Connection con = ConPool.getConnection()) {
           PreparedStatement ps = con.prepareStatement("DELETE FROM Outfit WHERE id =?");
           ps.setInt(1, id);
           if (ps.executeUpdate() != 1) {
               throw new RuntimeException("INSERT error.");
           }
           ConPool.closeConnection(con);
       } catch (SQLException | IOException e) {
           throw new RuntimeException(e);
       }
   }


    public void updateOutfit(Outfit outfit) {
        try (Connection con = ConPool.getConnection()) {
            if (outfit.getNome() != null && !outfit.getNome().isEmpty()) {
                aggiornaCampo(con, outfit.getId(), "Nome", outfit.getNome());
            }
            if (outfit.getDescrizione() != null && !outfit.getDescrizione().isEmpty()) {
                aggiornaCampo(con, outfit.getId(), "Descrizione", outfit.getDescrizione());
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void aggiornaCampo(Connection connection, int idOutfit, String campo, Object valore) throws SQLException {
        // Query di aggiornamento utilizzando PreparedStatement
        String query = "UPDATE Outfit SET " + campo + " = ? WHERE Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Imposta il valore del campo nella query
            if (valore instanceof String) {
                preparedStatement.setString(1, (String) valore);
            }

            // Imposta l'ID dell'outfit nella clausola WHERE
            preparedStatement.setInt(2, idOutfit);

            // Esegui l'aggiornamento
            int righeAggiornate = preparedStatement.executeUpdate();

            // Verifica quante righe sono state effettivamente aggiornate
            if (righeAggiornate > 0) {
                System.out.println("Aggiornamento completato con successo per l'outfit con ID " + idOutfit);
            } else {
                System.out.println("Nessun outfit trovato con ID " + idOutfit);
            }
        }
    }

    public List<Outfit> getOutfitByUser(int idUtente){
        List<Outfit> list = new ArrayList<>();

        try (Connection con = ConPool.getConnection()){

            PreparedStatement ps = con.prepareStatement("SELECT DISTINCT o.* FROM Outfit o JOIN DettagliOutfit d ON o.Id = d.IdOutfit JOIN CapoDAbbigliamento c ON d.IdCapo = c.Id WHERE c.IdUtenteRegistrato = ?");
            ps.setInt(1,idUtente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Id");
                String nome= rs.getString("Nome");
                String descrizione= rs.getString("Descrizione");
                Outfit out = new Outfit(nome,descrizione);
                out.setId(id);
                CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
                out.setListaCapi(C.getCapiByIdOufit(out.getId()));

                list.add(out);
            }

            ConPool.closeConnection(con);
            return list;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public Outfit getOutfitById(int idO){
        Outfit out = null;

        try (Connection con = ConPool.getConnection()){

            PreparedStatement ps = con.prepareStatement("SELECT  o.* FROM Outfit o WHERE o.Id =?");
            ps.setInt(1,idO);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Id");
                String nome= rs.getString("Nome");
                String descrizione= rs.getString("Descrizione");
                 out = new Outfit(nome,descrizione);
                out.setId(id);
                CapoAbbigliamentoDAO C = new CapoAbbigliamentoDAO();
                out.setListaCapi(C.getCapiByIdOufit(out.getId()));
            }

            ConPool.closeConnection(con);
            return out;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void rimuoviCapDaOutfit(int idOutfit, int idCapo){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM DettagliOutfit WHERE idOutfit =? AND idCapo=?");
            ps.setInt(1, idOutfit);
            ps.setInt(2, idCapo);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }






}
