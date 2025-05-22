package challenge.futurefocus.Dtos;
import challenge.futurefocus.models.StatusIntervencao;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record IntervencaoDto(
        int id,
        LocalTime tempoResposta,
        StatusIntervencao status,
        LocalDateTime dataHora,
        String nomeEstacao,
        String nomeUsuario
) {}
