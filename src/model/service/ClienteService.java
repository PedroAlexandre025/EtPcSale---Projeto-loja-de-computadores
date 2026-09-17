package model.service;

import java.util.List;
import model.entitties.*;
import model.repository.Repositorio;


public class ClienteService {

    private final Repositorio<Cliente, Integer> repositorio;

    private final Repositorio<Venda, Integer> repositorioVenda;

    private int proximoId = 1;

    public ClienteService(Repositorio<Cliente, Integer> repositorio, Repositorio<Venda,  Interger > Movimentos) {
        this.repositorio = repositorio;
        this.repositorioVenda = Movimentos;
    }

    public List<Cliente> listarClientes() {
        return repositorio.listarTodos();
    }
    
    public Cliente SalvarCliente(String nome, String telefone, String cpf, String email) {
        
        String nomeValido = validacao.texto(nome, "Nome");
        String telefoneValido = validacao.telefone(telefone);
        String cpfValido = validacao.cpf(cpf);
        String emailValido = validacao.email(email);

        if(id != null && repositorio.buscarPorId(id).isEmpty()) throw new IllegalArgumentException("Cliente não encontrado para o ID fornecido.");
        boolean repetido = listar().stream.anyMatch(c -> c.getCpf().equals(cpfValido) && (id == null || !c.getId().equals(id)));
        if(repetido) throw new IllegalArgumentException("Já existe um cliente cadastrado com o CPF informado.");

        if (email == null || email.trim().matches("[^@]+@[^@]+\\.[^@]+")) throw new IllegalArgumentException("Email inválido. O email deve conter um '@' e um domínio válido.");
        
        Cliente cliente = new Cliente(proximoId++, nome, telefone, cpf, email);
        repositorio.salvar(cliente);
        return cliente;
        
    }

    public void remover(int id){
        if ( movimentos.listar().stream().anyMatch(v -> v.getCliente().getId() == id)) {
            throw new IllegalArgumentException("Não é possível remover o cliente, pois existem vendas associadas a ele.");
        }
        repositorio.deletar(id);
    }
}
