package CRUD;

import java.sql.*;

public class CrudContaPoupanca {
    private Connection connection;
    private int userCPF;
    private String Nome;
    private int NumeroConta;
    private float Saldo;
    private float Taxa;
    private CrudLog crudlog;
    
    //Verificação da Conta com Com as variaveis obtidas pelo CrudPessoa, utilizando o try/ catch para se caso houver erros com o Banco de Dados
    public CrudContaPoupanca(CrudPessoa Usuario) {
        this.userCPF = Usuario.getCPF();
        this.Nome = Usuario.getNome();
        this.crudlog = new CrudLog();

        try {
            connection = DatabaseManager.getConnection();
            if (ContaPoupancaExiste()){
                this.NumeroConta = PegarNumContaPoupancaBD();
                this.Saldo = PegarSaldoContaPoupancaBD();
                this.Taxa = PegarTaxaContaPoupancaBD();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //verificando se a Conta existe
    public boolean ContaPoupancaExiste(){
        String sql = "SELECT * FROM contapoupanca WHERE CPF = ?";

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
            System.out.print("Error");
            return false;
        }
    }
    

    // criando um contapoupanca
    public void createContaPoupanca(float saldo, float taxa) {
        String sql = "INSERT INTO contapoupanca (CPF , Nome, saldo, taxa) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            statement.setString(2, this.Nome);
            statement.setString(3, saldo+"");
            statement.setString(4, taxa+"");

            statement.executeUpdate();

            crudlog.log("ContaPoupança", String.valueOf(statement));

            this.NumeroConta = PegarNumContaPoupancaBD();
            this.Saldo = PegarSaldoContaPoupancaBD();
            this.Taxa = PegarTaxaContaPoupancaBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //deletar usuario
    public void deletContaPoupanca(){
        String sql = "DELETE FROM contapoupanca WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.NumeroConta));

            statement.executeUpdate();

            crudlog.log("ContaPoupança", String.valueOf(statement));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Numero da Conta
    //Pegar o Numero da conta do banco de dados
    public int PegarNumContaPoupancaBD(){
        String sql = "SELECT NumeroConta FROM contapoupanca WHERE CPF = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.NumeroConta = Integer.parseInt(resultSet.getString("NumeroConta"));
                return Integer.parseInt(resultSet.getString("NumeroConta"));
            } else {
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //Pegar o Numero da conta da Variavel "NumeroConta"
    public int PegarNumContaPoupanca(){
        return this.NumeroConta;
    }

    //Saldo
    //atualizar Alterar Saldo da Conta do banco de dados
    public void AlterarSaldoContaPoupanca(Float saldo){
        String sql = "UPDATE contapoupanca SET saldo = ? WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, saldo + "");
            statement.setString(2, Integer.toString(this.NumeroConta));
            statement.executeUpdate();

            crudlog.log("ContaPoupança", String.valueOf(statement));

            this.Saldo = saldo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Pegar o Saldo da Conta do banco de dados
    public float PegarSaldoContaPoupancaBD(){
        String sql = "SELECT saldo FROM contapoupanca WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.NumeroConta));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.Saldo = Float.parseFloat(resultSet.getString("saldo"));
                return Float.parseFloat(resultSet.getString("saldo"));
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Pegar o Saldo da Conta da Variavel "Saldo"
    public float PegarSaldoContaPoupanca(){
        return this.Saldo;
    }

    //Taxa
    // Alterar a Taxa da Conta do banco de dados
    public void AlterarTaxaContaPoupancaBD(Float saldo){
        String sql = "UPDATE contapoupanca SET taxa = ? WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, saldo + "");
            statement.setString(2, Integer.toString(this.NumeroConta));
            statement.executeUpdate();

            crudlog.log("ContaPoupança", String.valueOf(statement));

            this.Taxa = saldo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Pegar a Taxa da Conta do banco de dados
    public float PegarTaxaContaPoupancaBD(){
        String sql = "SELECT taxa FROM contapoupanca WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.NumeroConta));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.Taxa = Float.parseFloat(resultSet.getString("taxa"));
                return Float.parseFloat(resultSet.getString("taxa"));
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Pegar a Taxa da Conta da Variavel "Taxa"
    public float PegarTaxaContaPoupanca(){
        return this.Taxa;
    }

    //Pegar Todos os nomes existente dentro da Tabela contapoupanca no banco de dados
    public void PegarTodosUsuarios(){
        String sql = "SELECT * FROM contapoupanca";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Nenhum Usuario encontrado na conta poupança!");
            } else {
                System.out.println("Usuarios na conta poupança: ");
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
