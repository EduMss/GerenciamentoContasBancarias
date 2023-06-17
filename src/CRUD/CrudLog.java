package CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudLog {
    private Connection connection;

    public CrudLog(){
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Envia um log para o Banco de dados
    public void log(String tabela, String log){
        if(connection != null){
            String sql = "INSERT INTO log (tabela, log, data) VALUES (?, ?, NOW())";
            try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
                statement.setString(1, tabela);
                statement.setString(2, log);

                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
