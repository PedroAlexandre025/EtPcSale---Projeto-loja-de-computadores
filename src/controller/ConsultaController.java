package controller;

import model.service.ClienteService;
import view.consulta.*;

import javax.swing.*;

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
        view.model.setDados(
                service.listarClientes()
        );

        view.filtrar();
    }

    @Override
    public void eventos() {
        view.consultar.addActionListener(
                evento -> consultar()
        );

        view.limpar.addActionListener(
                evento -> view.limparCampos()
        );

        view.busca.addActionListener(
                evento -> consultar()
        );

        viewProduto.consultar.addActionListener(e -> consultarProduto());

        viewProduto.limpar.addActionListener(e -> viewProduto.limparFiltros());

        viewProduto.busca.addActionListener(e-> consultarProduto());

    }
    private void consultar() {
        String texto = view.busca.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    view,
                    "Digite um nome, CPF ou e-mail.",
                    "Consulta",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        view.filtrar();

        if (view.tabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    view,
                    "Nenhum cliente encontrado.",
                    "Consulta",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void consultarProduto() {
        String texto =
                viewProduto.busca.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    viewProduto,
                    "Digite um produto válido.",
                    "Consulta",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        viewProduto.aplicarFiltros();

        if (viewProduto.tabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    viewProduto,
                    "Nenhum produto encontrado.",
                    "Consulta",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }


    public void atualizarTabela() {
        view.busca.setText("");
        view.model.setDados(
                service.listarClientes()
        );
        view.filtrar();
    }
}
