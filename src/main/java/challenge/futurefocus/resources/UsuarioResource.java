package challenge.futurefocus.resources;

import challenge.futurefocus.Dtos.UsuarioDto;
import challenge.futurefocus.models.Usuario;
import challenge.futurefocus.repositories.UsuarioRepository;
import challenge.futurefocus.service.UsuarioService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioRepository repository = new UsuarioRepository();
    private final UsuarioService service = new UsuarioService();

    //retorna todos os usuarios
    @GET
    @Path("/ativos")
    public Response listarUsuariosAtivos() {
        List<UsuarioDto> dtos = repository.get().stream()
                .map(usuario -> new UsuarioDto(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getCpf(),
                        usuario.getfaixaEtaria(),
                        usuario.getNascimento(),
                        usuario.getNivelAcesso()
                ))
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    //cadastra um usuario novo
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarUsuario(Usuario usuario) {
        if (!service.validateUsuario(usuario)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Preencha os campos obrigatorios!").build();
        }

        repository.add(usuario);
        return Response.status(Response.Status.CREATED)
                .entity("Usuario cadastrado com sucesso.").build();
    }

    //deleta um usuario pelo id
    @DELETE
    @Path("/{id}")
    public Response deletarUsuario(@PathParam("id") int id) {
        Optional<Usuario> user = repository.getById(id);

        if (user.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado.").build();
        }

        repository.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    // faz a verificação para ver se e-mail e senha cofnerem e retorna o tipo de Usuario *TipoUsuario
    @GET
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        List<Usuario> usuarios = repository.get();

        Optional<Usuario> encontrado = usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getSenha().equals(senha))
                .findFirst();

        if (encontrado.isPresent()) {
            var usuario = encontrado.get();
            String resposta = usuario.getNivelAcesso().toString().equals("ADMIN")
                    ? "Login valido. Bem-vindo, Admin " + usuario.getNome() + "!"
                    : "Login valido. Bem-vindo, " + usuario.getNome() + "!";

            return Response.ok(resposta).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Credenciais invalidas.")
                .build();
    }
}
