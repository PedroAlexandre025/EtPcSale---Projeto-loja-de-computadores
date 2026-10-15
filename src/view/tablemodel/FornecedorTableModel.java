package view.tablemodel;

import model.entitties.Fornecedor;

public class FornecedorTableModel
        extends TableModelBase<Fornecedor> {

    public FornecedorTableModel() {
        super(
                new String[]{
                        "CNPJ",
                        "Razão social",
                        "ID",
                        "Telefone"
                },
                fornecedor -> new Object[]{
                        fornecedor.getDocumento(),
                        fornecedor.getRazaoSocial(),
                        fornecedor.getId(),
                        fornecedor.getTelefone()
                }
        );
    }
}