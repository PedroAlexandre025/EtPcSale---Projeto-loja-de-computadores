package model.service;

import interfaces.validacao;
import model.entitties.Fornecedor;
import model.repository.RepositorioFornecedor;

import java.util.List;

public class FornecedorService {

    private final RepositorioFornecedor fornecedoresRepositorio;

    public FornecedorService(RepositorioFornecedor fornecedoresRepositorio) {
        this.fornecedoresRepositorio = fornecedoresRepositorio;
    }

    public List<Fornecedor> listarFornecedores(){
        return fornecedoresRepositorio.listarTodos();
    }
    private int proximoID = 1;

    public Fornecedor RegistrarFornecedor(String nome, String telefone, String CNPJ, String
                                            razaoSocial){
        Integer id = proximoID;
        String nomeValido = validacao.texto(nome);
        String telefoneValido = validacao.telefone(telefone);
        String cnpjValido = validacao.cnpj(CNPJ);
        String razaoSocialValido = validacao.razaoSocial(razaoSocial);

        boolean repetido = listarFornecedores().stream().anyMatch(f -> f.getDocumento().equals(CNPJ));
        if (repetido) throw new IllegalArgumentException("CNPJ já registrado");
        repetido = listarFornecedores().stream().anyMatch(f -> f.getTelefone().equals(telefone));
        if (repetido) throw new IllegalArgumentException("Telefone já cadastrado para outro Fornecedor");

        Fornecedor fornecedor = new Fornecedor(id, nomeValido,telefoneValido ,cnpjValido, razaoSocialValido );
        fornecedoresRepositorio.salvar(fornecedor);
        proximoID++;
        return fornecedor;

    }

    public void remover(int id){
        fornecedoresRepositorio.deletar(id);
    }


}

