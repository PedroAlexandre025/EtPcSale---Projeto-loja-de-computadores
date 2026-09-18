package model.service;

import java.util.List;
import model.entitties.*;
import model.repository.Repositorio;
import interfaces.*;


public class ClienteService {

    private final Repositorio<Cliente, Integer> repositorio;

    private final Repositorio<Venda, Integer> repositorioVenda;

    private int proximoId = 1;

    public ClienteService(Repositorio<Cliente, Integer> repositorio, Repositorio<Venda,  Integer > movimentos) {
        this.repositorio = repositorio;
        this.repositorioVenda = movimentos;
    }

    public List<Cliente> listarClientes() {
        return repositorio.listarTodos();
    }


    public Cliente RegistrarCliente(String nome, String telefone, String cpf, String email) {
        Integer id = proximoId;
        String nomeValido = validacao.texto(nome);
        String telefoneValido = validacao.telefone(telefone);
        String cpfValido = validacao.cpf(cpf);
        String emailValido = validacao.email(email);

        if(!repositorio.buscarPorId(id).isEmpty()) throw new IllegalArgumentException("Já existe um cliente para o ID fornecido.");

        boolean repetido = listarClientes().stream().anyMatch(c -> c.getDocumento().equals(cpfValido));
        if(repetido) throw new IllegalArgumentException("Já existe um cliente cadastrado com o CPF informado.");

        repetido = listarClientes().stream().anyMatch(c -> c.getEmail().equals(emailValido));
        if (repetido) throw new IllegalArgumentException("Email já cadastrado.");

        Cliente cliente = new Cliente(id, nomeValido, telefoneValido, cpfValido, emailValido);
        repositorio.salvar(cliente);
        proximoId++;
        return cliente;


    }

    public void remover(int id){

        if (repositorioVenda.listarTodos().stream().anyMatch(v -> v.getCliente().getId() == id)) {
            throw new IllegalArgumentException("Não é possível remover o cliente, pois existem vendas associadas a ele.");
        }
        repositorio.deletar(id);
    }
}
