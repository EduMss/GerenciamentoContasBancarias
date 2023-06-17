import CRUD.CrudPessoa;

public class Pessoa {
    private String nome;
    private int cpf;
    private CrudPessoa crudPessoa;
    private boolean ContaExiste;

    //Contrutor Pessoa, ele verifica se o Usuario existe no banco de dados, se exister ele pega o nome cadastrado, se não existir, ele seta a varialvel ContaExite como false, para no programa possa ser feita o cadastro
    Pessoa(int cpf){
        this.cpf = cpf;
        this.crudPessoa = new CrudPessoa(this.cpf);
        if(crudPessoa.UsuarioExiste()){
            this.ContaExiste = true;
            this.nome = this.crudPessoa.getNome();
        } else {
            this.ContaExiste = false;
        }
    }

    //ele retorna a Variavel Nome
    public String PegarNome(){
        return this.nome;
    }
    //ele retorna a Variavel CPF
    public int PegarCpf(){
        return this.cpf;
    }
    //ele retorna a Variavel Nome CrudPessoa
    public CrudPessoa PegarCrudPessoa(){
        return this.crudPessoa;
    }
    //ele retorna a Variavel ContaExiste, então se ele e true ou false
    public boolean ContaExiste(){
        return this.ContaExiste;
    }
    //ele cria a Conta Pessoa, utilizando o metodo createUserPessoa da class CrudPessoa
    public void CrearContaPesso(String nome){
        this.nome = nome;
        this.crudPessoa.createUserPessoa(this.nome);   
    }




}
