package model.repository;

import interfaces.Identificavel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.function.Function;
import java.util.function.Predicate;


public class Repositorio< T extends Identificavel<ID>, ID> {
     List<T> list = new ArrayList<>();
    public void salvar(T objeto){
        list.add(objeto);

    }

    public void atualizar(T objetoAtualizado) {
        for (int i = 0; i < list.size(); i++) {
            T objetoAtual = list.get(i);

            if (objetoAtual.getId()
                    .equals(objetoAtualizado.getId())) {

                list.set(i, objetoAtualizado);
                return;
            }
        }

        throw new IllegalArgumentException(
                "Registro não encontrado."
        );
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


    public <R> List<R> Transformar(
            Function<T, R> transformacao){
        return  Transformar(objeto -> true, transformacao);
    }

    public <R> List<R> Transformar(
            Predicate<T> filtro,
            Function<T, R> transformacao
    ){
        return list.stream().filter(filtro).map(transformacao).toList();

    }



}
