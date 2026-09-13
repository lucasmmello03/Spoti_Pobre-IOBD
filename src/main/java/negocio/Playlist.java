package negocio;

import java.time.LocalDateTime;

public class Playlist {

    private int id;
    private String nome;
    private LocalDateTime dataHoraCriacao;
    private boolean publica;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }
    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }
    public boolean isPublica() {
        return publica;
    }
    public void setPublica(boolean publica) {
        this.publica = publica;
    }
}