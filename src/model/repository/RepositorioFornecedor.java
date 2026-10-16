package model.repository;

import model.entitties.Fornecedor;
import model.entitties.Produto;

import java.util.ArrayList;
import java.util.List;

public class RepositorioFornecedor extends Repositorio<Fornecedor, Integer>{

    public List<Produto> ProdutosFornecidos(
            Fornecedor fornecedor,
            RepositorioProduto repositorioProduto) {

        if (fornecedor == null) {
            throw new IllegalArgumentException(
                    "Informe um fornecedor."
            );
        }

        return repositorioProduto.listarTodos()
                .stream()
                .filter(produto ->
                        produto.getFornecedor() != null
                                && produto.getFornecedor().getId()
                                .equals(fornecedor.getId()))
                .toList();
    }
}
