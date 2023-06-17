import CRUD.CrudPessoa;

public class ContaPoupanca extends Conta{
    private float taxa;

    //ele faz uma extenção do Contrutor Conta, que so para definir a taxa
    public ContaPoupanca(CrudPessoa crudPessoa){
        super(crudPessoa, 1);
        if(ContaExiste){
            this.taxa = this.crudContaPoupanca.PegarTaxaContaPoupanca();
        }
    }

    //Criar sua Nova Conta poupança, e definindo no CrudContaPoupanca
    public void CriarConta(float saldo){
        this.crudContaPoupanca.createContaPoupanca(saldo, 0.005f);
        this.numero_conta = this.crudContaPoupanca.PegarNumContaPoupanca();
        this.taxa = this.crudContaPoupanca.PegarTaxaContaPoupanca();
        this.saldo = saldo;
        this.ContaExiste = true;
    }
    //Metodo para Depositar multiplicando o deposito pela taxa (0.005), e somando pelo deposito e pelo saque, depois Alterando o saldo da conta no CRUD
    public void Deposita(float deposito){
        this.saldo += deposito + (deposito * this.taxa); 
        this.crudContaPoupanca.AlterarSaldoContaPoupanca(this.saldo);
    }

    //Alterar o Valor da taxa
    public void AlterarTaxa(float taxa){
        this.crudContaPoupanca.AlterarTaxaContaPoupancaBD(taxa);
    }

}
