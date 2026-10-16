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
import java.util.Optional;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
        buscarClientePorCpf();
    }

    private void carregarProdutos() {
        view.definirProdutos(produtoService.listarProdutos());
    }

    private void carregarVendas() {
        view.modelVendas.setDados(service.listarVendas());
    }

    @Override
    public void eventos() {

        view.cpfCliente.getDocument().addDocumentListener(
                new DocumentListener() {


                    @Override
                    public void insertUpdate(DocumentEvent evento) {
                        buscarClientePorCpf();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent evento) {
                        buscarClientePorCpf();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent evento) {
                        buscarClientePorCpf();
                    }
                }
        );

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

        view.limpar.addActionListener(evento-> limparVenda());
    }

    private void adicionarItem() {
        try {
            Cliente cliente = clienteService.BuscarCpf(view.cpfCliente.getText()).orElseThrow(()-> new IllegalArgumentException("Informe o CPF de um Cliente Cadastrado"));

            Produto produto =
                    (Produto) view.produto.getSelectedItem();

            int quantidade = ((Number) view.quantidade.getValue()).intValue();


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

            BigDecimal desconto = converterValor(view.desconto.getText());

            if (vendaAtual == null) {
                vendaAtual = service.iniciarVenda(cliente);

                view.cpfCliente.setEditable(false);
            }

            // impedindo a troca de cliente no meio da venda
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
                    quantidade,
                    desconto
                    );
            carregarVendas();
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

            BigDecimal desconto = converterValor(view.desconto.getText());

            service.removerItem(
                    vendaAtual,
                    linhaModelo,
                    desconto
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
            carregarVendas();

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

    private void limparVenda(){
        try{

            if(vendaAtual != null){
                service.cancelarVenda(vendaAtual);

            }

            vendaAtual = null;
            view.limparCampos();
            carregarVendas();

        } catch(IllegalArgumentException ex){
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Nao foi possivel Limpar",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private BigDecimal converterValor(String texto) {
        return interfaces.validacao.valorMonetario(texto, true);
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


    public void buscarClientePorCpf(){
        String texto = view.cpfCliente.getText();

        String numeros = texto.trim().replace(".", "").replace("-", "");

        Optional<Cliente> encontrado = clienteService.BuscarCpf(texto);

        view.nomeCliente.setText(encontrado.map(Cliente::getNome).orElse(""));

        if(texto.isEmpty()){
            view.mensagem("DIGITE O CPF DO CLIENTE");
        } else if (!numeros.matches("[0-9]{11}")) {

            view.mensagem("Informe os 11 numeros do CPF");
        } else if (encontrado.isEmpty()) {
            view.mensagem("CPF não encontrado. Cadastre o cliente primeiro");

        }else {
            view.mensagem("CLIENTE LOCALIZADO. Adicione os Produtos à VENDA");

    }

    }
}