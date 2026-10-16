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
import java.util.function.Predicate;

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
        consultar = botao("Consultar");
        limpar = botao("Limpar");

        tabela.setModel(model);
        configurarTabela(tabela);
        tabela.setRowSorter(ordenador);
        ordenador.addRowSorterListener(evento -> atualizarResumo());
        categoria.addActionListener(evento -> aplicarFiltros());
        mensagem("Pesquise pelo nome ou ID e escolha uma categoria para filtrar a tabela.");
    }

    public void aplicarFiltros() {

        String texto = busca.getText().trim().toLowerCase(Locale.ROOT);

        Object selecionada = categoria.getSelectedItem();

        Predicate<Produto> porTexto = produto -> String.valueOf(produto.getId()).contains(texto) || produto.getNome().toLowerCase(Locale.ROOT).contains(texto);

        Predicate<Produto> PorCategoria = produto -> !(selecionada instanceof CategoriaProduto) || produto.getCategoria() == selecionada;

        Predicate<Produto> criterio = porTexto.and(PorCategoria);

        // filtro os produtos na tabela mostrando só os que corresponde
        // é essa gambiarra aqui, se tem a linha o produto = true, se nao tem = false esconde a linha
        ordenador.setRowFilter(
                new RowFilter<ProdutoTableModel, Integer>(){

                    @Override
                    public boolean include(  // nao se assuste, ainda é java...
                            // é aqui o false e true
                        Entry<? extends ProdutoTableModel, ? extends Integer> linha){
                            Produto produto = model.getItem(linha.getIdentifier()); // aqui eu busco o objeto Produto que corresponde a linha analisada
                                return criterio.test(produto); // se atende ao Predicate acima, ele é combina a pesquisa com o nome ou Id
                }
            }
        );
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
