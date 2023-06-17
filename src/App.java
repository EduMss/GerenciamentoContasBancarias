import CRUD.CrudContaCorrente;
import CRUD.CrudContaPoupanca;
import CRUD.CrudPessoa;

public class App {
    public static CrudPessoa crudPessoa;
    public static CrudContaCorrente crudContaCorrente;
    public static CrudContaPoupanca crudContaPoupanca;
    public static void main( String[] args ){
        Tela janela = new Tela();
        janela.setVisible(true);

        //mostrar no terminal todos os usuarios cadastrado na tabela pessoa   
        crudPessoa = new CrudPessoa(0);
        crudPessoa.PegarTodosUsuarios();

        //mostrar no terminal todos os usuarios cadastrado na tabela contacorrente
        crudContaCorrente = new CrudContaCorrente(crudPessoa);
        crudContaCorrente.PegarTodosUsuarios();

        //mostrar no terminal todos os usuarios cadastrado na tabela contapoupaca
        crudContaPoupanca = new CrudContaPoupanca(crudPessoa);
        crudContaPoupanca.PegarTodosUsuarios();
    }
}
