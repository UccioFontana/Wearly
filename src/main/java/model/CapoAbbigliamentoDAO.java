package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CapoAbbigliamentoDAO {

    public List<CapoAbbigliamento> getCapoByUser(int idUtente){
        List<CapoAbbigliamento> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT c.* FROM CapoDAbbigliamento c,UtenteRegistrato u WHERE c.idUtenteRegistrato = ? ");
            ps.setInt(1, idUtente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id= rs.getInt("id");
                int idU= rs.getInt("idUtenteRegistrato");
                 String nome= rs.getString("Nome");
                 String descrizione=rs.getString("Descrizione");
                 String materiale=rs.getString("Materiale");
                 String colore =rs.getString("Colore");
                 String stile= rs.getString("Stile");
                 String stagione= rs.getString("Stagione");
                 String immagine= rs.getString("Immagine");
                 String categoria= rs.getString("Categoria");
                 String parteDelCorpo=rs.getString("ParteDelCorpo");

                 CapoAbbigliamento c= new CapoAbbigliamento(idU,nome,descrizione,materiale,colore,stile,stagione,immagine,categoria,parteDelCorpo);
                 c.setId(id);
                 list.add(c);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
        throw new RuntimeException(e);
    }
    }

    public List<CapoAbbigliamento> getCapi(){
        List<CapoAbbigliamento> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT c.* FROM CapoDAbbigliamento c");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                int id= Integer.parseInt(rs.getString("id"));
                int idU= Integer.parseInt(rs.getString("idUtenteRegistrato"));
                String nome= rs.getString("Nome");
                String descrizione=rs.getString("Descrizione");
                String materiale=rs.getString("Materiale");
                String colore =rs.getString("Colore");
                String stile= rs.getString("Stile");
                String stagione= rs.getString("Stagione");
                String immagine= rs.getString("Immagine");
                String categoria= rs.getString("Categoria");
                String parteDelCorpo=rs.getString("ParteDelCorpo");

                CapoAbbigliamento c= new CapoAbbigliamento(idU,nome,descrizione,materiale,colore,stile,stagione,immagine,categoria,parteDelCorpo);
                c.setId(id);
                list.add(c);
            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean doSave(CapoAbbigliamento c){
        List<CapoAbbigliamento> list = getCapi();
        if(list.contains(c))
            return false;
        else{
            try (Connection con = ConPool.getConnection()){
                PreparedStatement ps = con.prepareStatement("INSERT INTO CapoDAbbigliamento (IdUtenteRegistrato, Nome, Descrizione, Materiale, Colore, Stile, Stagione, Immagine, Categoria, ParteDelCorpo) VALUES (?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1,c.getIdUtente());
                ps.setString(2,c.getNome());
                ps.setString(3,c.getDescrizione());
                ps.setString(4,c.getMateriale());
                ps.setString(5,c.getColore());
                ps.setString(6,c.getStile());
                ps.setString(7,c.getStagione());
                ps.setString(8,c.getImmagine());
                ps.setString(9,c.getCategoria());
                ps.setString(10,c.getParteDelCorpo());
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ConPool.closeConnection(con);

            }catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

    }

    public void deleteCapo(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM CapoDAbbigliamento WHERE id =?");
            ps.setInt(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateCapoAbbigliamento(CapoAbbigliamento capo) {
        try (Connection con = ConPool.getConnection()) {

            if (capo.getNome() != null && !capo.getNome().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "nome", capo.getNome());
            }
            if (capo.getDescrizione() != null && !capo.getDescrizione().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "descrizione", capo.getDescrizione());
            }
            if (capo.getMateriale() != null && !capo.getMateriale().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "materiale", capo.getMateriale());
            }
            if (capo.getColore() != null && !capo.getColore().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "colore", capo.getColore());
            }
            if (capo.getStile() != null && !capo.getStile().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "stile", capo.getStile());
            }
            if (capo.getStagione() != null && !capo.getStagione().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "stagione", capo.getStagione());
            }
            if (capo.getImmagine() != null && !capo.getImmagine().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "immagine", capo.getImmagine());
            }
            if (capo.getCategoria() != null && !capo.getCategoria().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "categoria", capo.getCategoria());
            }
            if (capo.getParteDelCorpo() != null && !capo.getParteDelCorpo().isEmpty()) {
                aggiornaCampo(con, capo.getId(), "parteDelCorpo", capo.getParteDelCorpo());
            }
            if (capo.getIdUtente() != -1) { // Verifica che idUtenteRegistrato sia diverso da -1
                aggiornaCampo(con, capo.getId(), "idUtenteRegistrato", capo.getIdUtente());
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void aggiornaCampo(Connection connection, int idCapo, String campo, Object valore) throws SQLException {
        String query = "UPDATE CapoDAbbigliamento SET " + campo + " = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (valore instanceof Integer) {
                preparedStatement.setInt(1, (Integer) valore);
            } else if (valore instanceof String) {
                preparedStatement.setString(1, (String) valore);
            } else if (valore instanceof Double) {
                preparedStatement.setDouble(1, (Double) valore);
            }

            preparedStatement.setInt(2, idCapo);

            int righeAggiornate = preparedStatement.executeUpdate();

            if (righeAggiornate > 0) {
                System.out.println("Aggiornamento completato con successo per il capo d'abbigliamento con ID " + idCapo);
            } else {
                System.out.println("Nessun capo d'abbigliamento trovato con ID " + idCapo);
            }
        }
    }


    public List<CapoAbbigliamento> getCapiByIdOufit(int idOutfit){
        List<CapoAbbigliamento> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT c.* FROM CapoDAbbigliamento c JOIN DettagliOutfit d ON c.Id = d.IdCapo JOIN Outfit o ON d.IdOutfit = o.Id WHERE o.Id = ?");
            ps.setInt(1, idOutfit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id= rs.getInt("id");
                int idU= rs.getInt("idUtenteRegistrato");
                String nome= rs.getString("Nome");
                String descrizione=rs.getString("Descrizione");
                String materiale=rs.getString("Materiale");
                String colore =rs.getString("Colore");
                String stile= rs.getString("Stile");
                String stagione= rs.getString("Stagione");
                String immagine= rs.getString("Immagine");
                String categoria= rs.getString("Categoria");
                String parteDelCorpo=rs.getString("ParteDelCorpo");

                CapoAbbigliamento c= new CapoAbbigliamento(idU,nome,descrizione,materiale,colore,stile,stagione,immagine,categoria,parteDelCorpo);
                c.setId(id);
                list.add(c);

            }
            ConPool.closeConnection(con);
            return list;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public CapoAbbigliamento getCapoByCategoriaMaterialeColore(String categoria, String materiale, String colore){
        try (Connection con = ConPool.getConnection()){

            PreparedStatement ps = con.prepareStatement("SELECT * FROM CapoDAbbigliamento c where c.categoria = ? AND c.materiale=? AND c.colore=?");
            ps.setString(1,categoria);
            ps.setString(2,materiale);
            ps.setString(3,colore);

            ResultSet rs = ps.executeQuery();
            CapoAbbigliamento c = null;
            if(rs.next()){
                int id= rs.getInt("id");
                int idU= rs.getInt("idUtenteRegistrato");
                String nome= rs.getString("Nome");
                String descrizione=rs.getString("Descrizione");
                String mat=rs.getString("Materiale");
                String col =rs.getString("Colore");
                String stile= rs.getString("Stile");
                String stagione= rs.getString("Stagione");
                String immagine= rs.getString("Immagine");
                String cat= rs.getString("Categoria");
                String parteDelCorpo=rs.getString("ParteDelCorpo");

                c= new CapoAbbigliamento(idU,nome,descrizione,mat,col,stile,stagione,immagine,cat,parteDelCorpo);
                c.setId(id);
            }
            ConPool.closeConnection(con);
            return c;

        }catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }







}
