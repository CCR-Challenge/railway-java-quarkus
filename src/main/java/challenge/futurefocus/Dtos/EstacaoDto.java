package challenge.futurefocus.Dtos;
import java.time.LocalTime;

public record EstacaoDto(
        int id,
        String nome,
        String localEstacao,
        int capacidadePassageiros,
        LocalTime horarioFuncionamento
) {

}
