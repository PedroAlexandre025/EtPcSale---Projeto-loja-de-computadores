package view.cadastro;

import javax.swing.*;
import view.FormularioPanel;
import view.tablemodel.ClienteTableModel;
import model.entitties.*;

public class CadastroClientePanel extends FormularioPanel {

    public final JTextField nome = new JTextField(), telefone = new JTextField(), cpf = new JTextField(), email = new JTextField();
    public JButton salvar, remover, limpar, consultar;
    public final ClienteTableModel model = new ClienteTableModel();

    public CadastroClientePanel() {
        super("Cadastro de Clientes");
        campo("Nome", nome);
        campo("Telefone", telefone);
        campo("CPF", cpf);
        campo("Email", email);
        tabela.setModel(new ClienteTableModel();
        resumo.setText("Selecione uma linha para alterar, limpe o formulario para cadastrar um novo cliente");

    }
    public void limparCampos(){
        nome.setText("");
        telefone.setText("");
        cpf.setText("");
        email.setText("");
    }

}