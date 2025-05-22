package challenge.futurefocus.models;
import java.time.LocalDate;

public class Usuario extends _BaseEntity {

    private String nome;
    private String email;
    private String cpf;
    private String FaixaEtaria; // Adulto, Criança, Idoso
    private String senha;
    private LocalDate nascimento;
    private TipoUsuario nivelAcesso; //

    public Usuario() {}

    public Usuario(int id, String nome, String email, String cpf, String tipo, String senha, LocalDate nascimento, TipoUsuario nivelAcesso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.FaixaEtaria = tipo;
        this.senha = senha;
        this.nascimento = nascimento;
        this.nivelAcesso = nivelAcesso;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getfaixaEtaria() { return FaixaEtaria; }
    public void setFaixaEtaria(String tipo) { this.FaixaEtaria = tipo; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }

    public TipoUsuario getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(TipoUsuario nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", faixaEtaria='" + FaixaEtaria + '\'' +
                ", nascimento=" + nascimento +
                ", nivelAcesso=" + nivelAcesso +
                '}';
    }
}
