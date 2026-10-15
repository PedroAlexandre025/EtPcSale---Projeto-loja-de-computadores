package controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import model.entitties.Cliente;
import model.entitties.Produto;
import model.entitties.Venda;
import model.enums.FormaPagamentos;
import model.service.ClienteService;
import model.service.ProdutoService;
import model.service.VendaService;
import view.movimento.MovimentoVendaPanel;

public class VendaController extends ControllerBase {

    private final MovimentoVendaPanel view;
    private final VendaService service;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    private Venda vendaAtual;

    private final NumberFormat formato = NumberFormat.getNumberInstance(
            Locale.forLanguageTag("pt-BR")
    );

    public VendaController(
            MovimentoVendaPanel view,
            VendaService service,
            ClienteService clienteService,
            ProdutoService produtoService
    ) {
        this.view = view;
        this.service = service;
        this.clienteService = clienteService;
        this.produtoService = produtoService;

        carregarClientes();
        carregarProdutos();
        carregarVendas();
        eventos();
    }

    private void carregarClientes() {
        view.definirClientes(clienteService.listarClientes());
    }

    private void carregarProdutos() {
        view.definirProdutos(produtoService.listarProdutos());
    }

    private void carregarVendas() {
        view.modelVendas.setDados(service.listarVendas());
    }

    @Override
    public void eventos() {
        view.adicionarItem.addActionListener(
                evento -> adicionarItem()
        );

        view.removerItem.addActionListener(
                evento -> removerItem()
        );

        view.finalizar.addActionListener(
                evento -> finalizarVenda()
        );

        view.cancelarVenda.addActionListener(
                evento -> cancelarVenda()
        );
    }

    private void adicionarItem() {
        try {
            Cliente cliente =
                    (Cliente) view.cliente.getSelectedItem();

            Produto produto =
                    (Produto) view.produto.getSelectedItem();

            int quantidade = ((Number) view.quantidade.getValue()).intValue();

            if (cliente == null) {
                throw new IllegalArgumentException(
                        "Selecione um cliente."
                );
            }

            if (produto == null) {
                throw new IllegalArgumentException(
                        "Selecione um produto."
                );
            }

            if (quantidade <= 0) {
                throw new IllegalArgumentException(
                        "A quantidade deve ser maior que zero."
                );
            }

            if (vendaAtual == null) {
                vendaAtual = service.iniciarVenda(cliente);
            }

            /*
             * Impede trocar o cliente no meio da venda.
             */
            if (!vendaAtual.getCliente()
                    .getId()
                    .equals(cliente.getId())) {

                throw new IllegalArgumentException(
                        "Não é possível trocar o cliente "
                                + "durante uma venda aberta."
                );
            }

            service.adicionarItem(
                    vendaAtual,
                    produto,
                    quantidade
            );

            atualizarItens();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível adicionar o item",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void removerItem() {
        try {
            if (vendaAtual == null) {
                throw new IllegalArgumentException(
                        "Nenhuma venda está aberta."
                );
            }

            int linhaVisual = view.tabela.getSelectedRow();

            if (linhaVisual < 0) {
                throw new IllegalArgumentException(
                        "Selecione um item para remover."
                );
            }

            int linhaModelo = view.tabela.convertRowIndexToModel(
                    linhaVisual
            );

            service.removerItem(
                    vendaAtual,
                    linhaModelo
            );

            atualizarItens();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível remover o item",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void atualizarItens() {
        if (vendaAtual == null) {
            view.model.setDados(List.of());
            view.exibirTotal(BigDecimal.ZERO);
            return;
        }

        view.model.setDados(vendaAtual.getItens());

        BigDecimal desconto = converterValor(
                view.desconto.getText()
        );

        BigDecimal total = service.calcularTotal(
                vendaAtual,
                desconto
        );

        view.exibirTotal(total);
    }

    private void finalizarVenda() {
        try {
            if (vendaAtual == null) {
                throw new IllegalArgumentException(
                        "Nenhuma venda foi iniciada."
                );
            }

            if (vendaAtual.getItens().isEmpty()) {
                throw new IllegalArgumentException(
                        "Adicione pelo menos um item à venda."
                );
            }

            FormaPagamentos pagamento =
                    (FormaPagamentos) view.pagamento.getSelectedItem();

            BigDecimal desconto = converterValor(
                    view.desconto.getText()
            );

            service.finalizarVenda(
                    vendaAtual,
                    pagamento,
                    desconto
            );

            carregarVendas();
            carregarProdutos();

            vendaAtual = null;
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Venda finalizada com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível finalizar",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void cancelarVenda() {
        try {
            if (vendaAtual == null) {
                throw new IllegalArgumentException(
                        "Nenhuma venda aberta."
                );
            }

            service.cancelarVenda(vendaAtual);

            vendaAtual = null;
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Venda cancelada com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível cancelar",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private BigDecimal converterValor(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            Number valor = formato.parse(texto.trim());

            BigDecimal resultado = BigDecimal.valueOf(
                    valor.doubleValue()
            );

            if (resultado.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "O desconto não pode ser negativo."
                );
            }

            return resultado;

        } catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "Informe um desconto válido."
            );
        }
    }

    public void atualizarClientes() {
        carregarClientes();
    }

    public void atualizarProdutos() {
        carregarProdutos();
    }

    public void atualizarVendas() {
        carregarVendas();
    }
}