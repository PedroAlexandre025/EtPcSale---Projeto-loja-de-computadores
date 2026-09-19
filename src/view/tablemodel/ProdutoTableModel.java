package view.tablemodel;

import model.entitties.Produto;

public class ProdutoTableModel extends TableModelBase<Produto> {

    public ProdutoTableModel() {
        super(new String[]{"ID", "Nome", "Categoria", "Preço (R$)", "Estoque"},
                produto -> new Object[]{produto.getId(), produto.getNome(),
                    produto.getCategoria(), produto.getPreco(), produto.getEstoque()});
    }
}
