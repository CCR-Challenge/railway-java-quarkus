package challenge.futurefocus.repositories;

import challenge.futurefocus.infrastructure.DatabaseConfig;
import challenge.futurefocus.models.Usuario;
import challenge.futurefocus.models.TipoUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements _CrudRepository<Usuario> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRepository.class);

    @Override
    public void add(Usuario usuario) {
        String query = "INSERT INTO CCR_USUARIOS (id_usuario, nome, email, cpf, tipo, senha, nascimento, nivel_acesso, ativo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setInt(1, usuario.getId());
            statement.setString(2, usuario.getNome());
            statement.setString(3, usuario.getEmail());
            statement.setString(4, usuario.getCpf());
            statement.setString(5, usuario.getfaixaEtaria());
            statement.setString(6, usuario.getSenha());
            statement.setDate(7, Date.valueOf(usuario.getNascimento()));
            statement.setString(8, usuario.getNivelAcesso().toString());

            statement.executeUpdate();

        } catch (Exception exception) {
            LOGGER.error("Erro ao adicionar usuário", exception);
        }
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM CCR_USUARIOS WHERE id_usuario = ?";

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (Exception exception) {
            LOGGER.error("Erro ao deletar usuário por ID: {}", id, exception);
        }
    }

    @Override
    public List<Usuario> get() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM CCR_USUARIOS WHERE ativo = 1";

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(query);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("ID_USUARIO"),
                        resultSet.getString("NOME"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("CPF"),
                        resultSet.getString("FAIXA_ETARIA"),
                        resultSet.getString("SENHA"),
                        resultSet.getDate("NASCIMENTO").toLocalDate(),
                        TipoUsuario.valueOf(resultSet.getString("NIVEL_ACESSO"))
                );
                usuarios.add(usuario);
            }

        } catch (Exception exception) {
            LOGGER.error("Erro ao listar usuários", exception);
        }

        return usuarios;
    }

    @Override
    public Optional<Usuario> getById(int id) {
        String query = "SELECT * FROM CCR_USUARIOS WHERE id_usuario = ? AND ativo = 1";

        try (var connection = DatabaseConfig.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            var resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("ID_USUARIO"),
                        resultSet.getString("NOME"),
                        resultSet.getString("EMAIL"),
                        resultSet.getString("CPF"),
                        resultSet.getString("FAIXA_ETARIA"),
                        resultSet.getString("SENHA"),
                        resultSet.getDate("NASCIMENTO").toLocalDate(),
                        TipoUsuario.valueOf(resultSet.getString("NIVEL_ACESSO"))
                );
                return Optional.of(usuario);
            }

        } catch (Exception exception) {
            LOGGER.error("Erro ao buscar usuário por ID: {}", id, exception);
        }

        return Optional.empty();
    }
}
