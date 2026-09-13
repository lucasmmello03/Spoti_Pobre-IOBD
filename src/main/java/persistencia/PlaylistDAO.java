package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Playlist;

public class PlaylistDAO {

    public boolean salvarComDono(Playlist playlist, int usuarioId) {
        // Usa RETURNING id para pegar o ID gerado pelo banco
        String sqlPlaylist = "INSERT INTO playlist (nome, publica) VALUES (?, ?) RETURNING id;";
        String sqlVinculo = "INSERT INTO usuario_playlist (usuario_id, playlist_id, dono) VALUES (?, ?, true);";

        try (Connection conexao = new ConexaoPostgreSQL().getConexao()) {
            conexao.setAutoCommit(false); // Inicia a transação

            try (PreparedStatement stmtPlaylist = conexao.prepareStatement(sqlPlaylist);
                 PreparedStatement stmtVinculo = conexao.prepareStatement(sqlVinculo)) {

                // 1. Salva a playlist
                stmtPlaylist.setString(1, playlist.getNome());
                stmtPlaylist.setBoolean(2, playlist.isPublica());
                
                ResultSet rs = stmtPlaylist.executeQuery(); // Executa e pega o retorno
                
                if (rs.next()) {
                    int novaPlaylistId = rs.getInt("id");

                    // 2. Cria o vínculo de dono
                    stmtVinculo.setInt(1, usuarioId);
                    stmtVinculo.setInt(2, novaPlaylistId);
                    stmtVinculo.executeUpdate();
                }

                conexao.commit(); // Confirma ambas as operações
                return true;

            } catch (SQLException e) {
                conexao.rollback(); // Desfaz em caso de erro em qualquer insert
                System.out.println("Erro ao salvar playlist e vínculo: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro de conexão na transação da playlist: " + e.getMessage());
            return false;
        }
    }
}