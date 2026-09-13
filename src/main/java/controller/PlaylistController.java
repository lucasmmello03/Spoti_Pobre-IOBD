package controller;

import io.javalin.Javalin;
import negocio.Playlist;
import persistencia.PlaylistDAO;
import persistencia.UsuarioDAO;
import java.util.HashMap;
import java.util.Map;

public class PlaylistController {

    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public PlaylistController(Javalin app) {

        // Rota GET: Tem que bater com o href="/playlists/tela_adicionar" do index.html
        app.get("/playlists/tela_adicionar", ctx -> {
            Map<String, Object> map = new HashMap<>();
            map.put("usuarios", usuarioDAO.listar());
            // O caminho do render continua com .html, pois é o caminho físico do arquivo no projeto
            ctx.render("/templates/playlists/tela_adicionar.html", map);
        });

        // Rota POST: Tem que bater com o action="/playlists/adicionar" do formulário
        app.post("/playlists/adicionar", ctx -> {
            String nome = ctx.formParam("nome");
            boolean publica = ctx.formParam("publica") != null;
            int usuarioId = Integer.parseInt(ctx.formParam("usuario_id"));

            Playlist novaPlaylist = new Playlist();
            novaPlaylist.setNome(nome);
            novaPlaylist.setPublica(publica);

            boolean resultado = playlistDAO.salvarComDono(novaPlaylist, usuarioId);

            if (resultado) {
                ctx.redirect("/");
            } else {
                ctx.html("Erro ao criar playlist.");
            }
        });
    }
}