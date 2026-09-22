package model.entitties;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

import interfaces.Identificavel;
import model.enums.*;

public class Venda implements Identificavel<Integer> {
    
    private final int id;
    private final Cliente cliente;
    private final List<ItemVenda> itens;
    private final LocalDateTime data = LocalDateTime.now();
    private final FormaPagamentos formaPagamentos;
    private final BigDecimal desconto;
    private StatusVenda status = StatusVenda.ABERTA;


    public Venda(int id, Cliente cliente, List<ItemVenda> itens, FormaPagamentos formaPagamentos, BigDecimal desconto){


        this.id = id;
        this.cliente = cliente;
        this.itens = List.copyOf(itens);

        this.formaPagamentos = formaPagamentos;
        this.desconto = desconto;
    }

    @Override
    public Integer getId(){
        return id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public List<ItemVenda> getItens(){
        return itens;
    }

    public LocalDateTime getData(){
        return data;
    }

    public StatusVenda getStatus(){
        return status;
    }

    public FormaPagamentos getFormaPagamentos(){
        return formaPagamentos;
    }

    public BigDecimal getDesconto(){
        return desconto;
    }


    public BigDecimal getTotal(){
         
        // muito processamento para pouco cerebro aqui, basicamente eu verifico o item, preco, e quantidade, e no final se tiver desconto da desconto no caluclo do total;

    return itens.stream().map(ItemVenda::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add).subtract(desconto); 


    }

    // aqui é para cancelar a venda, tem cliente que fica 1h no caixa e sai nao compra nada... ai tem que ter né

    public void finalizar(){
        if (status == StatusVenda.CANCELADA || status == StatusVenda.FINALIZADA){
            throw new IllegalArgumentException("Operação inválida.");
        }
        status = StatusVenda.FINALIZADA;
    }
    public void Cancelar(){
        if(status == StatusVenda.CANCELADA) throw new IllegalArgumentException("Vennda Cancelada");
        status = StatusVenda.CANCELADA;
    }


}