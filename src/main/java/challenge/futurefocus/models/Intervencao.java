package challenge.futurefocus.models;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class Intervencao extends _BaseEntity {

    private LocalTime tempoResposta;
    private StatusIntervencao status;
    private int idEstacao;
    private int idUsuario;
    private LocalDateTime dataHora;

    public Intervencao() {}

    public Intervencao(int id, LocalTime tempoResposta, StatusIntervencao status, int idEstacao, int idUsuario, LocalDateTime dataHora) {
        this.id = id;
        this.tempoResposta = tempoResposta;
        this.status = status;
        this.idEstacao = idEstacao;
        this.idUsuario = idUsuario;
        this.dataHora = dataHora;
    }

    public LocalTime getTempoResposta() {
        return tempoResposta;
    }

    public void setTempoResposta(LocalTime tempoResposta) {
        this.tempoResposta = tempoResposta;
    }

    public StatusIntervencao getStatus() {
        return status;
    }

    public void setStatus(StatusIntervencao status) {
        this.status = status;
    }

    public int getIdEstacao() {
        return idEstacao;
    }

    public void setIdEstacao(int idEstacao) {
        this.idEstacao = idEstacao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Intervencao{" +
                "id=" + id +
                ", tempoResposta=" + tempoResposta +
                ", status=" + status +
                ", idEstacao=" + idEstacao +
                ", idUsuario=" + idUsuario +
                ", dataHora=" + dataHora +
                '}';
    }
}