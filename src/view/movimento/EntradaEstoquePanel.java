package view.movimento;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import model.entitties.Produto;
import view.FormularioPanel;
import view.tablemodel.EntradaEstoqueTableModel;

public class EntradaEstoquePanel extends FormularioPanel {

    public final JComboBox<Produto> produto = new JComboBox<>();
    public final JTextField fornecedor = new JTextField();
    public final JTextField estoqueAtual = new JTextField();
    public final JSpinner quantidade = new JSpinner(
            new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)
    );
    public final JTextField custoUnitario = new JTextField();
    public final JButton registrar, limpar, atualizar;
    public final EntradaEstoqueTableModel model =
            new EntradaEstoqueTableModel();

    public EntradaEstoquePanel() {
        super("Entrada de estoque");
        campo("Produto", produto);
        campo("Fornecedor", fornecedor);
        campo("Estoque atual", estoqueAtual);
        campo("Quantidade recebida", quantidade);
        campo("Custo unitário (R$)", custoUnitario);

        fornecedor.setEditable(false);
        estoqueAtual.setEditable(false);
        custoUnitario.setToolTipText("Exemplo: 149,90");
        registrar = botao("Registrar entrada");
        limpar = botao("Limpar");
        atualizar = botao("Atualizar");
        tabela.setModel(model);
        configurarTabela(tabela);
        mensagem("Selecione um produto e informe a quantidade e o custo recebido.");
    }

    public void definirProdutos(List<Produto> produtos) {
        Produto anterior = (Produto) produto.getSelectedItem();
        Integer id = anterior == null ? null : anterior.getId();
        produto.removeAllItems();

        for (Produto item : produtos) {
            produto.addItem(item);
        }
        produto.setSelectedIndex(-1);

        if (id != null) {
            for (int i = 0; i < produto.getItemCount(); i++) {
                if (produto.getItemAt(i).getId().equals(id)) {
                    produto.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public void exibirProduto(Produto selecionado) {
        fornecedor.setText(selecionado == null
                || selecionado.getFornecedor() == null ? ""
                : selecionado.getFornecedor().getRazaoSocial());
        estoqueAtual.setText(selecionado == null
                ? "" : String.valueOf(selecionado.getEstoque()));
    }

    public void limparCampos() {
        produto.setSelectedIndex(-1);
        fornecedor.setText("");
        estoqueAtual.setText("");
        quantidade.setValue(1);
        custoUnitario.setText("");
        tabela.clearSelection();
    }

    public void exibirResumo(int registros, BigDecimal total) {
        NumberFormat moeda = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("pt-BR")
        );
        mensagem(registros + " entrada(s) registrada(s) | Custo total: "
                + moeda.format(total));
    }
}