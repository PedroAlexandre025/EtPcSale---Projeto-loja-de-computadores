package view.tablemodel;

import model.consulta.ResumoCliente;

public class ResumoClienteTableModel extends TableModelBase<ResumoCliente> {

        public ResumoClienteTableModel() {
            super(
                    new String[]{
                            "ID",
                            "NOME",
                            "TELEFONE",
                            "CPF",
                            "E-mail",
                            "Vendas Finalizadas",
                            "Total gasto R$:"
                    },
                    resumo -> new Object[]{
                            resumo.getCliente().getId(),
                            resumo.getCliente().getNome(),
                            resumo.getCliente().getTelefone(),
                            resumo.getCliente().getDocumento(),
                            resumo.getCliente().getEmail(),
                            resumo.getQuantidadeVendas(),
                            resumo.getValorTotal(),
                    }

            );
        }


}
