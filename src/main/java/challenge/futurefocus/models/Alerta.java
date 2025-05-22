package challenge.futurefocus.models;

import java.time.LocalDateTime;

public class Alerta extends _BaseEntity {
    private String mensagem;
    private TipoAlerta tipo;
    private LocalDateTime dataHora;
    private int idEstacao;
    private boolean ativo = true;

    public Alerta() {}

    public Alerta(int id, String mensagem, TipoAlerta tipo, LocalDateTime dataHora, int idEstacao) {
        this.id = id;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.idEstacao = idEstacao;
        this.ativo = true;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public TipoAlerta getTipo() {
        return tipo;
    }

    public void setTipo(TipoAlerta tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id=" + id +
                ", mensagem='" + mensagem + '\'' +
                ", tipo=" + tipo +
                ", dataHora=" + dataHora +
                ", idEstacao=" + idEstacao +
                ", ativo=" + ativo +
                '}';
    }
}
