package challenge.futurefocus.repositories;

import challenge.futurefocus.infrastructure.DatabaseConfig;
import challenge.futurefocus.models.TipoAlerta;
import challenge.futurefocus.models.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlertaRepository implements _CrudRepository<Alerta> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertaRepository.class);

    @Override
    public void add(Alerta alerta) {
        String query = "INSERT INTO CCR_ALERTA (id_alerta, tipo, mensagem, data_hora, id_estacao, ativo) VALUES (?, ?, ?, ?, ?, 1)";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, alerta.getId());
            stmt.setString(2, alerta.getTipo().toString());
            stmt.setString(3, alerta.getMensagem());
            stmt.setTimestamp(4, Timestamp.valueOf(alerta.getDataHora()));
            stmt.setInt(5, alerta.getIdEstacao());

            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao adicionar alerta", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM CCR_ALERTA WHERE id_alerta = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao deletar alerta por ID: {}", id, e);
        }
    }

    @Override
    public List<Alerta> get() {
        List<Alerta> alertas = new ArrayList<>();
        String query = "SELECT * FROM CCR_ALERTA WHERE ativo = 1";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query);
             var resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Alerta alerta = new Alerta(
                        resultSet.getInt("ID_ALERTA"),
                        resultSet.getString("MENSAGEM"),
                        TipoAlerta.valueOf(resultSet.getString("TIPO")),
                        resultSet.getTimestamp("DATA_HORA").toLocalDateTime(),
                        resultSet.getInt("ID_ESTACAO")
                );
                alertas.add(alerta);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao listar alertas ativos", e);
        }

        return alertas;
    }

    @Override
    public Optional<Alerta> getById(int id) {
        String query = "SELECT * FROM CCR_ALERTA WHERE id_alerta = ? AND ativo = 1";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Alerta alerta = new Alerta(
                        resultSet.getInt("ID_ALERTA"),
                        resultSet.getString("MENSAGEM"),
                        TipoAlerta.valueOf(resultSet.getString("TIPO")),
                        resultSet.getTimestamp("DATA_HORA").toLocalDateTime(),
                        resultSet.getInt("ID_ESTACAO")
                );
                return Optional.of(alerta);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar alerta por ID: {}", id, e);
        }

        return Optional.empty();
    }
}