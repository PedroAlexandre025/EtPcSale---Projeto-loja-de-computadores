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
    private final VendaController vendaController;

    public ClienteController(CadastroClientePanel view, ClienteService service, ConsultaController consultaController, VendaController vendaController) {
        this.view = view;
        this.service = service;
        this.consultaController = consultaController;
        this.vendaController = vendaController;

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
            Cliente clienteSelecionado =
                    view.getSelecionado();

            String nome =
                    view.nome.getText().trim();

            String cpf =
                    view.cpf.getText().trim();

            String telefone =
                    view.telefone.getText().trim();

            String email =
                    view.email.getText().trim();

            if (clienteSelecionado == null) {
                Cliente cliente =
                        service.RegistrarCliente(
                                nome,
                                telefone,
                                cpf,
                                email
                        );

                view.model.addDados(cliente);

                consultaController.atualizarTabela();
                vendaController.atualizarClientes();

                JOptionPane.showMessageDialog(
                        view,
                        "Cliente salvo com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                service.atualizar(
                        clienteSelecionado.getId(),
                        nome,
                        telefone,
                        cpf,
                        email
                );

                carregarClientes();

                vendaController.atualizarClientes();
                consultaController.atualizarTabela();

                JOptionPane.showMessageDialog(
                        view,
                        "Cliente atualizado com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            view.limparCampos();

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

            carregarClientes();

            consultaController.atualizarTabela();
            vendaController.atualizarClientes();

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

    private void carregarClientes() {
        view.model.setDados(
                service.listarClientes()
        );
    }

}