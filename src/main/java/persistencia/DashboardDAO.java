package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import negocio.Album;
import negocio.Musica;

public class DashboardDAO {

    public List<Album> listarCatalogo() {
        // LinkedHashMap preserva a ordem em que os álbuns chegam do banco
        Map<Integer, Album> mapaAlbuns = new LinkedHashMap<>();

        // O JOIN exigido no escopo do trabalho
        String sql = "SELECT a.id AS album_id, a.titulo, a.data_lancamento, " +
                     "m.id AS musica_id, m.nome, m.duracao " +
                     "FROM album a " +
                     "JOIN album_musica am ON a.id = am.album_id " +
                     "JOIN musica m ON am.musica_id = m.id " +
                     "ORDER BY a.titulo, m.nome;";

        try (Connection conexao = new ConexaoPostgreSQL().getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int albumId = rs.getInt("album_id");

                // Se o álbum ainda não está no mapa, cria e adiciona
                if (!mapaAlbuns.containsKey(albumId)) {
                    Album album = new Album();
                    album.setId(albumId);
                    album.setTitulo(rs.getString("titulo"));
                    
                    if (rs.getDate("data_lancamento") != null) {
                        album.setData_lancamento(rs.getDate("data_lancamento").toLocalDate());
                    }
                    mapaAlbuns.put(albumId, album);
                }

                // Cria a música
                Musica musica = new Musica();
                musica.setId(rs.getInt("musica_id"));
                musica.setNome(rs.getString("nome"));
                musica.setDuracao(rs.getTime("duracao"));

                // Adiciona a música na lista de músicas do álbum correspondente
                mapaAlbuns.get(albumId).getMusicas().add(musica);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar o Dashboard: " + e.getMessage());
        }

        // Retorna apenas a lista de álbuns já preenchida com as músicas
        return new ArrayList<>(mapaAlbuns.values());
    }
}