package model.service;

import java.math.BigDecimal;
import java.util.List;

import model.entitties.Cliente;
import model.entitties.ItemVenda;
import model.entitties.Produto;
import model.entitties.Venda;
import model.enums.FormaPagamentos;
import model.repository.Repositorio;

public class VendaService {

    private final Repositorio<Venda, Integer>
            vendasRepositorio;

    private int proximoId = 1;

    public VendaService(
            Repositorio<Venda, Integer>
                    vendasRepositorio) {

        this.vendasRepositorio = vendasRepositorio;
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

    public void adicionarItem(
            Venda venda,
            Produto produto,
            int quantidade) {

        if (venda == null) {
            throw new IllegalArgumentException(
                    "Nenhuma venda foi iniciada."
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

        venda.adicionarItem(
                new ItemVenda(produto, quantidade)
        );
    }

    public void removerItem(
            Venda venda,
            int indice) {

        if (venda == null) {
            throw new IllegalArgumentException(
                    "Nenhuma venda foi iniciada."
            );
        }

        venda.removerItem(indice);
    }

    public BigDecimal calcularTotal(
            Venda venda,
            BigDecimal desconto) {

        if (venda == null) {
            return BigDecimal.ZERO;
        }

        venda.definirDesconto(
                desconto == null
                        ? BigDecimal.ZERO
                        : desconto
        );

        return venda.getTotal();
    }

    public void finalizarVenda(
            Venda venda,
            FormaPagamentos pagamento,
            BigDecimal desconto) {

        if (venda == null) {
            throw new IllegalArgumentException(
                    "Nenhuma venda foi iniciada."
            );
        }

        venda.definirPagamento(pagamento);
        venda.definirDesconto(desconto);
        venda.finalizar();
    }

    public void cancelarVenda(Venda venda) {
        if (venda == null) {
            throw new IllegalArgumentException(
                    "Nenhuma venda foi iniciada."
            );
        }

        venda.cancelar();
    }
}