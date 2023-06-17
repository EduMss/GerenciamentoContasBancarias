package CRUD;

import java.sql.*;

public class CrudContaCorrente {
    private Connection connection;
    private int userCPF;
    private String Nome;
    private int NumeroConta;
    private float Saldo;
    private float ChequeEspecial;
    private CrudLog crudlog;

    //Construtor da Conta Corrente
    public CrudContaCorrente(CrudPessoa Usuario) {
        this.userCPF = Usuario.getCPF();
        this.Nome = Usuario.getNome();
        this.crudlog = new CrudLog();
        
        try {
            connection = DatabaseManager.getConnection();
            if (ContaCorrenteExiste()){
                this.NumeroConta = PegarNumContaCorrenteBD();
                this.Saldo = PegarSaldoContaCorrenteBD();
                this.ChequeEspecial = PegarChequeEspecialContaCorrenteBD();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //verificando se a Conta existe
    public boolean ContaCorrenteExiste(){
        String sql = "SELECT * FROM contacorrente WHERE CPF = ?";

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


    // criando um contacorrente
    public void createContaCorrente(float saldo) {
        String sql = "INSERT INTO contacorrente (CPF , Nome, saldo, ChequeEspecial) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.userCPF));
            statement.setString(2, this.Nome);
            statement.setString(3, saldo+"");
            statement.setString(4, "500.00");

            statement.executeUpdate();

            crudlog.log("ContaCorrente", String.valueOf(statement));

            this.NumeroConta = PegarNumContaCorrenteBD();
            this.Saldo = PegarSaldoContaCorrenteBD();
            this.ChequeEspecial = PegarChequeEspecialContaCorrenteBD();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //deletar Conta
    public void deletContaCorrente(){
        String sql = "DELETE FROM contacorrente WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.NumeroConta));
            statement.executeUpdate();

            crudlog.log("ContaCorrente", String.valueOf(statement));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Numero da Conta

    //Pegar o Numero da conta do banco de dados
    public int PegarNumContaCorrenteBD(){
        String sql = "SELECT NumeroConta FROM contacorrente WHERE CPF = ?";

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
    public int PegarNumContaCorrente(){
        return this.NumeroConta;
    }

    //Saldo
    //atualizar Alterar Saldo da Conta do banco de dados
    public void AlterarSaldoContaCorrente(Float saldo){
        String sql = "UPDATE contacorrente SET saldo = ? WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, saldo + "");
            statement.setString(2, Integer.toString(this.NumeroConta));
            statement.executeUpdate();

            crudlog.log("ContaCorrente", String.valueOf(statement));

            this.Saldo = saldo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Pegar o Saldo da Conta do banco de dados
    public float PegarSaldoContaCorrenteBD(){
        String sql = "SELECT saldo FROM contacorrente WHERE NumeroConta = ?";

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
    public float PegarSaldoContaCorrente(){
        return this.Saldo;
    }

    //Cheque Especial
    //Alterar o valor do Cheque Especial do Banco de dados
    public void AlterarChequeEspecialContaCorrenteBD(Float ChequeEspecial){
        String sql = "UPDATE contacorrente SET ChequeEspecial = ? WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, ChequeEspecial + "");
            statement.setString(2, Integer.toString(this.NumeroConta));
            statement.executeUpdate();

            crudlog.log("ContaCorrente", String.valueOf(statement));

            this.ChequeEspecial = ChequeEspecial;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Pegar o valor do Cheque Especial do Banco de dados
    public float PegarChequeEspecialContaCorrenteBD(){
        String sql = "SELECT ChequeEspecial FROM contacorrente WHERE NumeroConta = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, Integer.toString(this.NumeroConta));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                this.ChequeEspecial = Float.parseFloat(resultSet.getString("ChequeEspecial"));
                return Float.parseFloat(resultSet.getString("ChequeEspecial"));
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //Pegar o valor do Cheque Especial da Variavel 'ChequeEspecial'
    public float PegarChequeEspecialContaCorrente(){
        return this.ChequeEspecial;
    }

    //Pegar Todos os nomes existente dentro da Tabela contacorrente no banco de dados
    public void PegarTodosUsuarios(){
        String sql = "SELECT * FROM contacorrente";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("Nenhum Usuario encontrado na conta corrente!");
            } else {
                System.out.println("Usuarios na conta corrente: ");
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
