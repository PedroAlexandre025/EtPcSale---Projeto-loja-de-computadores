package model.service;

import interfaces.validacao;
import model.entitties.Fornecedor;
import model.repository.RepositorioFornecedor;
import model.repository.RepositorioProduto;

import java.util.List;
import model.entitties.Produto;
import model.repository.RepositorioFornecedor;

public class FornecedorService {

    private final RepositorioFornecedor fornecedoresRepositorio;
    private final RepositorioProduto produtoRepositorio;

    public FornecedorService(RepositorioFornecedor fornecedoresRepositorio,  RepositorioProduto produtoRepositorio) {
        this.fornecedoresRepositorio = fornecedoresRepositorio;
        this.produtoRepositorio = produtoRepositorio;
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

    public void atualizarFornecedor(
            int id,
            String nome,
            String telefone,
            String cnpj,
            String razaoSocial) {

        String nomeValido =
                validacao.texto(nome);

        String telefoneValido =
                validacao.telefone(telefone);

        String cnpjValido =
                validacao.cnpj(cnpj);

        String razaoSocialValida =
                validacao.razaoSocial(razaoSocial);

        boolean fornecedorExiste =
                listarFornecedores()
                        .stream()
                        .anyMatch(fornecedor ->
                                fornecedor.getId().equals(id)
                        );

        if (!fornecedorExiste) {
            throw new IllegalArgumentException(
                    "Fornecedor não encontrado."
            );
        }

        boolean cnpjRepetido =
                listarFornecedores()
                        .stream()
                        .anyMatch(fornecedor ->
                                fornecedor.getId() != id
                                        && fornecedor.getDocumento()
                                        .equals(cnpjValido)
                        );

        if (cnpjRepetido) {
            throw new IllegalArgumentException(
                    "CNPJ já registrado para outro fornecedor."
            );
        }

        boolean telefoneRepetido =
                listarFornecedores()
                        .stream()
                        .anyMatch(fornecedor ->
                                fornecedor.getId() != id
                                        && fornecedor.getTelefone()
                                        .equals(telefoneValido)
                        );

        if (telefoneRepetido) {
            throw new IllegalArgumentException(
                    "Telefone já cadastrado para outro fornecedor."
            );
        }

        Fornecedor fornecedorAtualizado =
                new Fornecedor(
                        id,
                        nomeValido,
                        telefoneValido,
                        cnpjValido,
                        razaoSocialValida
                );

        fornecedoresRepositorio.atualizar(
                fornecedorAtualizado
        );

        for(Produto produto : produtoRepositorio.listarTodos()){
            Fornecedor associado = produto.getFornecedor();
            if(associado != null && associado.getId().equals(id)){
                produto.atualizarFornecedor(fornecedorAtualizado);
            }
        }
    }

    public void remover(int id){
        List<String> produtosVinculados = produtoRepositorio.Transformar(
                produto -> produto.getFornecedor() != null
                    && produto.getFornecedor().getId().equals(id),
                Produto::getNome
        );
        if (!produtosVinculados.isEmpty()){
            throw new IllegalArgumentException("Fonecedor associado aos produtos" + String.join(", ", produtosVinculados));
        }
        fornecedoresRepositorio.deletar(id);
    }



}

