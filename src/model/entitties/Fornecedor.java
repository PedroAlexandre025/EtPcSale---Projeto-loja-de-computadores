package model.entitties;

public  class Fornecedor extends Pessoa {

    private String cnpj;
    private String razaoSocial;

    public Fornecedor(String nome, String telefone, String email, String cnpj, String razaoSocial) {
        super(nome, telefone, email);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial          ;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

}