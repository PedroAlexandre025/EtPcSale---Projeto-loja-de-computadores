package view.cadastro;

import javax.swing.*;
import model.entitties.Cliente;
import view.FormularioPanel;
import view.tablemodel.ClienteTableModel;

public class CadastroClientePanel extends FormularioPanel {
    public final JTextField nome = new JTextField();
    public final JTextField telefone = new JTextField();
    public final JTextField cpf = new JTextField();
    public final JTextField email = new JTextField();
    public final JButton salvar, remover, limpar, consultar;
    public final ClienteTableModel model = new ClienteTableModel();

    public CadastroClientePanel() {
        super("Cadastro de clientes");
        campo("Nome", nome);
        campo("Telefone", telefone);
        campo("CPF", cpf);
        campo("E-mail", email);
        salvar = botao("Salvar");
        remover = botao("Remover");
        limpar = botao("Limpar", this::limparCampos);
        consultar = botao("Consultar clientes");
        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Cliente selecionado = getSelecionado();
                if (selecionado != null) {
                    preencherCampos(selecionado);
                }
            }
        });
        mensagem("Preencha os dados do cliente. Selecione uma linha para visualizar o cadastro.");
    }

    public Cliente getSelecionado() {
        int linha = tabela.getSelectedRow();
        return linha < 0 ? null : model.getItem(tabela.convertRowIndexToModel(linha));
    }

    public void preencherCampos(Cliente cliente) {
        nome.setText(cliente.getNome());
        telefone.setText(cliente.getTelefone());
        cpf.setText(cliente.getDocumento());
        email.setText(cliente.getEmail());
    }

    public void limparCampos() {
        tabela.clearSelection();
        nome.setText("");
        telefone.setText("");
        cpf.setText("");
        email.setText("");
        nome.requestFocusInWindow();
    }
}
