import javax.swing.SwingUtilities;

import controller.*;
import view.*;


public class Main {

    // falta fazer a tela principal e as telas de cadastro e consulta, mas já dá pra testar o fluxo de execução do programa
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.adicionarTela("Cadastro de Clientes", new CadastroCliente(), null);
            telaPrincipal.adicionarTela("Consulta de Clientes", new ConsultaCliente(), null);
            telaPrincipal.adicionarTela("Cadastro de Produtos", new CadastroProduto(), null);
            telaPrincipal.adicionarTela("Consulta de Produtos", new ConsultaProduto(), null);
            telaPrincipal.adicionarTela("Movimento de Vendas", new MovimentoVenda(), null);
            telaPrincipal.setVisible(true);
        });
    }
}