package challenge.futurefocus.Dtos;
import challenge.futurefocus.models.TipoUsuario;
import java.time.LocalDate;

public record UsuarioDto(
        int id,
        String nome,
        String email,
        String cpf,
        String FaixaEtaria,
        LocalDate nascimento,
        TipoUsuario nivelAcesso
) {

}