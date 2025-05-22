package challenge.futurefocus.Dtos;

import challenge.futurefocus.models.TipoAlerta;
import java.time.LocalDateTime;

public record AlertaDto(
        int id,
        String mensagem,
        TipoAlerta tipo,
        LocalDateTime dataHora,
        String nomeEstacao,
        boolean ativo
) {}
