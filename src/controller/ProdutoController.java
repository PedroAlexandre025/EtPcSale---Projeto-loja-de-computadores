package controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.JOptionPane;

import model.entitties.Fornecedor;
import model.entitties.Produto;
import model.enums.CategoriaProduto;
import model.service.ProdutoService;
import view.cadastro.CadastroProdutoPanel;

public class ProdutoController extends ControllerBase {

    private final CadastroProdutoPanel view;
    private final ProdutoService service;
    private final ConsultaProdutoController consultaController;

    private final NumberFormat formato =
            NumberFormat.getNumberInstance(
                    Locale.forLanguageTag("pt-BR")
            );

    public ProdutoController(
            CadastroProdutoPanel view,
            ProdutoService service,
            ConsultaProdutoController consultaController) {

        this.view = view;
        this.service = service;
        this.consultaController = consultaController;

        carregarProdutos();
        eventos();
    }

    private void carregarProdutos() {
        view.model.setDados(
                service.listarProdutos()
        );
    }

    @Override
    public void eventos() {
        view.salvar.addActionListener(
                evento -> salvarProduto()
        );

        view.remover.addActionListener(
                evento -> removerProduto()
        );
    }

    private void salvarProduto() {
        try {
            Produto produtoSelecionado =
                    view.getSelecionado();

            String nome =
                    view.nome.getText().trim();

            CategoriaProduto categoria =
                    (CategoriaProduto)
                            view.categoria.getSelectedItem();

            BigDecimal preco =
                    converterPreco(
                            view.preco.getText()
                    );

            int estoque =
                    ((Number) view.estoque.getValue())
                            .intValue();

            Fornecedor fornecedor =
                    view.getFornecedorSelecionado();

            if (categoria == null) {
                throw new IllegalArgumentException(
                        "Selecione uma categoria."
                );
            }

            if (produtoSelecionado == null) {
                Produto produto =
                        service.registrarProduto(
                                nome,
                                categoria,
                                preco,
                                estoque,
                                fornecedor
                        );

                JOptionPane.showMessageDialog(
                        view,
                        "Produto cadastrado com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                service.atualizarProduto(
                        produtoSelecionado.getId(),
                        nome,
                        categoria,
                        preco,
                        estoque,
                        fornecedor
                );

                JOptionPane.showMessageDialog(
                        view,
                        "Produto atualizado com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            view.model.setDados(
                    service.listarProdutos()
            );

            consultaController.atualizarTabela();
            view.limparCampos();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível salvar",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    view,
                    "Erro ao salvar produto: "
                            + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            ex.printStackTrace();
        }
    }


    private BigDecimal converterPreco(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe o preço do produto."
            );
        }

        try {
            Number valor =
                    formato.parse(texto.trim());

            return BigDecimal.valueOf(
                    valor.doubleValue()
            );

        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "Informe um preço válido. Exemplo: 1.299,90"
            );
        }
    }

    private void removerProduto() {
        try {
            Produto produto =
                    view.getSelecionado();

            if (produto == null) {
                JOptionPane.showMessageDialog(
                        view,
                        "Selecione um produto para remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean confirmou =
                    view.confirmar(
                            "Deseja remover o produto \""
                                    + produto.getNome()
                                    + "\"?"
                    );

            if (!confirmou) {
                return;
            }

            service.removerProduto(
                    produto.getId()
            );

            view.model.setDados(
                    service.listarProdutos()
            );

            consultaController.atualizarTabela();
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Produto removido com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível remover",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    public void atualizarTabela() {
        view.model.setDados(
                service.listarProdutos()
        );
    }
}