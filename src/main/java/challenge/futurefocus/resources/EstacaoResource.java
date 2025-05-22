package challenge.futurefocus.resources;

import challenge.futurefocus.Dtos.EstacaoDto;
import challenge.futurefocus.models.Estacao;
import challenge.futurefocus.repositories.EstacaoRepository;
import challenge.futurefocus.service.EstacaoService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/estacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstacaoResource {

    private final EstacaoRepository repository = new EstacaoRepository();
    private final EstacaoService service = new EstacaoService();

    //lista todas as estações
    @GET
    public Response listarTodas() {
        List<EstacaoDto> dtos = repository.get().stream()
                .map(estacao -> new EstacaoDto(
                        estacao.getId(),
                        estacao.getNome(),
                        estacao.getLocalEstacao(),
                        estacao.getCapacidadePassageiros(),
                        estacao.getHorarioFuncionamento()
                ))
                .collect(Collectors.toList());

        return Response.ok(dtos).build();
    }

    //cadastra uma nova estação
    @POST
    public Response cadastrar(Estacao estacao) {
        if (!service.validateEstacao(estacao)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados invalidos.").build();
        }

        repository.add(estacao);
        return Response.status(Response.Status.CREATED)
                .entity("Estação cadastrada com sucesso.").build();
    }

    //filtra a estação de acordo com o seu nome e localização
    @GET
    @Path("/buscar")
    public Response buscarEstacoes(@QueryParam("nome") String nome) {
        List<EstacaoDto> resultados = repository.get().stream()
                .filter(estacao -> nome == null ||
                        estacao.getNome().toLowerCase().contains(nome.toLowerCase()) ||
                        estacao.getLocalEstacao().toLowerCase().contains(nome.toLowerCase()))
                .map(estacao -> new EstacaoDto(
                        estacao.getId(),
                        estacao.getNome(),
                        estacao.getLocalEstacao(),
                        estacao.getCapacidadePassageiros(),
                        estacao.getHorarioFuncionamento()
                ))
                .collect(Collectors.toList());

        return Response.ok(resultados).build();
    }}
