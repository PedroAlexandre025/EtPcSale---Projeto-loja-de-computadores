package etpcsale.controller;

import etpcsale.model.entitties.Cliente;
import etpcsale.model.entitties.Produto;
import etpcsale.model.entitties.Venda;

public class ClienteController extends ControllerBase{
    private final CadastroClientePanel view;
    private final ClienteService service;

    public ClienteController(cadastroClientePanel view, ClienteService service) {
        this.view = view;
        this.service = service;
    
        // fazer a desgraça do botão de salvar funcionar
    
    }

    
    
}