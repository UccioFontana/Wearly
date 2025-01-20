package model;


import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConPool {
    private static DataSource datasource;

    public static Connection getConnection() throws SQLException, IOException {
        if (datasource == null) {
            // Carica il file di configurazione
            Properties props = new Properties();
            String configFilePath = "/Users/pietro/IdeaProjects/pd/Wearly/src/main/java/model/db_properties.properties"; // Percorso del file
            FileInputStream input = new FileInputStream(configFilePath);
            props.load(input);

            // Configura il pool di connessione
            PoolProperties p = new PoolProperties();
            p.setUrl(props.getProperty("db.url"));
            p.setUsername(props.getProperty("db.username"));
            p.setPassword(props.getProperty("db.password"));
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);

            // Crea il datasource
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connessione chiusa.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Ottieni una connessione dal pool
            connection = ConPool.getConnection();
            System.out.println("Connessione al database avvenuta con successo!");

            // Esegui la query per visualizzare tutti gli utenti
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM UtenteRegistrato";
            ResultSet rs = stmt.executeQuery(query);

            // Stampa i risultati
            System.out.println("Utenti nel database:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("nome");


                System.out.println("ID: " + id + ", nome: " + username  );
            }

        } catch (SQLException e) {
            System.err.println("Errore durante la connessione al database:");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Chiudi la connessione
            ConPool.closeConnection(connection);
        }
    }

}
