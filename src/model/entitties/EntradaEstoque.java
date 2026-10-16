package model.entitties;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import interfaces.Identificavel;

public class EntradaEstoque implements Identificavel<Integer> {

    private final int id, quantidade;
    private final Produto produto;
    private final Fornecedor fornecedor;
    private final String nomeProduto, razaoSocialFornecedor;
    private final BigDecimal custoUnitario;
    private final LocalDateTime dataEntrada = LocalDateTime.now();

    public EntradaEstoque(int id, Fornecedor fornecedor, Produto produto, int quantidade, BigDecimal custoUnitario) {
        if (id <= 0 || fornecedor == null || produto == null) {
            throw new IllegalArgumentException("Informe produto e fornecedor válidos.");
        }
        if (produto.getFornecedor() == null
                || !produto.getFornecedor().getId().equals(fornecedor.getId())) {
            throw new IllegalArgumentException("O fornecedor não corresponde ao produto.");
        }
        if (quantidade <= 0 || custoUnitario == null
                || custoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade e custo devem ser maiores que zero.");
        }
        this.id = id;
        this.fornecedor = fornecedor;
        this.produto = produto;
        this.nomeProduto = produto.getNome();
        this.razaoSocialFornecedor = fornecedor.getRazaoSocial();
        this.quantidade = quantidade;
        this.custoUnitario = custoUnitario;
    }

    @Override
    public Integer getId(){
        return id;
    }

    public Fornecedor getFornecedor(){
        return fornecedor;
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

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getRazaoSocialFornecedor() {
        return razaoSocialFornecedor;
    }

    public BigDecimal getCustoTotal() {
        return custoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}