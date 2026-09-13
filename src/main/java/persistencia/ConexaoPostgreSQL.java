package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



    public class ConexaoPostgreSQL {
        private String host;
        private String port;
        private String dbname;
        private String username;
        private String password;

        public Connection getConexao() throws SQLException {
            this.port = "5432";
            this.host = "localhost";
            this.dbname = "spoti_pobre";
            this.username = "postgres";
            this.password = "postgres";
            String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.dbname;
            return DriverManager.getConnection(url, this.username, this.password);

        }

    }

