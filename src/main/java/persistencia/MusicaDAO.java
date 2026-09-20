package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MusicaDAO {

    
    public boolean registrarReproducao(int musicaId, int usuarioId) {
        String sql = "INSERT INTO reproducao (musica_id, usuario_id) VALUES (?, ?);";

        try (Connection conexao = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, musicaId);
            stmt.setInt(2, usuarioId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao registrar reprodução: " + e.getMessage());
            return false;
        }
    }
}