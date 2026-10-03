package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.javalin.Javalin;
import negocio.Usuario;
import persistencia.UsuarioDAO;

public class UsuarioController {

    
    private UsuarioDAO dao = new UsuarioDAO(); 

    
    public UsuarioController(Javalin app) {
        
        app.get("/usuarios", ctx -> {
            ArrayList<Usuario> vet = dao.listar();
            Map<String, Object> map = new HashMap<>();
            map.put("vetUsuario", vet);
            ctx.render("/templates/usuarios/listagem.html", map);
        });

        app.get("/usuarios/tela_adicionar", ctx -> {
            ctx.render("/templates/usuarios/tela_adicionar.html");
        });

        app.get("/usuarios/tela_alterar/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = dao.obter(id);
            Map<String, Object> map = new HashMap<>();
            map.put("usuario", usuario);
            ctx.render("/templates/usuarios/tela_alterar.html", map);
        });

        app.post("/usuarios/alterar", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            String nome = ctx.formParam("nome");
            String email = ctx.formParam("email");
            String senha = ctx.formParam("senha");
            String dataNascimento = ctx.formParam("data_nascimento");
            
            Usuario usuarioNovo = new Usuario();
            usuarioNovo.setId(id);
            usuarioNovo.setNome(nome);
            usuarioNovo.setEmail(email);
            
            usuarioNovo.setSenha((senha == null || senha.isBlank()) ? null : senha);
            
            if (dataNascimento != null && !dataNascimento.isBlank()) {
                usuarioNovo.setDataNascimento(LocalDate.parse(dataNascimento));
            }

            boolean resultado = dao.atualizar(usuarioNovo);
            if (resultado) {
                ctx.redirect("/usuarios");
            } else {
                ctx.html("Não foi possível salvar as alterações. O e-mail informado já pode estar cadastrado para outro usuário.");
            }
        });

        app.post("/usuarios/excluir", ctx -> {
            int id = Integer.parseInt(ctx.formParam("id"));
            boolean resultado = dao.deletar(id);
            if (resultado) {
                ctx.redirect("/usuarios");
            } else {
                ctx.html("Erro ao excluir usuário.");
            }
        });

        app.post("/usuarios/adicionar", ctx -> {
            String nome = ctx.formParam("nome");
            String email = ctx.formParam("email");
            String senha = ctx.formParam("senha");
            String dataNascimento = ctx.formParam("data_nascimento");
            
            Usuario usuarioNovo = new Usuario();
            usuarioNovo.setNome(nome);
            usuarioNovo.setEmail(email);
            usuarioNovo.setSenha(senha);
            
            if (dataNascimento != null && !dataNascimento.isBlank()) {
                usuarioNovo.setDataNascimento(LocalDate.parse(dataNascimento));
            }

            boolean resultado = dao.salvar(usuarioNovo);
            if (resultado) {
                ctx.redirect("/usuarios");
            } else {
                ctx.html("Não foi possível criar o usuário. O e-mail informado já pode estar cadastrado.");
            }
        });
    }
}