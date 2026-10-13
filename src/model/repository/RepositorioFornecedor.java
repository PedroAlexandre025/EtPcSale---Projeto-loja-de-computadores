package model.repository;

import model.entitties.Fornecedor;
import model.entitties.Produto;

import java.util.ArrayList;
import java.util.List;

public class RepositorioFornecedor extends Repositorio<Fornecedor, Integer>{


    public List<Produto> ProdutosFornecidos(Fornecedor fornecedor, RepositorioProduto repositorioProduto){

        return repositorioProduto.listarTodos().stream().filter(p-> p.getFornecedor() == fornecedor).toList();
    }
}
