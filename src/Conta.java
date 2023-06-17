import CRUD.CrudContaCorrente;
import CRUD.CrudContaPoupanca;
import CRUD.CrudPessoa;

public class Conta {
    public int numero_conta;
    public int cpf;
    public String NomeTitular;
    public float saldo;
    public CrudPessoa crudPessoa;
    public boolean ContaExiste;
    public CrudContaCorrente crudContaCorrente;
    public CrudContaPoupanca crudContaPoupanca;
    public int TipoConta;// 0 = Corrente, 1 = Poupança

    //Contrutor Conta, verificando se a conta existe, se existir ele define todos os valores pelo CRUD
    Conta(CrudPessoa crudPessoa, int TipoConta){
        this.crudPessoa = crudPessoa;
        this.TipoConta = TipoConta;
        if(this.TipoConta == 0){
            this.crudContaCorrente = new CrudContaCorrente(this.crudPessoa);
            if(crudContaCorrente.ContaCorrenteExiste()){
                this.ContaExiste = true;
                this.numero_conta = this.crudContaCorrente.PegarNumContaCorrente();
                this.cpf = this.crudPessoa.getCPF();
                this.NomeTitular = this.crudPessoa.getNome();
                this.saldo = this.crudContaCorrente.PegarSaldoContaCorrente();
            } else {
                this.ContaExiste = false;
                this.cpf = this.crudPessoa.getCPF();
                this.NomeTitular = this.crudPessoa.getNome();
            }
        } else if (this.TipoConta == 1) {
            this.crudContaPoupanca = new CrudContaPoupanca(this.crudPessoa);
            if(crudContaPoupanca.ContaPoupancaExiste()){
                this.ContaExiste = true;
                this.numero_conta = this.crudContaPoupanca.PegarNumContaPoupanca();
                this.cpf = this.crudPessoa.getCPF();
                this.NomeTitular = this.crudPessoa.getNome();
                this.saldo = this.crudContaPoupanca.PegarSaldoContaPoupanca();
            } else {
                this.ContaExiste = false;
                this.cpf = this.crudPessoa.getCPF();
                this.NomeTitular = this.crudPessoa.getNome();
            }
        } else {
            System.out.println("error no Tipo de Conta");
        }
    }

    //Retorna se a conta existe
    public boolean ContaExiste(){
        return this.ContaExiste;
    }

    //Retorna o numero da conta
    public int ConsultarNumeroConta(){
        return this.numero_conta;
    }
    //Retornar o Cpf
    public int ConsultarCPF(){
        return this.cpf;
    }
    //Retorna o Nome do Titular
    public String ConsultarTitular(){
        return this.NomeTitular;
    }
    //ele rotorna o Saldo da Conta, ele faz uma verificação para saber se vai pegar da conta poupança(1) ou da corrente(0)
    public float ConsultarSaldo(){
        if(this.TipoConta == 0){
            this.saldo = this.crudContaCorrente.PegarSaldoContaCorrenteBD();
        } else if(this.TipoConta == 1){
            this.saldo = this.crudContaPoupanca.PegarSaldoContaPoupancaBD();
        } else {
            System.out.println("error no Tipo de Conta");
        }
        
        return this.saldo;
    }

    //Diminui o valor do Saldo de acordo com o tipo de conta, poupança(1) ou da corrente(0)
    public void DiminuirSaldo(float saque){
        if(this.TipoConta == 0){
            ConsultarSaldo();
            this.saldo -= saque;
            this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);
        } else if(this.TipoConta == 1){
            this.saldo = this.crudContaPoupanca.PegarSaldoContaPoupancaBD();
            this.saldo -= saque;
            this.crudContaPoupanca.AlterarSaldoContaPoupanca(this.saldo);
        } else {
            System.out.println("error no Tipo de Conta");
        }

    }
    //função saque, O saque da Conta Corrente vai ser alterado dentro da classe ContaCorrente
    public boolean sacar(float saque){
        if (ConsultarSaldo() - saque < 0){
            return false;
        } else {
            DiminuirSaldo(saque);
            return true;
        }
    }
    // apagar a conta
    public void deletarConta(){
        if(this.TipoConta == 0){
            this.crudContaCorrente.deletContaCorrente();
        } else if(this.TipoConta == 1){
            this.crudContaPoupanca.deletContaPoupanca();
        } else {
            System.out.println("error no Tipo de Conta");
        }
    }

}
