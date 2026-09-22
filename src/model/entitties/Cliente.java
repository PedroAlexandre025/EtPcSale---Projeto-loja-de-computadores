package model.entitties;

public  class Cliente extends Pessoa {

    private  final String cpf;
    private final String email;
    public Cliente(int id, String nome, String telefone, String cpf, String email){
        super(id, nome, telefone);
        this.cpf = cpf;
        this.email = email;
    }


    @Override public String getDocumento(){
        return cpf;
    }
    public String getEmail(){
        return email;
    }


}

