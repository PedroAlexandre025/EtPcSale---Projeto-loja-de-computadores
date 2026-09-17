package model.entitties;

public  class Fornecedor extends Pessoa {

    private final String  cnpj;
    private String razaoSocial;

    public Fornecedor(int id, String nome, String telefone, String cnpj, String razaoSocial) {
        super(id, nome, telefone);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial          ;
    }

    public String getDocumento() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

}