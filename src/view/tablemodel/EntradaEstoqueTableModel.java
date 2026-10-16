package view.tablemodel;

import java.time.format.DateTimeFormatter;
import model.entitties.EntradaEstoque;

public class EntradaEstoqueTableModel
        extends TableModelBase<EntradaEstoque> {

    private static final DateTimeFormatter DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public EntradaEstoqueTableModel() {
        super(
                new String[]{
                        "ID", "Data", "Produto", "Fornecedor", "Quantidade",
                        "Custo unitário (R$)", "Custo total (R$)"
                },
                entrada -> new Object[]{
                        entrada.getId(),
                        entrada.getDataEntrada().format(DATA),
                        entrada.getNomeProduto(),
                        entrada.getRazaoSocialFornecedor(),
                        entrada.getQuantidade(),
                        entrada.getCustoUnitario(),
                        entrada.getCustoTotal()
                }
        );
    }
}