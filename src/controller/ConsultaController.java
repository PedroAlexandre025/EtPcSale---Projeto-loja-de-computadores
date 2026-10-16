package controller;

import javax.swing.JOptionPane;

import model.service.ClienteService;
import view.consulta.ConsultaClientePanel;
import view.consulta.ConsultaProdutoPanel;

public class ConsultaController extends ControllerBase {

    private final ConsultaClientePanel view;
    private final ClienteService service;
    private final ConsultaProdutoPanel viewProduto;

    public ConsultaController(
            ConsultaClientePanel view,
            ClienteService service,
            ConsultaProdutoPanel viewProduto) {

        this.view = view;
        this.service = service;
        this.viewProduto = viewProduto;

        carregarClientes();
        eventos();
    }

    private void carregarClientes() {
        view.model.setDados(service.consultarCliente());
        view.filtrar();
    }

    @Override
    public void eventos() {

        // Consulta de clientes.
        view.consultar.addActionListener(
                evento -> consultar()
        );

        view.limpar.addActionListener(
                evento -> view.limparCampos()
        );

        // Permite pesquisar pressionando Enter.
        view.busca.addActionListener(
                evento -> consultar()
        );

        // Consulta de produtos.
        viewProduto.consultar.addActionListener(
                evento -> consultarProduto()
        );

        viewProduto.limpar.addActionListener(
                evento -> viewProduto.limparFiltros()
        );

        viewProduto.busca.addActionListener(
                evento -> consultarProduto()
        );
    }

    private void consultar() {
        // Atualiza os dados e aplica a pesquisa.
        // Com o campo vazio, mostra todos os clientes.
        carregarClientes();

        if (view.tabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "Nenhum cliente encontrado.",
                    "Consulta de clientes",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void consultarProduto() {
        // Permite pesquisar por texto, categoria ou ambos.
        viewProduto.aplicarFiltros();

        if (viewProduto.tabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    viewProduto,
                    "Nenhum produto encontrado.",
                    "Consulta de produtos",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public void atualizarTabela() {
        // Atualiza os clientes preservando a pesquisa digitada.
        carregarClientes();
    }
}