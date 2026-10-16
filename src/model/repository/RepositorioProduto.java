package model.repository;

import model.entitties.Produto;
import model.enums.CategoriaProduto;

import java.util.ArrayList;
import java.util.List;

public class RepositorioProduto extends Repositorio<Produto, Integer>{

    public List<Produto > ordernarporQuantidade(){
       List<Produto> ordenados = new ArrayList<>(listarTodos());
        ordenados.sort(java.util.Comparator.comparingInt(Produto::getEstoque).thenComparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER)
        );

        return ordenados;
    }

    public List<Produto> RetornaDeUmaCategoria(CategoriaProduto categoriaProduto){
        return listarTodos().stream().filter(p-> p.getCategoria() == categoriaProduto).toList();
    }

}
