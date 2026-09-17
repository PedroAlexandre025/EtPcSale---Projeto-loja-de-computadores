package controller;

import model.service.ClienteService;
import view.cadastro.*;

public class ClienteController extends ControllerBase{
    private final CadastroClientePanel view;
    private final ClienteService service;

    public ClienteController(CadastroClientePanel view, ClienteService service) {
        this.view = view;
        this.service = service;
    
        // fazer a desgraça do botão de salvar funcionar
    
    }

    
    
}