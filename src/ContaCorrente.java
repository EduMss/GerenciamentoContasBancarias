import CRUD.CrudPessoa;

public class ContaCorrente extends Conta{
    private float cheque_especial;
    

    //Contrutor o ContaCorrente, extendendo o construtor conta e adicionando o cheque especial
    public ContaCorrente(CrudPessoa crudPessoa){
        super(crudPessoa, 0);
        if(ContaExiste){
            this.cheque_especial = this.crudContaCorrente.PegarChequeEspecialContaCorrente();
        }
    }
    //Ele criar a conta Corrente
    public void CriarConta(float saldo){
        this.crudContaCorrente.createContaCorrente(saldo);
        this.numero_conta = this.crudContaCorrente.PegarNumContaCorrente();
        this.cheque_especial = this.crudContaCorrente.PegarChequeEspecialContaCorrente();
        this.saldo = saldo;
        this.ContaExiste = true;
    }
    //fazer o depositar 
    public void Deposita(float deposito){
        if(this.saldo < 0){
            if(this.saldo + deposito > 0){
                AleterarChequeEspecial(500.0f);
                this.saldo += deposito; 
                this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);
            } else {
                AleterarChequeEspecial(this.cheque_especial + deposito);
                this.saldo += deposito; 
                this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);
            }
        } else {
            this.saldo += deposito; 
            this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);
        }
    }
    //alterar o metodo sacar, para fazer as verificações do saque com o cheque especial
    @Override
    public boolean sacar(float saque){
        if(ConsultarSaldo() - saque < 0){// saldo == 100 - 600 < 0 == True -500 
            if(ConsultarSaldo() < 0){//-100 
                if(ConsultarChequeEspecial() < saque){// 500 < 600 == True
                    return false;
                } else {
                    this.saldo -= saque;
                    this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);

                    this.cheque_especial += this.saldo;
                    this.crudContaCorrente.AlterarChequeEspecialContaCorrenteBD(this.cheque_especial);
                    return true;
                }
            } else if (ConsultarSaldo() + ConsultarChequeEspecial() >= saque){
                    this.saldo -= saque;
                    this.crudContaCorrente.AlterarSaldoContaCorrente(this.saldo);

                    this.cheque_especial += this.saldo;
                    this.crudContaCorrente.AlterarChequeEspecialContaCorrenteBD(this.cheque_especial);
                    return true;
            } else {
                return false;
            }
        } else {
            DiminuirSaldo(saque);
            return true;
        }
    }

    //Retorna o valor do Cheque Especial
    public float ConsultarChequeEspecial(){
        this.cheque_especial = this.crudContaCorrente.PegarChequeEspecialContaCorrenteBD();
        return this.cheque_especial;
    }
    //Altera o Valor do cheque Especial
    public void AleterarChequeEspecial(float ChequeEspecial){
        this.cheque_especial = ChequeEspecial;
        this.crudContaCorrente.AlterarChequeEspecialContaCorrenteBD(this.cheque_especial);
    }

}
