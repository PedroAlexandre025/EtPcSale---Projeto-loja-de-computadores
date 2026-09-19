package view.cadastro;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import model.entitties.Produto;
import model.enums.CategoriaProduto;
import view.FormularioPanel;
import view.tablemodel.ProdutoTableModel;

public class CadastroProdutoPanel extends FormularioPanel {

    public final JTextField nome = new JTextField();
    public final JComboBox<CategoriaProduto> categoria = new JComboBox<>(CategoriaProduto.values());
    public final JTextField preco = new JTextField();
    public final JSpinner estoque = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    public final JButton salvar, remover, limpar;
    public final ProdutoTableModel model = new ProdutoTableModel();

    public CadastroProdutoPanel() {
        super("Cadastro de Produtos");
        campo("Nome", nome);
        campo("Categoria", categoria);
        campo("Preço (R$)", preco);
        campo("Estoque", estoque);
        preco.setToolTipText("Exemplo: 1.299,90");

        salvar = botao("Salvar");
        remover = botao("Remover");
        limpar = botao("Limpar", this::limparCampos);

        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                Produto produto = getSelecionado();
                if (produto != null) {
                    preencherCampos(produto);
                }
            }
        });
        mensagem("Preencha os campos ou selecione um produto para visualizar seus dados.");
    }

    public void limparCampos() {
        tabela.clearSelection();
        nome.setText("");
        categoria.setSelectedIndex(0);
        preco.setText("");
        estoque.setValue(0);
        nome.requestFocusInWindow();
    }

    public void preencherCampos(Produto produto) {
        if (produto == null) {
            limparCampos();
            return;
        }
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.forLanguageTag("pt-BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        nome.setText(produto.getNome());
        categoria.setSelectedItem(produto.getCategoria());
        preco.setText(formato.format(produto.getPreco()));
        estoque.setValue(produto.getEstoque());
    }

    public Produto getSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            return null;
        }
        return model.getItem(tabela.convertRowIndexToModel(linha));
    }
}
