package model.entitties;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class EntradaEstoque{

    private final int id, quantidade;
    private final Produto produto;
    private final BigDecimal custoUnitario;
    private final LocalDateTime dataEntrada = LocalDateTime.now();

    public EntradaEstoque(int id, Fornecedor fornecedor, Produto produto, int quantidade, BigDecimal custoUnitario) {
        this.id = id;

        this.produto = produto;
        this.quantidade = quantidade;
        this.custoUnitario = custoUnitario;
    }

    public int getId(){
        return id;
    }

    public Fornecedor getFornecedor(){
        return produto.getFornecedor();
    }

    public Produto getProduto(){
        return produto;
    }
    
    public int getQuantidade(){
        return quantidade;
    }

    public BigDecimal getCustoUnitario(){
        return custoUnitario;
    }

    public LocalDateTime getDataEntrada(){
        return dataEntrada;
    }

}