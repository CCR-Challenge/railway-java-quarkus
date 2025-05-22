package challenge.futurefocus.resources;

import challenge.futurefocus.Dtos.IntervencaoDto;
import challenge.futurefocus.models.Intervencao;
import challenge.futurefocus.models.StatusIntervencao;
import challenge.futurefocus.repositories.IntervencaoRepository;
import challenge.futurefocus.service.IntervencaoService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

@Path("/intervencoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IntervencaoResource {

    private final IntervencaoRepository repository = new IntervencaoRepository();
    private final IntervencaoService service = new IntervencaoService();

    //lista as intervenções que ainda estão em abertas
    @GET
    @Path("/ativas")
    public Response listarAtivas() {
        List<IntervencaoDto> abertas = repository.get().stream()
                .filter(intervencao -> intervencao.getStatus() != StatusIntervencao.RESOLVIDA)
                .map(intervencao -> new IntervencaoDto(
                        intervencao.getId(),
                        intervencao.getTempoResposta(),
                        intervencao.getStatus(),
                        intervencao.getDataHora(),
                        "Estação " + intervencao.getIdEstacao(),
                        "Usuário " + intervencao.getIdUsuario()
                ))
                .collect(Collectors.toList());

        return Response.ok(abertas).build();
    }

    //lista as intervenções especificas pela estação, de acordo com ela
    @GET
    @Path("/estacao/{id}")
    public Response listarPorEstacao(@PathParam("id") int idEstacao) {
        List<Intervencao> resultado = repository.get().stream()
                .filter(intervencao -> intervencao.getIdEstacao() == idEstacao)
                .collect(Collectors.toList());

        return Response.ok(resultado).build();
    }

    //cria uma intervenção
    @POST
    public Response criarIntervencao(Intervencao intervencao) {
        if (!service.validateIntervencao(intervencao)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Todos os campos obrigatórios devem ser preenchidos.").build();
        }

        repository.add(intervencao);
        return Response.status(Response.Status.CREATED)
                .entity("Intervenção registrada com sucesso.").build();
    }

    //avança a intervenção para o proximo estagio
    @PUT
    @Path("/{id}/avancar-status")
    public Response avancarStatus(@PathParam("id") int id) {
        Optional<Intervencao> intervencaoOpt = repository.getById(id);

        if (intervencaoOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Intervenção não encontrada.").build();
        }

        Intervencao intervencao = intervencaoOpt.get();
        StatusIntervencao statusAtual = intervencao.getStatus();
        StatusIntervencao proximo;

        switch (statusAtual) {
            case PENDENTE -> proximo = StatusIntervencao.EM_ANDAMENTO;
            case EM_ANDAMENTO -> proximo = StatusIntervencao.RESOLVIDA;
            default -> {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Intervenção já RESOLVIDA.").build();
            }
        }
        intervencao.setStatus(proximo);
        return Response.ok("Status atualizado para " + proximo)
                .build();
    }
}
