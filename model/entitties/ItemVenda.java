package etpcsale.model.entitties;

import java.math.BigDecimal; // caso se perguntem essa biblioteca calcula com extrema precisao para valroes monetários


public class ItemVenda {

    private final Produto produto;
    private  final int quantidade;
    private final BigDecimal precoUnitario;

    public ItemVenda(Produto produto, int quantidade){
        if (produto == null || quantidade <= 0) throw new IllegalArgumentException("Item invalido");
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();

    }

    public Produto getProduto(){
        return  produto;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getSubTotal(){
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade)); // esse crl aqui faz o calculo do subtotal preço unitário × quantidade com precisao sem arredondar
    }
}
