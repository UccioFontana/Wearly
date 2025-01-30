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



}
