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
        return produtosRepositorio.ordernarporQuantidade();
    }

    public Produto registrarProduto(String nome, CategoriaProduto categoriaProduto, BigDecimal preco, int quantidade, Fornecedor fornecedor){
        ValidarRelacionamentos(categoriaProduto, fornecedor);
        Integer id = proximoID;
        String nomeValido = validacao.texto(nome);
        BigDecimal precoValido = validacao.preco(preco);
        int quantidadeValida = validacao.quantidade(quantidade);

        // alteraçao feita pq agora cadastro e alteração usa a mesma regra de comparação
        boolean repetido = produtosRepositorio.Transformar(Produto::getNome).stream().anyMatch(nomeExistente->nomeExistente.equalsIgnoreCase(nomeValido));
        if (repetido){
            throw new IllegalArgumentException("Já existe um produto com esse nome e marca");

        }
        Produto produto = new Produto(id, nomeValido, categoriaProduto, precoValido, quantidadeValida, fornecedor);
        produtosRepositorio.salvar(produto);
        proximoID++;
        return produto;

    }
    public void atualizarProduto(

            int id,
            String nome,
            CategoriaProduto categoriaProduto,
            BigDecimal preco,
            int quantidade,
            Fornecedor fornecedor) {

        ValidarRelacionamentos(categoriaProduto, fornecedor);

        String nomeValido =
                validacao.texto(nome);

        BigDecimal precoValido =
                validacao.preco(preco);

        int quantidadeValida =
                validacao.quantidade(quantidade);

        boolean repetido =
                listarProdutos()
                        .stream()
                        .anyMatch(produto ->
                                produto.getId() != id
                                        && produto.getNome()
                                        .equalsIgnoreCase(nomeValido)
                        );

        if (repetido) {
            throw new IllegalArgumentException(
                    "Já existe outro produto com esse nome."
            );
        }

        Produto produtoAtualizado =
                new Produto(
                        id,
                        nomeValido,
                        categoriaProduto,
                        precoValido,
                        quantidadeValida,
                        fornecedor
                );

        produtosRepositorio.atualizar(
                produtoAtualizado
        );
    }

    public void removerProduto(int id) {
        for (Venda venda : vendasRepositorio.listarTodos()) {
            if (venda.getStatus() == StatusVenda.ABERTA) {
                for (ItemVenda item : venda.getItens()) {
                    if (item.getProduto().getId().equals(id)) {
                        throw new IllegalArgumentException(
                                "Não é possível remover o produto, "
                                        + "pois existem vendas em aberto associadas a ele."
                        );
                    }
                }
            }
        }

        produtosRepositorio.deletar(id);
     }


     private void ValidarRelacionamentos(CategoriaProduto categoriaProduto, Fornecedor fornecedor){
        if (categoriaProduto == null){
            throw new IllegalArgumentException("selecione um categoria produto");
        }

        if (fornecedor == null){
            throw new IllegalArgumentException("selecione um fornecedor produto");
        }
     }
    }