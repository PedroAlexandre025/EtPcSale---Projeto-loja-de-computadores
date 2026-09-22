import javax.swing.*;

import controller.ClienteController;
import view.TelaPrincipal;
import view.cadastro.CadastroClientePanel;
import view.cadastro.CadastroProdutoPanel;
import view.consulta.ConsultaClientePanel;
import view.consulta.ConsultaProdutoPanel;
import view.movimento.MovimentoVendaPanel;
import view.cadastro.CadastroFornecedorPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
            }

            TelaPrincipal tela = new TelaPrincipal();
            CadastroClientePanel clientes = new CadastroClientePanel();
            CadastroProdutoPanel produtos = new CadastroProdutoPanel();
            ConsultaClientePanel consultaClientes = new ConsultaClientePanel();
            ConsultaProdutoPanel consultaProdutos = new ConsultaProdutoPanel();
            CadastroFornecedorPanel fornecedores = new CadastroFornecedorPanel();
            MovimentoVendaPanel vendas = new MovimentoVendaPanel();
            
            tela.adicionarTela("Cadastro de Fornecedores", fornecedores, null);
            tela.adicionarTela("Cadastro de clientes", clientes, null);
            tela.adicionarTela("Consulta de clientes", consultaClientes, null);
            tela.adicionarTela("Cadastro de produtos", produtos, null);
            tela.adicionarTela("Consulta de produtos", consultaProdutos, null);
            tela.adicionarTela("Movimento de vendas", vendas, null);
            clientes.consultar.addActionListener(e -> tela.mostrarTela("Consulta de clientes"));

            // falta fazer os controllers conectados.
            aguardarController(clientes.salvar, clientes.remover, produtos.salvar,
                    produtos.remover, vendas.adicionarItem, vendas.removerItem,
                    vendas.finalizar, vendas.cancelarVenda);
            new ClienteController(clientes);
            //fazer os crontrooler
            tela.aviso("salvar, remover e movimentar vendas falta fazer os controller por isso nao funfa.");
            tela.setVisible(true);
        });
    }

    private static void aguardarController(JButton... botoes) {
        for (JButton botao : botoes) {
            botao.setEnabled(true);
            //aqui só vcai fujncionar qujando fazer os controoler
            botao.setToolTipText("FAZER O CONTROLLER DAQUI TBM.");
        }
    }
}
