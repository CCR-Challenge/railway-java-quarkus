package challenge.futurefocus.models;

import java.time.LocalTime;

public class Estacao extends _BaseEntity {
    private String nome;
    private String localEstacao;
    private int capacidadePassageiros;
    private LocalTime horarioFuncionamento;

    public Estacao() {
    }

    public Estacao(int id, String nome, String localEstacao, int capacidadePassageiros, LocalTime horarioFuncionamento) {
        this.id = id;
        this.nome = nome;
        this.localEstacao = localEstacao;
        this.capacidadePassageiros = capacidadePassageiros;
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalEstacao() {
        return localEstacao;
    }

    public void setLocalEstacao(String localEstacao) {
        this.localEstacao = localEstacao;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public LocalTime getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(LocalTime horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    @Override
    public String toString() {
        return "Estacao{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", localEstacao='" + localEstacao + '\'' +
                ", capacidadePassageiros=" + capacidadePassageiros +
                ", horarioFuncionamento=" + horarioFuncionamento +
                '}';
    }
}
