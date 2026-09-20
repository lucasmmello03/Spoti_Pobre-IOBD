package negocio;

import java.sql.Time;
import java.time.LocalDateTime;

public class Musica {

    private int id;
    private String nome;
    private Time duracao;
    private LocalDateTime data_hora_lancamento;

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
    public Time getDuracao() {
        return duracao;
    }
    public void setDuracao(Time duracao) {
        this.duracao = duracao;
    }
    public LocalDateTime getData_hora_lancamento() {
        return data_hora_lancamento;
    }
    public void setData_hora_lancamento(LocalDateTime data_hora_lancamento) {
        this.data_hora_lancamento = data_hora_lancamento;
    }
}