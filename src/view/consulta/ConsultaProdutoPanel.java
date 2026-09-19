package view.consulta;

import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.entitties.Produto;
import model.enums.CategoriaProduto;
import view.FormularioPanel;
import view.tablemodel.ProdutoTableModel;

public class ConsultaProdutoPanel extends FormularioPanel {

    public final JTextField busca = new JTextField();
    public final JComboBox<Object> categoria = new JComboBox<>();
    public final JButton consultar, limpar;
    public final ProdutoTableModel model = new ProdutoTableModel();
    private final TableRowSorter<ProdutoTableModel> ordenador = new TableRowSorter<>(model);

    public ConsultaProdutoPanel() {
        super("Consulta de Produtos");
        categoria.addItem("Todas as categorias");
        for (CategoriaProduto valor : CategoriaProduto.values()) {
            categoria.addItem(valor);
        }
        campo("Nome ou ID", busca);
        campo("Categoria", categoria);
        consultar = botao("Consultar", this::aplicarFiltros);
        limpar = botao("Limpar", this::limparFiltros);

        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.setRowSorter(ordenador);
        ordenador.addRowSorterListener(evento -> atualizarResumo());
        busca.addActionListener(evento -> aplicarFiltros());
        categoria.addActionListener(evento -> aplicarFiltros());
        mensagem("Pesquise pelo nome ou ID e escolha uma categoria para filtrar a tabela.");
    }

    public void aplicarFiltros() {
        String texto = busca.getText().trim().toLowerCase(Locale.ROOT);
        Object categoriaSelecionada = categoria.getSelectedItem();

        // A nossa busca usa texto literal: caracteres como ( e [ também podem ser pesquisados.
        ordenador.setRowFilter(new RowFilter<ProdutoTableModel, Integer>() {
            @Override
            public boolean include(Entry<? extends ProdutoTableModel, ? extends Integer> linha) {
                Produto produto = model.getItem(linha.getIdentifier());
                boolean correspondeTexto = String.valueOf(produto.getId()).contains(texto)
                        || produto.getNome().toLowerCase(Locale.ROOT).contains(texto);
                boolean correspondeCategoria = !(categoriaSelecionada instanceof CategoriaProduto)
                        || produto.getCategoria() == categoriaSelecionada;
                return correspondeTexto && correspondeCategoria;
            }
        });
        atualizarResumo();
    }

    public void limparFiltros() {
        busca.setText("");
        categoria.setSelectedIndex(0);
        ordenador.setRowFilter(null);
        tabela.clearSelection();
        mensagem("Exibindo todos os produtos: " + tabela.getRowCount() + ".");
        busca.requestFocusInWindow();
    }

    private void atualizarResumo() {
        mensagem(tabela.getRowCount() + " produto(s) encontrado(s).");
    }
}
