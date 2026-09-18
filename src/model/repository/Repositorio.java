package model.repository;

import interfaces.Identificavel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Repositorio< T extends Identificavel<ID>, ID> {
     List<T> list = new ArrayList<>();
    public void salvar(T objeto){
        list.add(objeto);
    }

    public Optional<T> buscarPorId(ID id){
        return list.stream()
                .filter(objeto -> objeto.getId().equals(id))
                .findFirst();
        //return Optional.empty();
    }

    public void deletar(ID id){
        list.removeIf(objeto -> objeto.getId().equals(id));

    }

    public List<T> listarTodos(){
        return List.copyOf(list);
    }
}
