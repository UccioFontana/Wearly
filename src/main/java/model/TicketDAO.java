package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    public List<Ticket> getTicket(){
        List<Ticket> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ticket");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                 int id= rs.getInt("id");
                 String titolo= rs.getString("titolo");
                 String descrizione= rs.getString("descrizione");
                 String stato= rs.getString("stato");
                 LocalDate dataCreazione=rs.getDate("dataCreazione").toLocalDate() ;
                 int idUtente= rs.getInt("idUtenteRegistrato");
                 int idAmministratore= rs.getInt("idAmministratore");

                Ticket t = new Ticket();
                t.setId(id);
                t.setTitolo(titolo);
                t.setDescrizione(descrizione);
                t.setStato(stato);
                t.setDataCreazione(dataCreazione);
                t.setIdUtente(idUtente);
                t.setIdAmministratore(idAmministratore);

                list.add(t);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Ticket> getTicketNotClaimed(){
        List<Ticket> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ticket where idAmministratore is null ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String titolo= rs.getString("titolo");
                String descrizione= rs.getString("descrizione");
                String stato= rs.getString("stato");
                LocalDate dataCreazione=rs.getDate("dataCreazione").toLocalDate() ;
                int idUtente= rs.getInt("idUtenteRegistrato");
                int idAmministratore= rs.getInt("idAmministratore");

                Ticket t = new Ticket();
                t.setId(id);
                t.setTitolo(titolo);
                t.setDescrizione(descrizione);
                t.setStato(stato);
                t.setDataCreazione(dataCreazione);
                t.setIdUtente(idUtente);
                t.setIdAmministratore(idAmministratore);

                list.add(t);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean doSave(Ticket t) {
        List<Ticket> list = getTicket();
        if (list.contains(t))
            return false;
        else {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Ticket (titolo, descrizione,idUtenteRegistrato) VALUES (?, ?, ?)");
                ps.setString(1, t.getTitolo());
                ps.setString(2, t.getDescrizione());
                ps.setInt(3, t.getIdUtente());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ConPool.closeConnection(con);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
    }


    public void deleteTicket(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Ticket WHERE id =?");
            ps.setInt(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Ticket> getTicketByUser(int idU){
        List<Ticket> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ticket WHERE idUtenteRegistrato=?");
            ps.setInt(1,idU);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String titolo= rs.getString("titolo");
                String descrizione= rs.getString("descrizione");
                String stato= rs.getString("stato");
                LocalDate dataCreazione=rs.getDate("dataCreazione").toLocalDate() ;
                int idUtente= rs.getInt("idUtenteRegistrato");
                int idAmministratore= rs.getInt("idAmministratore");

                Ticket t = new Ticket();
                t.setId(id);
                t.setTitolo(titolo);
                t.setDescrizione(descrizione);
                t.setStato(stato);
                t.setDataCreazione(dataCreazione);
                t.setIdUtente(idUtente);
                t.setIdAmministratore(idAmministratore);

                list.add(t);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    public List<Ticket> getTicketByAdmin(int idAdmin){
        List<Ticket> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ticket WHERE idAmministratore=?");
            ps.setInt(1,idAdmin);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                String titolo= rs.getString("titolo");
                String descrizione= rs.getString("descrizione");
                String stato= rs.getString("stato");
                LocalDate dataCreazione=rs.getDate("dataCreazione").toLocalDate() ;
                int idUtente= rs.getInt("idUtenteRegistrato");
                int idAmministratore= rs.getInt("idAmministratore");

                Ticket t = new Ticket();
                t.setId(id);
                t.setTitolo(titolo);
                t.setDescrizione(descrizione);
                t.setStato(stato);
                t.setDataCreazione(dataCreazione);
                t.setIdUtente(idUtente);
                t.setIdAmministratore(idAmministratore);

                list.add(t);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void assegnaTicket(int idAdmin,int idT){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE Ticket SET IdAmministratore = ?, Stato = 'In corso' WHERE Id = ?");
            ps.setInt(1, idAdmin);
            ps.setInt(2, idT);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeTicket(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE Ticket SET Stato = 'Chiuso' WHERE Id = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }









    }
