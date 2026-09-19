package view.tablemodel;

import java.time.format.DateTimeFormatter;
import model.entitties.Venda;

public class VendaTableModel extends TableModelBase<Venda> {

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VendaTableModel() {
        super(new String[]{"ID", "Cliente", "Data", "Pagamento", "Desconto (R$)",
            "Total (R$)", "Status"},
                venda -> new Object[]{venda.getId(), venda.getCliente().getNome(),
                    venda.getData().format(FORMATO_DATA), venda.getFormaPagamentos(),
                    venda.getDesconto(), venda.getTotal(), venda.getStatus()});
    }
}
