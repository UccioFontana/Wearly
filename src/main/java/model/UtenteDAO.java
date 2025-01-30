package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public Utente getUserByEmail(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM UtenteRegistrato WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String em = rs.getString("email");
                String password = rs.getString("password");
                Utente user = new Utente(nome,cognome,em,password);
                user.setId(id);
                ConPool.closeConnection(con);
                return user;
            }
            ConPool.closeConnection(con);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utente> getAdmin(){
        List<Utente> list = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM Amministratore ");
            ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String em = rs.getString("email");
                String password = rs.getString("password");
                Utente user = new Utente(nome,cognome,em,password);
                user.setId(id);
                list.add(user);
            }
            ConPool.closeConnection(con);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean doSave(Utente user) {
        if (this.getUserByEmail(user.getEmail()) == null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO utenteRegistrato (nome,cognome,email,password) VALUES(?,?,?,?,?)");
                ps.setString(1, user.getNome());
                ps.setString(2, user.getCognome());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ConPool.closeConnection(con);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }





}
