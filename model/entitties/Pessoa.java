package etpcsale.model.entitties;

public abstract class Pessoa{
    private final int id;
    private final String nome, telefone;
    protected Pessoa ( int id, String nome, String telefone){
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getId(){
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
    public  String toString(){
        return id + " - " + nome;
    }

}