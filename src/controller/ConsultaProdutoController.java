package controller;

import model.service.ProdutoService;
import view.consulta.ConsultaProdutoPanel;

public class ConsultaProdutoController extends ControllerBase {

    private final ConsultaProdutoPanel view;
    private final ProdutoService service;

    public ConsultaProdutoController(
            ConsultaProdutoPanel view,
            ProdutoService service
    ) {
        this.view = view;
        this.service = service;

        carregarProdutos();
        eventos();
    }

    private void carregarProdutos() {
        view.model.setDados(service.listarProdutos());
        view.aplicarFiltros();
    }

    @Override
    public void eventos() {

    }

    public void atualizarTabela() {
        view.model.setDados(service.listarProdutos());
        view.aplicarFiltros();
    }
}