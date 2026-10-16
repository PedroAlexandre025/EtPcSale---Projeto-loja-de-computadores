import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.ClienteController;
import controller.ConsultaController;
import controller.ConsultaProdutoController;
import controller.FornecedorController;
import controller.ProdutoController;
import controller.VendaController;
import controller.EntradaEstoqueController;
import model.entitties.Cliente;
import model.entitties.EntradaEstoque;
import model.entitties.Venda;
import model.repository.Repositorio;
import model.repository.RepositorioFornecedor;
import model.repository.RepositorioProduto;
import model.service.ClienteService;
import model.service.FornecedorService;
import model.service.ProdutoService;
import model.service.VendaService;
import model.service.EntradaEstoqueService;
import view.TelaPrincipal;
import view.cadastro.CadastroClientePanel;
import view.cadastro.CadastroFornecedorPanel;
import view.cadastro.CadastroProdutoPanel;
import view.consulta.ConsultaClientePanel;
import view.consulta.ConsultaProdutoPanel;
import view.movimento.MovimentoVendaPanel;
import view.movimento.EntradaEstoquePanel;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {

            }

            TelaPrincipal tela = new TelaPrincipal();

            CadastroClientePanel clientes = new CadastroClientePanel();
            CadastroProdutoPanel produtos = new CadastroProdutoPanel();
            ConsultaClientePanel consultaClientes =
                    new ConsultaClientePanel();
            ConsultaProdutoPanel consultaProdutos =
                    new ConsultaProdutoPanel();
            CadastroFornecedorPanel fornecedores =
                    new CadastroFornecedorPanel();
            MovimentoVendaPanel vendas = new MovimentoVendaPanel();
            EntradaEstoquePanel entradas = new EntradaEstoquePanel();

            Repositorio<Cliente, Integer> repositorioCliente =
                    new Repositorio<>();

            Repositorio<Venda, Integer> repositorioVenda =
                    new Repositorio<>();

            Repositorio<EntradaEstoque, Integer> repositorioEntrada =
                    new Repositorio<>();

            ClienteService clienteService = new ClienteService(
                    repositorioCliente,
                    repositorioVenda
            );

            RepositorioProduto repositorioProduto =
                    new RepositorioProduto();

            ProdutoService produtoService = new ProdutoService(
                    repositorioProduto,
                    repositorioVenda
            );

            ConsultaProdutoController consultaProdutoController =
                    new ConsultaProdutoController(
                            consultaProdutos,
                            produtoService
                    );

            RepositorioFornecedor repositorioFornecedor =
                    new RepositorioFornecedor();

            FornecedorService fornecedorService =
                    new FornecedorService(
                            repositorioFornecedor,
                            repositorioProduto

                    );

            VendaService vendaService = new  VendaService(
                    repositorioVenda,
                    repositorioProduto

            );

            VendaController vendaController = new VendaController(
                    vendas,
                    vendaService,
                    clienteService,
                    produtoService
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
                    consultaController,
                    vendaController
            );

            ProdutoController produtoController =
                    new ProdutoController(
                            produtos,
                            produtoService,
                            consultaProdutoController,
                            fornecedorService,
                            vendaController
                    );

            FornecedorController fornecedorController =
                    new FornecedorController(
                            fornecedores,
                            fornecedorService,
                            produtoController
                    );

            EntradaEstoqueService entradaService = new EntradaEstoqueService(
                    repositorioEntrada,
                    repositorioProduto,
                    repositorioFornecedor
            );

            EntradaEstoqueController entradaController =
                    new EntradaEstoqueController(
                            entradas, entradaService, produtoService,
                            () -> {
                                produtoController.atualizarTabela();
                                consultaProdutoController.atualizarTabela();
                                vendaController.atualizarProdutos();
                            }
                    );

            tela.adicionarTela(
                    "Cadastro de Fornecedores",
                    fornecedores,
                    null
            );

            tela.adicionarTela(
                    "Cadastro de clientes",
                    clientes,
                    null
            );

            tela.adicionarTela(
                    "Consulta de clientes",
                    consultaClientes,
                    consultaController::atualizarTabela
            );

            tela.adicionarTela(
                    "Cadastro de produtos",
                    produtos,
                    null
            );

            tela.adicionarTela(
                    "Consulta de produtos",
                    consultaProdutos,
                    null
            );

            tela.adicionarTela(
                    "Movimento de vendas",
                    vendas,
                    null
            );

            tela.adicionarTela(
                    "Entrada de estoque",
                    entradas,
                    entradaController::atualizarTela
            );

            clientes.consultar.addActionListener(
                    evento -> tela.mostrarTela("Consulta de clientes")
            );

            tela.aviso("EtPcSale -- Loja de computadores.");
            tela.setVisible(true);
        });
    }
}