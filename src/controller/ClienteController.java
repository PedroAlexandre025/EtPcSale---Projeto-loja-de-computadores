package controller;

import model.entitties.*;
import model.service.ClienteService;
import view.cadastro.*;
import view.tablemodel.ClienteTableModel;

import javax.swing.*;

public class ClienteController extends ControllerBase {
    private final CadastroClientePanel view;
    private final ClienteService service;
    private final ConsultaController consultaController;

    public ClienteController(CadastroClientePanel view, ClienteService service, ConsultaController consultaController) {
        this.view = view;
        this.service = service;
        this.consultaController = consultaController;

        view.model.setDados(service.listarClientes());

        eventos();
    }


    @Override
    public void eventos(){
        view.salvar.addActionListener(e -> {salvarCliente();
        });
        view.remover.addActionListener(e -> {removerCliente();});
    }

    private void salvarCliente() {
        try {
            String nome = view.nome.getText();
            String cpf = view.cpf.getText();
            String telefone = view.telefone.getText();
            String email = view.email.getText();

            Cliente cliente = service.RegistrarCliente(nome,
                    telefone,
                    cpf,
                    email);


            view.model.addDados(cliente);
            consultaController.atualizarTabela();
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Cliente salvo com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível salvar",
                    JOptionPane.WARNING_MESSAGE
            );

        }
    }

    private void removerCliente() {
        try {
            Cliente cliente = view.getSelecionado();

            if (cliente == null) {
                JOptionPane.showMessageDialog(
                        view,
                        "Selecione um cliente na tabela para remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            boolean confirmou = view.confirmar(
                    "Deseja remover o cliente " + cliente.getNome() + "?"
            );

            if (!confirmou) {
                return;
            }

            service.remover(cliente.getId());

            int linhaView = view.tabela.getSelectedRow();
            int linhaModelo = view.tabela.convertRowIndexToModel(linhaView);
            view.model.removeDados(linhaModelo);
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Cliente removido com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    view,
                    ex.getMessage(),
                    "Não foi possível remover",
                    JOptionPane.WARNING_MESSAGE
            );


        }
    }
}