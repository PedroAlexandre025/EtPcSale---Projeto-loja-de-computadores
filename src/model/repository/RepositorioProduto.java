package model.repository;

import model.entitties.Produto;
import model.enums.CategoriaProduto;

import java.util.ArrayList;
import java.util.List;

public class RepositorioProduto extends Repositorio<Produto, Integer>{

    public List<Produto > ordernarporQuantidade(){
        List<Produto> ordenados = new ArrayList<>(listarTodos());
        ordenados.sort((p1, p2) -> Integer.compare(p1.getEstoque(), p2.getEstoque()));

        return ordenados;
    }

    public List<Produto> RetornaDeUmaCategoria(CategoriaProduto categoriaProduto){
        return listarTodos().stream().filter(p-> p.getCategoria() == categoriaProduto).toList();
    }

}
