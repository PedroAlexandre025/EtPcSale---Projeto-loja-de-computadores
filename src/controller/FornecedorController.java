package controller;

import javax.swing.JOptionPane;

import model.entitties.Fornecedor;
import model.service.FornecedorService;
import view.cadastro.CadastroFornecedorPanel;

public class FornecedorController extends ControllerBase {

    private final CadastroFornecedorPanel view;
    private final FornecedorService service;
    private final ProdutoController produtoController;

    public FornecedorController(
            CadastroFornecedorPanel view,
            FornecedorService service,
            ProdutoController produtoController
    ) {
        this.view = view;
        this.service = service;
        this.produtoController = produtoController;

        carregarFornecedores();
        eventos();
    }

    private void carregarFornecedores() {
        view.model.setDados(service.listarFornecedores());
    }

    @Override
    public void eventos() {
        view.salvar.addActionListener(
                evento -> salvarFornecedor()
        );

        view.remover.addActionListener(
                evento -> removerFornecedor()
        );
    }

    private void salvarFornecedor() {
        try {
            Fornecedor fornecedorSelecionado = view.getSelecionado();

            String nome = view.nome.getText().trim();
            String telefone = view.telefone.getText().trim();
            String cnpj = view.cnpj.getText().trim();
            String razaoSocial = view.razaoSocial.getText().trim();

            if (fornecedorSelecionado == null) {
                Fornecedor fornecedor = service.RegistrarFornecedor(
                        nome,
                        telefone,
                        cnpj,
                        razaoSocial
                );

                view.model.addDados(fornecedor);
                produtoController.atualizarFornecedores();

                JOptionPane.showMessageDialog(
                        view,
                        "Fornecedor cadastrado com sucesso.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                service.atualizarFornecedor(
                        fornecedorSelecionado.getId(),
                        nome,
                        telefone,
                        cnpj,
                        razaoSocial
                );

                carregarFornecedores();

                JOptionPane.showMessageDialog(
                        view,
                        "Fornecedor atualizado com sucesso.",
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

    private void removerFornecedor() {
        try {
            Fornecedor fornecedor = view.getSelecionado();

            if (fornecedor == null) {
                JOptionPane.showMessageDialog(
                        view,
                        "Selecione um fornecedor para remover.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            boolean confirmou = view.confirmar(
                    "Deseja remover o fornecedor \""
                            + fornecedor.getRazaoSocial()
                            + "\"?"
            );

            if (!confirmou) {
                return;
            }

            service.remover(fornecedor.getId());

            carregarFornecedores();
            view.limparCampos();

            JOptionPane.showMessageDialog(
                    view,
                    "Fornecedor removido com sucesso.",
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

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    view,
                    "Erro ao remover fornecedor: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void atualizarTabela() {
        carregarFornecedores();
    }
}