package CRUD;
import java.sql.*;

public class CrudPessoa {
    private Connection connection;
    private int userCPF;
    private String Nome;
    private CrudLog crudlog;

    //construtor
    public CrudPessoa(int CPF) {
        this.crudlog = new CrudLog();
        this.userCPF = CPF;
        try {
            connection = DatabaseManager.getConnection();

            if (UsuarioExiste() == true){
                this.Nome = getNomeUsuarioBD();
            } else {
                this.Nome = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //deletar usuario
    public void deletPessoa(){
        String sql = "DELETE FROM pessoa WHERE CPF = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            statement.executeUpdate();

            this.crudlog.log("Pessoa", String.valueOf(statement));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //verificando se o usuario existe
    public boolean UsuarioExiste(){
        String sql = "SELECT * FROM pessoa WHERE CPF = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (java.lang.NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    // criando um usuario
    public void createUserPessoa(String Nome) {
        String sql = "INSERT INTO pessoa (CPF, Nome) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            statement.setString(2, Nome);

            statement.executeUpdate();

            this.crudlog.log("Pessoa", String.valueOf(statement));

            this.Nome = Nome;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //pegando O Nome dentro da tabela do pessoa
    public String getNomeUsuarioBD(){
        String sql = "SELECT Nome FROM pessoa WHERE CPF = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.Nome = resultSet.getString("Nome");
                return this.Nome;
            }  else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (java.lang.NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }

    //retornar CPF
    public int getCPF(){
        return this.userCPF;
    }

    //retornar Nome
    public String getNome(){
        return this.Nome;
    }

    //Pegar Todos os nomes existente dentro da Tabela pessoa no banco de dados
    public void PegarTodosUsuarios(){
        String sql = "SELECT * FROM pessoa";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Nenhum Usuario encontrado na conta pessoa!");
            } else {
                System.out.println("Usuarios na conta pessoa: ");
                while (true) {
                    System.out.println(resultSet.getString("Nome"));
                    if(resultSet.isLast()){
                        break;
                    } else {
                        resultSet.next();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.lang.NullPointerException e){
            e.printStackTrace();
        }
    }

}
