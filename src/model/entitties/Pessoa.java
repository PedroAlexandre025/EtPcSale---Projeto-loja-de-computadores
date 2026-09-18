package model.entitties;

import interfaces.Identificavel;

public abstract class Pessoa implements Identificavel<Integer> {
    private final int id;
    private final String nome, telefone;
    protected Pessoa ( int id, String nome, String telefone){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }
    @Override
    public Integer getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getTelefone(){
        return telefone;

    }

    public abstract String getDocumento();

    @Override
    public String toString(){
        return id + " - " + nome;
    }

}