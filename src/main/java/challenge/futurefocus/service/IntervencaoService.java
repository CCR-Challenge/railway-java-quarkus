package challenge.futurefocus.service;

import challenge.futurefocus.models.Intervencao;

public class IntervencaoService {

    public boolean validateIntervencao(Intervencao intervencao) {
        if (intervencao == null)
            return false;

        if (intervencao.getTempoResposta() == null
                || intervencao.getStatus() == null
                || intervencao.getDataHora() == null
                || intervencao.getIdEstacao() <= 0
                || intervencao.getIdUsuario() <= 0)
            return false;

        return true;
    }
}