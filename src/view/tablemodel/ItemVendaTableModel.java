package view.tablemodel;

import model.entitties.ItemVenda;

public class ItemVendaTableModel extends TableModelBase<ItemVenda> {

    public ItemVendaTableModel() {
        super(new String[]{"Produto", "Quantidade", "Preço unitário (R$)", "Subtotal (R$)"},
                item -> new Object[]{item.getProduto().getNome(), item.getQuantidade(),
                    item.getPrecoUnitario(), item.getSubTotal()});
    }
}
