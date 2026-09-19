package view.tablemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.swing.table.AbstractTableModel;


// isso aqui é nosso modelo de Tabela !! que vai herdando pra vendas, item, produto terem a tabela lá tbm;;

public abstract class TableModelBase<T> extends AbstractTableModel {

    private final String[] colunas;
    private final Function<T, Object[]> extrairLinha;
    private final List<T> dados = new ArrayList<>();

    protected TableModelBase(String[] colunas, Function<T, Object[]> extrairLinha) {
        this.colunas = colunas.clone();
        this.extrairLinha = extrairLinha;
    }

    public void setDados(List<T> novosDados) {
        dados.clear();
        dados.addAll(novosDados);
        fireTableDataChanged();
    }

    public T getItem(int linha) {
        return dados.get(linha);
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        return extrairLinha.apply(dados.get(linha))[coluna];
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        if (dados.isEmpty()) {
            return Object.class;
        }
        Object valor = getValueAt(0, coluna);
        return valor == null ? Object.class : valor.getClass();
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}
