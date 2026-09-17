package model.entitties;

import java.math.BigDecimal;
import model.enums.*;

public class Produto{
    private final int id;
    private final String nome;
    private final CategoriaProduto categoria;
    private  final BigDecimal preco;
    private int estoque;
    public Produto(int id, String nome, CategoriaProduto categoria, BigDecimal preco, int estoque){
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getId(){
            return id;
    }

    public String getNome(){
        return  nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public CategoriaProduto getCategoria(){
        return categoria;
    }

    public  int getEstoque(){
        return estoque;
    }

    public void adicionarEstoque(int quantidade){
        if ( quantidade <= 0  || quantidade > estoque ) throw new IllegalArgumentException("Estoque insuficiente");
        estoque -= quantidade; 
    }

    @Override 
    public String toString(){
        return id + " - " + nome;
    }
}