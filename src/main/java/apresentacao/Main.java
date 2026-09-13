package apresentacao;

import java.sql.SQLException;

import controller.PlaylistController;
import controller.UsuarioController;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;

public class Main {
    public static void main(String[] args) throws SQLException {
        
        Javalin app = Javalin.create(config -> {
            config.fileRenderer(new JavalinMustache());
        }).start(7070);

        //Rotas de usuario
        new UsuarioController(app);

        //Rotas de playlist
        new PlaylistController(app);

    }
}