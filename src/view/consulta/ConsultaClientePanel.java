package view.consulta;

import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import model.entitties.Cliente;
import view.FormularioPanel;
import view.tablemodel.ClienteTableModel;

public class ConsultaClientePanel extends FormularioPanel {
    public final JTextField busca = new JTextField();
    public final JButton consultar, limpar;
    public final ClienteTableModel model = new ClienteTableModel();
    private final TableRowSorter<ClienteTableModel> filtro = new TableRowSorter<>(model);

    public ConsultaClientePanel() {
        super("Consulta de clientes");
        campo("Nome, CPF ou e-mail", busca);
        consultar = botao("Consultar", this::filtrar);
        limpar = botao("Limpar", this::limparCampos);
        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.setRowSorter(filtro);
        busca.addActionListener(e -> filtrar());
        filtro.addRowSorterListener(e -> atualizarResumo());
        mensagem("Digite parte do nome, CPF ou e-mail para filtrar a lista de clientes.");
    }

    public void filtrar() {
        String texto = busca.getText().trim();
        filtro.setRowFilter(texto.isEmpty() ? null
                : RowFilter.regexFilter("(?iu)" + Pattern.quote(texto), 1, 3, 4));
        atualizarResumo();
    }

    public void limparCampos() {
        busca.setText("");
        tabela.clearSelection();
        filtrar();
        busca.requestFocusInWindow();
    }

    public Cliente getSelecionado() {
        int linha = tabela.getSelectedRow();
        return linha < 0 ? null : model.getItem(tabela.convertRowIndexToModel(linha));
    }

    private void atualizarResumo() {
        mensagem(tabela.getRowCount() + " cliente(s) encontrado(s).");
    }
}
