import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tela extends JFrame{
    private int WIDTH = 600;
    private int HEIGHT = 400;
    private String NomeJanela = "Gerenciamento de Contas";
    public Pessoa usuario;
    public ContaCorrente usuarioContaCorrente;
    public ContaPoupanca usuarioContaPoupanca;
    //tela Contrutor
    public Tela(){
        // Configuração da janela
        setTitle(this.NomeJanela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.WIDTH, this.HEIGHT);

        //Definindo o layout como null para o contêiner
        getContentPane().setLayout(null);

        //criando tela inicial
        TelaInicial();
    }
    //tela Tela inicial para entrar com o cpf 
    public void TelaInicial(){
        JLabel TextoPrincipal = new JLabel("Entrar Na Sua Conta");
        JLabel TextoCPF = new JLabel("CPF:");
        final JTextField TextoInputCPF = new JTextField();
        JButton BotaoCPF = new JButton("Entrar com CPF");
        JLabel TextoInformativo1 = new JLabel("caso você ainda não possui um cadastro,");        
        JLabel TextoInformativo2 = new JLabel("você será redirecionado para uma página");
        JLabel TextoInformativo3 = new JLabel("para poder terminar seu cadastro!");        

        //adicionando Função a Eventos do botão
        BotaoCPF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //obtendo o texto inserido no JTextoField
                if(TextoInputCPF.getText().length() > 0){
                    String cpf = TextoInputCPF.getText();
                    usuario = new Pessoa(Integer.parseInt(cpf));

                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela

                    if(usuario.ContaExiste()){
                        TelaMenuGerenciamentoContas();
                    } else {
                        TelaCadastro();
                    }
                }
            }
        });
        //Defininfo as posições e tamanhos dos componentes
        // (PosiçãoX horizontal, PosiçãoY vertical, TamanhoLargura, TamanhoAltura)

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(180, 20, 250, 30);

        getContentPane().add(TextoCPF).setBounds(175, 80, 200, 31);
        getContentPane().add(TextoInputCPF).setBounds(215, 80, 200, 30);

        getContentPane().add(BotaoCPF).setBounds(170, 150, 250, 40);
        getContentPane().add(TextoInformativo1).setBounds(170, 170, 300, 60);
        getContentPane().add(TextoInformativo2).setBounds(170, 185, 400, 60);
        getContentPane().add(TextoInformativo3).setBounds(170, 200, 400, 60);
    }
    //tela de cadastro se o cpf não existir no banco 
    public void TelaCadastro(){
        JLabel TextoPrincipal = new JLabel("Cadastrar Sua Conta");
        JLabel TextoNome = new JLabel("Nome:");
        final JTextField TextoInputNome = new JTextField();
        JButton BotaoFinalizarCadastro = new JButton("Finalizar Cadastro");      
        JButton BotaoVoltarTela = new JButton("Voltar Para Tela Inicial");      

        //adicionando Função a Eventos do botão
        BotaoFinalizarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(TextoInputNome.getText().length() > 0){
                    String Nome = TextoInputNome.getText();
                    usuario.CrearContaPesso(Nome);

                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaCadastroFinalizado();
                }
            }
        });
        BotaoVoltarTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela

                TelaInicial();
            }
        });

        //Defininfo as posições e tamanhos dos componentes
        // (PosiçãoX horizontal, PosiçãoY vertical, TamanhoLargura, TamanhoAltura)

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(180, 20, 250, 30);

        getContentPane().add(TextoNome).setBounds(175, 80, 200, 31);
        getContentPane().add(TextoInputNome).setBounds(215, 80, 200, 30);

        getContentPane().add(BotaoFinalizarCadastro).setBounds(170, 150, 250, 40);
        getContentPane().add(BotaoVoltarTela).setBounds(170, 200, 250, 40);
    }
    //tela de loading apos finalizar o cadastro
    public void TelaCadastroFinalizado(){
        JLabel TextoPrincipal = new JLabel("Cadastrado com Sucesso!"); 
        JLabel TextoRedirecionamento = new JLabel("Você está sendo redirecionado a Menu de Gerenciamento de Contas"); 

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));
        TextoRedirecionamento.setFont(new Font("Default", Font.BOLD , 15));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(180, 20, 250, 30);

        getContentPane().add(TextoRedirecionamento).setBounds(55, 80, 500, 31);

        // Criação do Timer com um atraso de 5 segundos (5000 milissegundos)
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código a ser executado após o atraso de tempo
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
        
                TelaMenuGerenciamentoContas();
            }
        });
        // Iniciar o Timer
        timer.setRepeats(false); // Define para não repetir a ação
        timer.start();
    }
    //menu para escolher o tipo de conta desejada
    public void TelaMenuGerenciamentoContas(){

        usuarioContaCorrente = new ContaCorrente(usuario.PegarCrudPessoa());
        usuarioContaPoupanca = new ContaPoupanca(usuario.PegarCrudPessoa());

        JLabel TextoNome = new JLabel("Olá, " + usuario.PegarNome());
        JLabel TextoPrincipal = new JLabel("Escolha Qual tipo de Conta você deseja");

        JLabel TextoContaCorrente;
        JButton BotaoContaCorrente;
        JLabel TextoContaPoupança;
        JButton BotaoContaPoupança;

        if(usuarioContaCorrente.ContaExiste()){
            TextoContaCorrente = new JLabel("Entrar Conta Corrente:");
            BotaoContaCorrente = new JButton("Conta Corrente");  
        } else {
            TextoContaCorrente = new JLabel("Criar Conta Corrente:");
            BotaoContaCorrente = new JButton("Criar Conta Corrente");  
        }

        if(usuarioContaPoupanca.ContaExiste()){
            TextoContaPoupança = new JLabel("Entrar Conta Poupança:");
            BotaoContaPoupança = new JButton("Conta Poupança"); 
        } else {
            TextoContaPoupança = new JLabel("Criar Conta Poupança:");
            BotaoContaPoupança = new JButton("Criar Conta Poupança");  
        }

  

  

        JButton BotaoTelaInicial = new JButton("Entar com outra Conta CPF");     

        //adicionando Função a Eventos do botão
        BotaoContaCorrente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(usuarioContaCorrente.ContaExiste()){
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaCorrente();
                } else {
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaCadastroContaCorrente();
                }
            }
        });
        BotaoContaPoupança.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(usuarioContaPoupanca.ContaExiste()){
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaPoupanca();
                } else {
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaCadastroContaPoupanca();
                }
            }
        });
        BotaoTelaInicial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaInicial();

            }
        });

        TextoNome.setFont(new Font("Default", Font.BOLD , 17));
        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoNome).setBounds(110, 35, 400, 30);
        getContentPane().add(TextoPrincipal).setBounds(110, 55, 400, 30);

        getContentPane().add(TextoContaCorrente).setBounds(115, 150, 200, 31);
        getContentPane().add(BotaoContaCorrente).setBounds(255, 150, 200, 30);

        getContentPane().add(TextoContaPoupança).setBounds(115, 200, 200, 31);
        getContentPane().add(BotaoContaPoupança).setBounds(255, 200, 200, 30);

        getContentPane().add(BotaoTelaInicial).setBounds(115, 270, 340, 30);
    }
    //menu da conta corrente depositos, saques
    public void TelaContaCorrente(){
        JLabel TextoPrincipal = new JLabel("Conta Corrente");

        JLabel TextoSaque = new JLabel("Sacar:");
        final JTextField TextoInputSaque = new JTextField();
        JButton BotaoSaque = new JButton("Saque");    

        JLabel TextoDepositar = new JLabel("Depositar:");
        final JTextField TextoInputDepositar = new JTextField();
        JButton BotaoDepositar = new JButton("Depositar");    

        JLabel TextoConsultarSaldo = new JLabel("Consultar Saldo:");
        JLabel TextoSaldo = new JLabel("R$ " + usuarioContaCorrente.ConsultarSaldo());

        JLabel TextoConsultarCreditoEspecial = new JLabel("Consultar Credito Especial:");
        JLabel TextoSaldoCreditoEspecial = new JLabel("R$ " + usuarioContaCorrente.ConsultarChequeEspecial());

        JLabel TextoVoltarMenu = new JLabel("Voltar Para o Menu De Gerenciamento de Contas:");
        JButton BotaoVoltarMenu = new JButton("Voltar");     

        JLabel TextoDeletarConta = new JLabel("Deletar Sua Conta:");
        JButton BotaoDeletarConta = new JButton("Deletar");    

        //adicionando Função a Eventos do botão
        BotaoSaque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                if(TextoInputSaque.getText().length() > 0){
                    float saque = Float.parseFloat(TextoInputSaque.getText());
                    if(usuarioContaCorrente.sacar(saque)){
                        getContentPane().removeAll();
                        revalidate(); // Revalida a janela
                        repaint(); // Redesenha a janela
                        TelaContaCorrente();
                    } else {
                        JLabel TextoInfo = new JLabel("error:Saldo Insuficiente");
                        getContentPane().add(TextoInfo).setBounds(115, 255, 400, 30);
                    }
                }
            }
        });
        BotaoDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(TextoInputDepositar.getText().length() > 0){
                    float deposito = Float.parseFloat(TextoInputDepositar.getText());
                    usuarioContaCorrente.Deposita(deposito);
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaCorrente();
                }
            }
        });
        BotaoVoltarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });
        BotaoDeletarConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                usuarioContaCorrente.deletarConta();
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(110, 35, 400, 30);

        getContentPane().add(TextoSaque).setBounds(115, 100, 200, 31);
        getContentPane().add(TextoInputSaque).setBounds(200, 100, 100, 30);
        getContentPane().add(BotaoSaque).setBounds(320, 100, 100, 30);

        getContentPane().add(TextoDepositar).setBounds(115, 150, 200, 31);
        getContentPane().add(TextoInputDepositar).setBounds(200, 150, 100, 30);
        getContentPane().add(BotaoDepositar).setBounds(320, 150, 100, 30);

        getContentPane().add(TextoConsultarSaldo).setBounds(115, 180, 200, 31);
        getContentPane().add(TextoSaldo).setBounds(320, 180, 200, 30);

        getContentPane().add(TextoConsultarCreditoEspecial).setBounds(115, 200, 200, 31);
        getContentPane().add(TextoSaldoCreditoEspecial).setBounds(320, 200, 200, 30);
        
        getContentPane().add(TextoVoltarMenu).setBounds(115, 225, 400, 30);
        getContentPane().add(BotaoVoltarMenu).setBounds(115, 250, 100, 30);
        
        getContentPane().add(TextoDeletarConta).setBounds(115, 285, 400, 30);
        getContentPane().add(BotaoDeletarConta).setBounds(115, 310, 100, 30);
    }
    //menu da conta poupança depositos, saques
    public void TelaContaPoupanca(){
        JLabel TextoPrincipal = new JLabel("Conta Poupança");

        JLabel TextoSaque = new JLabel("Sacar:");
        final JTextField TextoInputSaque = new JTextField();
        JButton BotaoSaque = new JButton("Saque");    

        JLabel TextoDepositar = new JLabel("Depositar:");
        final JTextField TextoInputDepositar = new JTextField();
        JButton BotaoDepositar = new JButton("Depositar");    

        JLabel TextoConsultarSaldo = new JLabel("Consultar Saldo:");
        JLabel TextoSaldo = new JLabel("R$ " + usuarioContaPoupanca.ConsultarSaldo());

        JLabel TextoVoltarMenu = new JLabel("Voltar Para o Menu De Gerenciamento de Contas:");
        JButton BotaoVoltarMenu = new JButton("Voltar");  

        JLabel TextoDeletarConta = new JLabel("Deletar Sua Conta:");
        JButton BotaoDeletarConta = new JButton("Deletar");     

        //adicionando Função a Eventos do botão
        BotaoSaque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                if(TextoInputSaque.getText().length() > 0){
                    float saque = Float.parseFloat(TextoInputSaque.getText());
                    if(usuarioContaPoupanca.sacar(saque)){
                        getContentPane().removeAll();
                        revalidate(); // Revalida a janela
                        repaint(); // Redesenha a janela
                        TelaContaPoupanca();
                    } else {
                        JLabel TextoInfo = new JLabel("error:Saldo Insuficiente");
                        getContentPane().add(TextoInfo).setBounds(115, 255, 400, 30);
                    }
                }
            }
        });
        BotaoDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(TextoInputDepositar.getText().length() > 0){
                    float deposito = Float.parseFloat(TextoInputDepositar.getText());
                    usuarioContaPoupanca.Deposita(deposito);
                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaPoupanca();
                }
            }
        });
        BotaoVoltarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });
        BotaoDeletarConta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                usuarioContaPoupanca.deletarConta();
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(110, 35, 400, 30);

        getContentPane().add(TextoSaque).setBounds(115, 100, 200, 31);
        getContentPane().add(TextoInputSaque).setBounds(200, 100, 100, 30);
        getContentPane().add(BotaoSaque).setBounds(320, 100, 100, 30);

        getContentPane().add(TextoDepositar).setBounds(115, 150, 200, 31);
        getContentPane().add(TextoInputDepositar).setBounds(200, 150, 100, 30);
        getContentPane().add(BotaoDepositar).setBounds(320, 150, 100, 30);

        getContentPane().add(TextoConsultarSaldo).setBounds(115, 200, 200, 31);
        getContentPane().add(TextoSaldo).setBounds(320, 200, 200, 30);
        
        getContentPane().add(TextoVoltarMenu).setBounds(115, 225, 400, 30);
        getContentPane().add(BotaoVoltarMenu).setBounds(115, 250, 100, 30);

        getContentPane().add(TextoDeletarConta).setBounds(115, 285, 400, 30);
        getContentPane().add(BotaoDeletarConta).setBounds(115, 310, 100, 30);
    }
    //tela de cadastro da conta corrente, nessa tela o usuario so tera que inserir o saldo da conta
    public void TelaCadastroContaCorrente(){     
        JLabel TextoPrincipal = new JLabel("Cadastrar Sua Conta Corrente");
        JLabel TextoNome = new JLabel("Saldo da conta:");
        final JTextField TextoInputNome = new JTextField();
        JButton BotaoFinalizarCadastro = new JButton("Finalizar Cadastro");      
        JButton BotaoVoltarTela = new JButton("Voltar");      

        //adicionando Função a Eventos do botão
        BotaoFinalizarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(TextoInputNome.getText().length() > 0){
                    String Saldo = TextoInputNome.getText();
                    usuarioContaCorrente.CriarConta(Float.parseFloat(Saldo));

                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaCorrente();
                }
            }
        });
        BotaoVoltarTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });

        //Defininfo as posições e tamanhos dos componentes
        // (PosiçãoX horizontal, PosiçãoY vertical, TamanhoLargura, TamanhoAltura)

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(150, 20, 300, 30);

        getContentPane().add(TextoNome).setBounds(145, 80, 200, 31);
        getContentPane().add(TextoInputNome).setBounds(245, 80, 200, 30);

        getContentPane().add(BotaoFinalizarCadastro).setBounds(170, 150, 250, 40);
        getContentPane().add(BotaoVoltarTela).setBounds(170, 200, 250, 40);
    }
    //tela de cadastro da conta poupança, nessa tela o usuario so tera que inserir o saldo da conta
    public void TelaCadastroContaPoupanca(){   
        JLabel TextoPrincipal = new JLabel("Cadastrar Sua Conta Poupança");
        JLabel TextoNome = new JLabel("Saldo da conta:");
        final JTextField TextoInputNome = new JTextField();
        JButton BotaoFinalizarCadastro = new JButton("Finalizar Cadastro");      
        JButton BotaoVoltarTela = new JButton("Voltar");      

        //adicionando Função a Eventos do botão
        BotaoFinalizarCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(TextoInputNome.getText().length() > 0){
                    String Saldo = TextoInputNome.getText();
                    usuarioContaPoupanca.CriarConta(Float.parseFloat(Saldo));

                    getContentPane().removeAll();
                    revalidate(); // Revalida a janela
                    repaint(); // Redesenha a janela
                    TelaContaPoupanca();
                }
            }
        });
        BotaoVoltarTela.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                getContentPane().removeAll();
                revalidate(); // Revalida a janela
                repaint(); // Redesenha a janela
                TelaMenuGerenciamentoContas();
            }
        });

        //Defininfo as posições e tamanhos dos componentes
        // (PosiçãoX horizontal, PosiçãoY vertical, TamanhoLargura, TamanhoAltura)

        TextoPrincipal.setFont(new Font("Default", Font.BOLD , 20));

        //adicionando o botão ao conteúdo da janela
        getContentPane().add(TextoPrincipal).setBounds(150, 20, 300, 30);

        getContentPane().add(TextoNome).setBounds(145, 80, 200, 31);
        getContentPane().add(TextoInputNome).setBounds(245, 80, 200, 30);

        getContentPane().add(BotaoFinalizarCadastro).setBounds(170, 150, 250, 40);
        getContentPane().add(BotaoVoltarTela).setBounds(170, 200, 250, 40);
    }

}