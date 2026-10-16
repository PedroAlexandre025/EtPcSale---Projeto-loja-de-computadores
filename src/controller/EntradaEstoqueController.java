package controller;

import java.math.BigDecimal;
import javax.swing.JOptionPane;
import interfaces.validacao;
import model.entitties.EntradaEstoque;
import model.entitties.Produto;
import model.service.EntradaEstoqueService;
import model.service.ProdutoService;
import view.movimento.EntradaEstoquePanel;

public class EntradaEstoqueController extends ControllerBase {

    private final EntradaEstoquePanel view;
    private final EntradaEstoqueService service;
    private final ProdutoService produtoService;
    private final Runnable aposRegistrar;

    public EntradaEstoqueController(
            EntradaEstoquePanel view,
            EntradaEstoqueService service,
            ProdutoService produtoService,
            Runnable aposRegistrar) {

        this.view = view;
        this.service = service;
        this.produtoService = produtoService;
        this.aposRegistrar = aposRegistrar;
        eventos();
        atualizarTela();
    }

    @Override
    public void eventos() {
        view.produto.addActionListener(evento -> exibirProduto());
        view.registrar.addActionListener(evento -> registrarEntrada());
        view.custoUnitario.addActionListener(evento -> registrarEntrada());
        view.atualizar.addActionListener(evento -> atualizarTela());
        view.limpar.addActionListener(evento -> {
            view.limparCampos();
            atualizarTela();
        });
    }

    private void exibirProduto() {
        view.exibirProduto((Produto) view.produto.getSelectedItem());
    }

    public void atualizarTela() {
        view.definirProdutos(produtoService.listarProdutos());
        exibirProduto();
        view.model.setDados(service.listarEntradas());
        view.exibirResumo(
                view.model.getRowCount(), service.calcularCustoTotal()
        );
    }

    private void registrarEntrada() {
        try {
            Produto produto = (Produto) view.produto.getSelectedItem();
            view.quantidade.commitEdit();
            int quantidade = ((Number) view.quantidade.getValue()).intValue();
            BigDecimal custo = validacao.valorMonetario(
                    view.custoUnitario.getText()
            );

            EntradaEstoque entrada = service.registrarEntrada(
                    produto, quantidade, custo
            );

            aposRegistrar.run();
            view.limparCampos();
            atualizarTela();
            view.mensagem("Entrada #" + entrada.getId()
                    + " registrada. Saldo de " + entrada.getNomeProduto()
                    + ": " + entrada.getProduto().getEstoque() + " unidade(s).");

        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(view,
                    "Informe uma quantidade inteira válida.",
                    "Entrada não registrada", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(),
                    "Entrada não registrada", JOptionPane.WARNING_MESSAGE);
        }
    }
}