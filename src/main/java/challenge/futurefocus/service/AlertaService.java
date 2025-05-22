package challenge.futurefocus.service;

import challenge.futurefocus.models.Alerta;

public class AlertaService {

    public boolean validateAlerta(Alerta alerta) {
        if (alerta == null)
            return false;

        if (alerta.getMensagem().isBlank()
                || alerta.getTipo() == null
                || alerta.getDataHora() == null
                || alerta.getIdEstacao() <= 0)
            return false;

        return true;
    }
}