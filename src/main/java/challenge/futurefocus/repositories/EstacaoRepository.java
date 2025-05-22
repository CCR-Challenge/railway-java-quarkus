package challenge.futurefocus.repositories;

import challenge.futurefocus.infrastructure.DatabaseConfig;
import challenge.futurefocus.models.Estacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EstacaoRepository implements _CrudRepository<Estacao> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstacaoRepository.class);

    @Override
    public void add(Estacao estacao) {
        String query = "INSERT INTO CCR_ESTACAO (id_estacao, nm_estacao, local_estacao, capacidade_passageiros, hr_funcionamento) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, estacao.getId());
            stmt.setString(2, estacao.getNome());
            stmt.setString(3, estacao.getLocalEstacao());
            stmt.setInt(4, estacao.getCapacidadePassageiros());
            stmt.setTime(5, Time.valueOf(estacao.getHorarioFuncionamento())); // LocalTime → SQL Time

            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao adicionar estação", e);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM CCR_ESTACAO WHERE id_estacao = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            LOGGER.error("Erro ao deletar estação por ID: {}", id, e);
        }
    }

    @Override
    public List<Estacao> get() {
        List<Estacao> estacoes = new ArrayList<>();
        String query = "SELECT * FROM CCR_ESTACAO";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query);
             var resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Estacao estacao = new Estacao(
                        resultSet.getInt("id_estacao"),
                        resultSet.getString("nm_estacao"),
                        resultSet.getString("local_estacao"),
                        resultSet.getInt("capacidade_passageiros"),
                        resultSet.getTime("hr_funcionamento").toLocalTime()
                );
                estacoes.add(estacao);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao listar estações", e);
        }

        return estacoes;
    }

    @Override
    public Optional<Estacao> getById(int id) {
        String query = "SELECT * FROM CCR_ESTACAO WHERE id_estacao = ?";

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, id);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Estacao estacao = new Estacao(
                        resultSet.getInt("id_estacao"),
                        resultSet.getString("nm_estacao"),
                        resultSet.getString("local_estacao"),
                        resultSet.getInt("capacidade_passageiros"),
                        resultSet.getTime("hr_funcionamento").toLocalTime()
                );
                return Optional.of(estacao);
            }

        } catch (Exception e) {
            LOGGER.error("Erro ao buscar estação por ID: {}", id, e);
        }

        return Optional.empty();
    }
}

