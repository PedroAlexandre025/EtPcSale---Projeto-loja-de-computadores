package view.movimento;

import java.awt.BorderLayout;
import java.awt.Font;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import model.entitties.Cliente;
import model.entitties.Produto;
import model.entitties.Venda;
import model.enums.FormaPagamentos;
import view.FormularioPanel;
import view.tablemodel.ItemVendaTableModel;
import view.tablemodel.VendaTableModel;

public class MovimentoVendaPanel extends FormularioPanel {

    public final JComboBox<Cliente> cliente = new JComboBox<>();
    public final JComboBox<Produto> produto = new JComboBox<>();
    public final JSpinner quantidade = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
    public final JComboBox<FormaPagamentos> pagamento = new JComboBox<>(FormaPagamentos.values());
    public final JTextField desconto = new JTextField("0");

    public final JButton adicionarItem;
    public final JButton removerItem;
    public final JButton finalizar;
    public final JButton limpar;
    public final JButton cancelarVenda;

    public final ItemVendaTableModel model = new ItemVendaTableModel();
    public final VendaTableModel modelVendas = new VendaTableModel();
    public final JTable tabelaVendas = new JTable(modelVendas);
    public final JLabel total = new JLabel("", SwingConstants.RIGHT);

    public MovimentoVendaPanel() {
        super("Vendas");

        campo("Cliente", cliente);
        campo("Pagamento", pagamento);
        campo("Produto", produto);
        campo("Quantidade", quantidade);
        campo("Desconto (R$)", desconto);

        // O controller conectará as operações de venda aos botões.
        adicionarItem = botao("Adicionar item");
        removerItem = botao("Remover item");
        finalizar = botao("Finalizar venda");
        limpar = botao("Limpar", this::limparCampos);
        cancelarVenda = botao("Cancelar venda");

        tabela.setModel(model);
        configurarTabela(tabelaVendas);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Itens da venda", new JScrollPane(tabela));
        abas.addTab("Vendas registradas", new JScrollPane(tabelaVendas));

        total.setFont(total.getFont().deriveFont(Font.BOLD, 18f));
        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.add(abas, BorderLayout.CENTER);
        centro.add(total, BorderLayout.SOUTH);

        // As abas ocupam o espaço da tabela padrão do formulário.
        remove(((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
        add(centro, BorderLayout.CENTER);

        limparCampos();
        mensagem("Selecione cliente e produto. Consulte o histórico na aba Vendas registradas.");
    }

    public void definirClientes(List<Cliente> clientes) {
        cliente.removeAllItems();
        for (Cliente item : clientes) {
            cliente.addItem(item);
        }
        cliente.setSelectedIndex(-1);
    }

    public void definirProdutos(List<Produto> produtos) {
        produto.removeAllItems();
        for (Produto item : produtos) {
            produto.addItem(item);
        }
        produto.setSelectedIndex(-1);
    }

    public void exibirTotal(BigDecimal valor) {
        NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        total.setText("Total da venda: " + moeda.format(valor));
    }

    public void limparCampos() {
        cliente.setSelectedIndex(-1);
        produto.setSelectedIndex(-1);
        quantidade.setValue(1);
        desconto.setText("0");
        pagamento.setSelectedIndex(0);
        model.setDados(List.of());
        tabela.clearSelection();
        exibirTotal(BigDecimal.ZERO);
    }

    public Venda getVendaSelecionada() {
        int linha = tabelaVendas.getSelectedRow();
        if (linha < 0) {
            return null;
        }
        return modelVendas.getItem(tabelaVendas.convertRowIndexToModel(linha));
    }
}
