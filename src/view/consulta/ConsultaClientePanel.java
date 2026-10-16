package view.consulta;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import model.consulta.ResumoCliente;
import model.entitties.Cliente;

import view.FormularioPanel;
import view.tablemodel.ResumoClienteTableModel;

public class ConsultaClientePanel extends FormularioPanel {

    public final JTextField busca = new JTextField();

    public final JButton consultar;
    public final JButton limpar;

    public final ResumoClienteTableModel model = new ResumoClienteTableModel();

    private final TableRowSorter<ResumoClienteTableModel> filtro =
            new TableRowSorter<>(model);

    public ConsultaClientePanel() {
        super("Consulta de clientes");

        campo("Nome, CPF ou e-mail", busca);

        consultar = botao("Consultar");
        limpar = botao("Limpar");

        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.setRowSorter(filtro);

        filtro.addRowSorterListener(
                evento -> atualizarResumo()
        );

        mensagem(
                "Pesquise clientes e consulte suas vendas finalizadas."
        );
    }

    public void filtrar() {
        String texto = busca.getText().trim();

        filtro.setRowFilter(
                texto.isEmpty()
                        ? null
                        : RowFilter.regexFilter(
                        "(?iu)" + Pattern.quote(texto),
                        1, 3, 4
                )
        );

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

        if (linha < 0) {
            return null;
        }

        int indice = tabela.convertRowIndexToModel(linha);

        return model.getItem(indice).getCliente();
    }

    private void atualizarResumo() {
        long vendas = 0;
        BigDecimal total = BigDecimal.ZERO;

        // Considera somente os clientes visíveis no filtro selecionado
        for (int linha = 0; linha < tabela.getRowCount(); linha++) {

            int indice = tabela.convertRowIndexToModel(linha);

            ResumoCliente resumo = model.getItem(indice);

            vendas += resumo.getQuantidadeVendas();
            total = total.add(resumo.getValorTotal());
        }

        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );

        mensagem(
                tabela.getRowCount() + " cliente(s) | "
                        + vendas + " venda(s) finalizada(s) | "
                        + "Total gasto: " + moeda.format(total)
        );
    }
}