package model;

import org.mindrot.jbcrypt.BCrypt;

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
                PreparedStatement ps = con.prepareStatement("INSERT INTO UtenteRegistrato (nome,cognome,email,password) VALUES(?,?,?,?)");
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


    public void deleteUser(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM UtenteRegistrato WHERE id =?");
            ps.setInt(1, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ConPool.closeConnection(con);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Utente> getUsers() {
        List<Utente> users = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =
                    con.prepareStatement("SELECT * FROM UtenteRegistrato");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                String em = rs.getString("email");
                String password = rs.getString("password");
                Utente user = new Utente(nome,cognome,em,password);
                user.setId(id);
                ConPool.closeConnection(con);
                users.add(user);
            }
            ConPool.closeConnection(con);
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void updateUser(Utente user) {
        try (Connection con = ConPool.getConnection()) {
            if (!user.getNome().isEmpty()) {
                aggiornaCampo(con, user.getId(), "nome", user.getNome());
            }
            if (!user.getCognome().isEmpty()) {
                aggiornaCampo(con, user.getId(), "cognome", user.getCognome());
            }
            if (!user.getEmail().isEmpty()) {
                aggiornaCampo(con, user.getId(), "email", user.getEmail());
            }
            if(!user.getPassword().isEmpty()) {
                String hashPass = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());
                aggiornaCampo(con, user.getId(), "password", hashPass);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void aggiornaCampo(Connection connection, int idUtente, String campo, Object valore) throws SQLException {
        // Query di aggiornamento utilizzando PreparedStatement
        String query = "UPDATE UtenteRegistrato SET " + campo + " = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Imposta il valore del campo nella query
            if (valore instanceof Integer) {
                preparedStatement.setInt(1, (Integer) valore);
            } else if (valore instanceof String) {
                preparedStatement.setString(1, (String) valore);
            }

            // Imposta l'ID dell'utente nella clausola WHERE
            preparedStatement.setInt(2, idUtente);

            // Eseguire l'aggiornamento
            int righeAggiornate = preparedStatement.executeUpdate();

            // Verifica quante righe sono state effettivamente aggiornate
            if (righeAggiornate > 0) {
                System.out.println("Aggiornamento completato con successo per l'utente con ID " + idUtente);
            } else {
                System.out.println("Nessun utente trovato con ID " + idUtente);
            }
        }
    }





}
