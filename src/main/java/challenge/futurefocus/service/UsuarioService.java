package challenge.futurefocus.service;

import challenge.futurefocus.models.Usuario;

public class UsuarioService {

    public boolean validateUsuario(Usuario usuario) {
        if (usuario == null)
            return false;

        if (usuario.getNome().isBlank()
                || usuario.getEmail().isBlank()
                || usuario.getCpf().isBlank()
                || usuario.getSenha().isBlank()
                || usuario.getNivelAcesso() == null
                || usuario.getNascimento() == null
                || usuario.getfaixaEtaria().isBlank())
            return false;

        return true;
    }
}
