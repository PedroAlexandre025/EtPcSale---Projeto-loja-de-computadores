import javax.swing.*;

import controller.*;
import model.entitties.*;
import model.enums.*;
import model.repository.*;
import model.service.*;
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

            Repositorio<Cliente, Integer> repositorioCliente =
                    new Repositorio<>();

            Repositorio<Venda, Integer> repositorioVenda =
                    new Repositorio<>();


            ClienteService clienteService =
                    new ClienteService(
                            repositorioCliente,
                            repositorioVenda
                    );

            ConsultaController consultaController =
                    new ConsultaController(
                            consultaClientes,
                            clienteService,
                            consultaProdutos
                    );

            new ClienteController(
                    clientes,
                    clienteService,
                    consultaController
            );

            RepositorioProduto repositorioProduto =
                    new RepositorioProduto();

            ProdutoService produtoService =
                    new ProdutoService(
                            repositorioProduto,
                            repositorioVenda
                    );

            ConsultaProdutoController consultaProdutoController =
                    new ConsultaProdutoController(
                            consultaProdutos,
                            produtoService
                    );

            ProdutoController produtoController =
                    new ProdutoController(
                            produtos,
                            produtoService,
                            consultaProdutoController
                    );

            RepositorioFornecedor repositorioFornecedor =
                    new RepositorioFornecedor();

            FornecedorService fornecedorService =
                    new FornecedorService(
                            repositorioFornecedor
                    );

            tela.adicionarTela("Cadastro de Fornecedores", fornecedores, null);
            tela.adicionarTela("Cadastro de clientes", clientes, null);
            tela.adicionarTela("Consulta de clientes", consultaClientes, null);
            tela.adicionarTela("Cadastro de produtos", produtos, null);
            tela.adicionarTela("Consulta de produtos", consultaProdutos, null);
            tela.adicionarTela("Movimento de vendas", vendas, null);
            clientes.consultar.addActionListener(e -> tela.mostrarTela("Consulta de clientes"));

            tela.aviso("salvar, remover e movimentar vendas falta fazer os controller por isso nao funfa.");
            tela.setVisible(true);
        });
    }

}
