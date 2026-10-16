package model.entitties;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import interfaces.Identificavel;
import model.enums.FormaPagamentos;
import model.enums.StatusVenda;

public class Venda implements Identificavel<Integer> {

    private final int id;
    private final Cliente cliente;
    private final List<ItemVenda> itens;
    private final LocalDateTime data;

    private FormaPagamentos formaPagamentos;
    private BigDecimal desconto;
    private StatusVenda status;

    public Venda(
            int id,
            Cliente cliente,
            List<ItemVenda> itens,
            FormaPagamentos formaPagamentos,
            BigDecimal desconto) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                    "A venda deve possuir um cliente."
            );
        }

        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens);
        this.data = LocalDateTime.now();
        this.formaPagamentos = formaPagamentos;
        this.desconto =
                desconto == null
                        ? BigDecimal.ZERO
                        : desconto;
        this.status = StatusVenda.ABERTA;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public LocalDateTime getData() {
        return data;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public FormaPagamentos getFormaPagamentos() {
        return formaPagamentos;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void adicionarItem(ItemVenda item) {
        adicionarItem(item, desconto);
    }

    public void adicionarItem(ItemVenda item, BigDecimal desconto) {
        validarVendaAberta();

        if (item == null) {
            throw new IllegalArgumentException("O ITEM NAO PODE SER NULO");
        }

        if (desconto == null || desconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("INFORME UM DESCONTO MAIOR OU IGUAL A 0");
        }

        BigDecimal subTotalDepois = getSubtotal().add(item.getSubTotal());

        if(desconto.compareTo(subTotalDepois) > 0) {
            throw new IllegalArgumentException("O DESSCONTO NAO PODE SER MAIOR " + " QUE O SUBTOTAL DA VENDA");
        }

        itens.add(item);
        this.desconto = desconto;
    }

    public void removerItem(int indice) {
      removerItem(indice, desconto);
    }

    public  void removerItem(int indice, BigDecimal desconto) {
        validarVendaAberta();

        if (indice < 0 || indice >= itens.size()) {
            throw new IllegalArgumentException("ITEM INVALIDO");
        }

        if (desconto == null || desconto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("DESCONTO INVALIDO");
        }

        BigDecimal subTotal = getSubtotal().subtract(itens.get(indice).getSubTotal());

        if(desconto.compareTo(subTotal) > 0) {
            throw new IllegalArgumentException("DESCONTO EXCESSIVO INVALIDO" + "REDUZA O DESCONTO CORRETAMENTE");
        }

        itens.remove(indice);
        this.desconto = desconto;
    }

    public void definirPagamento(FormaPagamentos pagamento) {
        validarVendaAberta();

        if (pagamento == null) {
            throw new IllegalArgumentException(
                    "Selecione uma forma de pagamento."
            );
        }

        this.formaPagamentos = pagamento;
    }

    public void definirDesconto(BigDecimal desconto) {
        validarVendaAberta();

        if (desconto == null
                || desconto.compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "O desconto não pode ser negativo."
            );
        }

        if (desconto.compareTo(getSubtotal()) > 0) {
            throw new IllegalArgumentException(
                    "O desconto não pode ser maior que o subtotal."
            );
        }

        this.desconto = desconto;
    }

    public BigDecimal getSubtotal() {
        return itens.stream()
                .map(ItemVenda::getSubTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    public BigDecimal getTotal() {
        return getSubtotal().subtract(desconto);
    }

    public void finalizar() {
        validarVendaAberta();

        if (itens.isEmpty()) {
            throw new IllegalArgumentException(
                    "Adicione pelo menos um item à venda."
            );
        }

        if (formaPagamentos == null) {
            throw new IllegalArgumentException(
                    "Selecione uma forma de pagamento."
            );
        }

        status = StatusVenda.FINALIZADA;
    }

    public void cancelar() {
        if (status == StatusVenda.FINALIZADA) {
            throw new IllegalArgumentException(
                    "Não é possível cancelar uma venda finalizada."
            );
        }

        if (status == StatusVenda.CANCELADA) {
            throw new IllegalArgumentException(
                    "A venda já foi cancelada."
            );
        }

        status = StatusVenda.CANCELADA;
    }

    private void validarVendaAberta() {
        if (status != StatusVenda.ABERTA) {
            throw new IllegalArgumentException(
                    "A venda não está aberta."
            );
        }
    }
}