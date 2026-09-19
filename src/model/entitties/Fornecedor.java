package model.entitties;

public class Fornecedor extends Pessoa {

    private final String cnpj;
    private String razaoSocial;
    private Produto produtoFornecido;

    public Fornecedor(int id, String nome, String telefone,
                      String cnpj, String razaoSocial,
                      Produto produtoFornecido) {
        super(id, nome, telefone);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.produtoFornecido = produtoFornecido;
    }

    @Override
    public String getDocumento() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Produto getProdutoFornecido() {
        return produtoFornecido;
    }

    public void setProdutoFornecido(Produto produtoFornecido) {
        this.produtoFornecido = produtoFornecido;
    }
}