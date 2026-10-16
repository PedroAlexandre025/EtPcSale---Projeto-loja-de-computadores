
package model.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.entitties.Cliente;
import model.entitties.ItemVenda;
import model.entitties.Produto;
import model.entitties.Venda;
import model.enums.FormaPagamentos;
import model.enums.StatusVenda;
import model.repository.Repositorio;
import model.repository.RepositorioProduto;

public class VendaService {

    private final Repositorio<Venda, Integer> vendasRepositorio;
    private final RepositorioProduto produtosRepositorio;

    private int proximoId = 1;

    public VendaService(
            Repositorio<Venda, Integer> vendasRepositorio,
            RepositorioProduto produtosRepositorio) {

        this.vendasRepositorio = vendasRepositorio;
        this.produtosRepositorio = produtosRepositorio;
    }

    public List<Venda> listarVendas() {
        return vendasRepositorio.listarTodos();
    }

    public Venda iniciarVenda(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException(
                    "Selecione um cliente."
            );
        }

        Venda venda = new Venda(
                proximoId,
                cliente,
                List.of(),
                null,
                BigDecimal.ZERO
        );

        vendasRepositorio.salvar(venda);
        proximoId++;

        return venda;
    }


    private void validarVendaAberta(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException(
                    "Nenhuma venda foi iniciada."
            );
        }

        Venda registrada = vendasRepositorio
                .buscarPorId(venda.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Venda não encontrada."
                ));

        if (registrada != venda) {
            throw new IllegalArgumentException(
                    "A referência da venda está desatualizada."
            );
        }

        if (venda.getStatus() != StatusVenda.ABERTA) {
            throw new IllegalArgumentException(
                    "A venda não está aberta."
            );
        }
    }

    private Produto buscarProdutoAtual(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException(
                    "Selecione um produto."
            );
        }

        return produtosRepositorio
                .buscarPorId(produto.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "O produto não está mais cadastrado."
                ));
    }

    public void adicionarItem(
            Venda venda,
            Produto produto,
            int quantidade
    ){
        validarVendaAberta(venda);

        adicionarItem(
                venda,
                produto,
                quantidade,
                venda.getDesconto()
        );

    }

    public void adicionarItem(Venda venda,
                              Produto produto,
                              int quantidade,
                              BigDecimal desconto
    ){
        validarVendaAberta(venda);

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A QUANTIDADE DEVE SER MAIOR QUE 0");
        }

        Produto atual = buscarProdutoAtual(produto);

        long quantidadeNaVenda = venda.getItens().stream().filter(item->item.getProduto().getId().equals(atual.getId())).mapToLong(ItemVenda::getQuantidade).sum();

        if (quantidadeNaVenda + quantidade > atual.getEstoque()) {
            throw new IllegalArgumentException(
                    "Estoque insuficiente para " + atual.getNome()
                            + ". Disponível: " + atual.getEstoque()
            );
        }

        venda.adicionarItem(
                new ItemVenda(atual, quantidade),
                desconto
        );
    }


    public void removerItem(Venda venda, int indice) {
        validarVendaAberta(venda);
        venda.removerItem(indice);
    }

    public BigDecimal calcularTotal(
            Venda venda,
            BigDecimal desconto) {

        if (venda == null) {
            return BigDecimal.ZERO;
        }

        validarVendaAberta(venda);

        venda.definirDesconto(
                desconto == null ? BigDecimal.ZERO : desconto
        );

        return venda.getTotal();
    }

    public void finalizarVenda(
            Venda venda,
            FormaPagamentos pagamento,
            BigDecimal desconto) {

        validarVendaAberta(venda);

        if (venda.getItens().isEmpty()) {
            throw new IllegalArgumentException(
                    "Adicione pelo menos um item."
            );
        }

        // Valida os dados antes de movimentar o estoque.
        venda.definirPagamento(pagamento);
        venda.definirDesconto(
                desconto == null ? BigDecimal.ZERO : desconto
        );

        Map<Integer, Long> quantidades = new LinkedHashMap<>();
        Map<Integer, Produto> produtos = new LinkedHashMap<>();

        for (ItemVenda item : venda.getItens()) {
            Produto atual = buscarProdutoAtual(item.getProduto());

            produtos.put(atual.getId(), atual);

            quantidades.merge(
                    atual.getId(),
                    (long) item.getQuantidade(),
                    Long::sum
            );
        }

        // Confere todos os produtos antes de fazer qualquer baixa.
        for (Map.Entry<Integer, Long> entrada : quantidades.entrySet()) {
            Produto produto = produtos.get(entrada.getKey());

            if (entrada.getValue() > produto.getEstoque()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para " + produto.getNome()
                                + ". Disponível: " + produto.getEstoque()
                );
            }
        }

        for (Map.Entry<Integer, Long> entrada : quantidades.entrySet()) {
            produtos.get(entrada.getKey())
                    .baixarEstoque(entrada.getValue().intValue());
        }

        venda.finalizar();
    }

    public void cancelarVenda(Venda venda) {
        validarVendaAberta(venda);
        venda.cancelar();
    }

    public void removerItem(Venda venda, int indice, BigDecimal desconto) {
        validarVendaAberta(venda);

        venda.removerItem(indice, desconto);
    }
}