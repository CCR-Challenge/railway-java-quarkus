package challenge.futurefocus.service;

import challenge.futurefocus.models.Estacao;

public class EstacaoService {

    public boolean validateEstacao(Estacao estacao) {
        if (estacao == null)
            return false;

        if (estacao.getNome().isBlank()
                || estacao.getLocalEstacao().isBlank()
                || estacao.getCapacidadePassageiros() <= 0
                || estacao.getHorarioFuncionamento() == null)
            return false;

        return true;
    }
}
