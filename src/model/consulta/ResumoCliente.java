package model.consulta;

import java.math.BigDecimal;
import model.entitties.Cliente;


public class ResumoCliente {


    private final Cliente cliente;
    private final long quantidadeVendas;
    private final BigDecimal valorTotal;

    public ResumoCliente(Cliente cliente, long quantidadeVendas, BigDecimal valorTotal) {
        this.cliente = cliente;
        this.quantidadeVendas = quantidadeVendas;
        this.valorTotal = valorTotal;
    }


    public Cliente getCliente() {
        return cliente;
    }

    public long getQuantidadeVendas() {
        return quantidadeVendas;
    }
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
