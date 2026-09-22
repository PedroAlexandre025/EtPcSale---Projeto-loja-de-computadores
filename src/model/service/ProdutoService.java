package model.service;

import interfaces.validacao;
import model.entitties.Fornecedor;
import model.entitties.ItemVenda;
import model.entitties.Produto;
import model.entitties.Venda;
import model.enums.CategoriaProduto;
import model.enums.StatusVenda;
import model.repository.Repositorio;
import model.repository.RepositorioProduto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoService {
    private final RepositorioProduto produtosRepositorio;
    private final Repositorio<Venda, Integer> vendasRepositorio;
    private int proximoID = 1;


    public ProdutoService(RepositorioProduto produtosRepositorio, Repositorio<Venda, Integer> vendasRepositorio){
        this.produtosRepositorio = produtosRepositorio;
        this.vendasRepositorio = vendasRepositorio;
    }

    public List<Produto> listarProdutos(){
        return produtosRepositorio.listarTodos();
    }

    public Produto registrarProduto(String nome, CategoriaProduto categoriaProduto, BigDecimal preco, int quantidade, Fornecedor fornecedor){
        Integer id = proximoID;
        String nomeValido = validacao.texto(nome);
        BigDecimal precoValido = validacao.preco(preco);
        int quantidadeValida = validacao.quantidade(quantidade);

        boolean repetido = listarProdutos().stream().anyMatch(p-> p.getNome().equals(nomeValido));
        if (repetido){
            throw new IllegalArgumentException("Já existe um produto com esse nome e marca");

        }
        Produto produto = new Produto(id, nomeValido, categoriaProduto, precoValido, quantidadeValida, fornecedor);
        produtosRepositorio.salvar(produto);
        proximoID++;
        return produto;

    }
    public void removerProduto(int id){
        for (Venda v: vendasRepositorio.listarTodos()) {
            if (v.getStatus() == StatusVenda.ABERTA){
                for (ItemVenda i : v.getItens()) {
                    if (i.getProduto().getId().equals(id )) {
                        throw new IllegalArgumentException("Não é possivel remover produto, existem vendas em aberto associadas a ele");
                    }
                }
            }
        }

    }
}
