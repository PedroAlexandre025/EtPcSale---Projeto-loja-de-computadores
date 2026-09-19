package view.tablemodel;

import model.entitties.Fornecedor;

public class FornecedorTableModel extends TableModelBase<Fornecedor> {

    public FornecedorTableModel() {
        super(
                new String[]{"CNPJ", "Razão social", "ID", "Telefone", "Fornecimento"},
                fornecedor -> new Object[]{
                        fornecedor.getDocumento(),
                        fornecedor.getRazaoSocial(),
                        fornecedor.getId(),
                        fornecedor.getTelefone(),
                        fornecedor.getProdutoFornecido() == null
                                ? "Não informado"
                                : fornecedor.getProdutoFornecido().getNome()
                }
        );
    }
}
