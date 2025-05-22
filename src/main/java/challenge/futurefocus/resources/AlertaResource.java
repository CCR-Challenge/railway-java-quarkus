package challenge.futurefocus.resources;

import challenge.futurefocus.Dtos.AlertaDto;
import challenge.futurefocus.models.Alerta;
import challenge.futurefocus.repositories.AlertaRepository;
import challenge.futurefocus.service.AlertaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/alertas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlertaResource {

    private final AlertaRepository repository = new AlertaRepository();
    private final AlertaService service = new AlertaService();

    //Retorna todos os alertas ativos, onde eles vem com a informação formatada

    @GET
    public Response listarAtivos() {
        List<AlertaDto> ativos = repository.get().stream()
                .filter(Alerta::isAtivo)
                .map(alerta -> new AlertaDto(
                        alerta.getId(),
                        alerta.getMensagem(),
                        alerta.getTipo(),
                        alerta.getDataHora(),
                        "Estação " + alerta.getIdEstacao(),
                        alerta.isAtivo()
                ))
                .collect(Collectors.toList());

        return Response.ok(ativos).build();
    }

    //retorna os alertas associados a cada estação
    @GET
    @Path("/estacao/{id}")
    public Response alertasPorEstacao(@PathParam("id") int idEstacao) {
        List<Alerta> alertas = repository.get().stream()
                .filter(a -> a.getIdEstacao() == idEstacao && a.isAtivo())
                .collect(Collectors.toList());

        return Response.ok(alertas).build();
    }

    //cadastra um alerta novo no sistema
    @POST
    public Response criarAlerta(Alerta alerta) {
        if (!service.validateAlerta(alerta)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Dados obrigatorios ausentes para o cadastro.").build();
        }

        repository.add(alerta);
        return Response.status(Response.Status.CREATED)
                .entity("Alerta cadastrado com sucesso.").build();
    }

    //deleta um alerta existente
    @PUT
    @Path("/{id}/desativar")
    public Response desativarAlerta(@PathParam("id") int id) {
        Optional<Alerta> alerta = repository.getById(id);

        if (alerta.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Alerta não encontrado.").build();
        }

        Alerta a = alerta.get();
        a.setAtivo(false);
        return Response.noContent().build();
    }
}
