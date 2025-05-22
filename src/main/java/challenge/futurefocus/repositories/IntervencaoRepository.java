package challenge.futurefocus.repositories;

import challenge.futurefocus.infrastructure.DatabaseConfig;
import challenge.futurefocus.models.Intervencao;
import challenge.futurefocus.models.StatusIntervencao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IntervencaoRepository implements _CrudRepository<Intervencao> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntervencaoRepository.class);

    @Override
    public void add(Intervencao intervencao) {
        String query = "INSERT INTO CCR_INTERVENCAO (id_interv, tempo_resp, sts_interv, id_usuario, id_estacao, data_hora) VALUES (?, ?, ?, ?, ?, ?)";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, intervencao.getId());
            stmt.setTime(2, Time.valueOf(intervencao.getTempoResposta())); // LocalTime
            stmt.setString(3, intervencao.getStatus().toString());
            stmt.setInt(4, intervencao.getIdUsuario());
            stmt.setInt(5, intervencao.getIdEstacao());
            stmt.setTimestamp(6, Timestamp.valueOf(intervencao.getDataHora()));

            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao adicionar intervenção", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM CCR_INTERVENCAO WHERE id_interv = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao deletar intervenção por ID: {}", id, e);
        }
    }

    @Override
    public List<Intervencao> get() {
        List<Intervencao> lista = new ArrayList<>();
        String query = "SELECT * FROM CCR_INTERVENCAO";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query);
             var resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Intervencao intervencao = new Intervencao(
                        resultSet.getInt("ID_INTERV"),
                        resultSet.getTime("TEMPO_RESP").toLocalTime(),
                        StatusIntervencao.valueOf(resultSet.getString("STS_INTERV")),
                        resultSet.getInt("ID_ESTACAO"),
                        resultSet.getInt("ID_USUARIO"),
                        resultSet.getTimestamp("DATA_HORA").toLocalDateTime()
                );
                lista.add(intervencao);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao listar intervenções", e);
        }

        return lista;
    }

    @Override
    public Optional<Intervencao> getById(int id) {
        String query = "SELECT * FROM CCR_INTERVENCAO WHERE id_interv = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Intervencao intervencao = new Intervencao(
                        resultSet.getInt("ID_INTERV"),
                        resultSet.getTime("TEMPO_RESP").toLocalTime(),
                        StatusIntervencao.valueOf(resultSet.getString("STS_INTERV")),
                        resultSet.getInt("ID_ESTACAO"),
                        resultSet.getInt("ID_USUARIO"),
                        resultSet.getTimestamp("DATA_HORA").toLocalDateTime()
                );
                return Optional.of(intervencao);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar intervenção por ID: {}", id, e);
        }

        return Optional.empty();
    }
}
