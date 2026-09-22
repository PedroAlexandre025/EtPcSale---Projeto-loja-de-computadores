package controller;

import model.entitties.Cliente;
import model.entitties.Venda;
import model.repository.Repositorio;
import model.service.ClienteService;
import view.cadastro.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteController extends ControllerBase implements ActionListener {
    private final CadastroClientePanel view;

    private Repositorio<Cliente, Integer> clienteRepositorio = new Repositorio<>();
    private Repositorio<Venda, Integer> vendaRepositorio = new Repositorio<>();

    private ClienteService service;

    public ClienteController(CadastroClientePanel view ) {
        this.view = view;
        view.salvar.addActionListener(this);
        service  = new ClienteService(clienteRepositorio, vendaRepositorio);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.salvar){
            String name = view.nome.getText();
            String tel = view.telefone.getText();
            String cpf = view.cpf.getText();
            String email = view.email.getText();

            service.RegistrarCliente(name, tel, cpf, email);

            for(Cliente C: clienteRepositorio.listarTodos()){
                System.out.println(C);
            }


        }
    }
    
    
}