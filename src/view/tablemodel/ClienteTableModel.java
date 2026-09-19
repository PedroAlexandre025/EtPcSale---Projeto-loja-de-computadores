package view.tablemodel;

import model.entitties.Cliente;

public class ClienteTableModel extends TableModelBase<Cliente> {

    public ClienteTableModel() {
        super(new String[]{"ID", "Nome", "Telefone", "CPF", "E-mail"},
                cliente -> new Object[]{cliente.getId(), cliente.getNome(),
                    cliente.getTelefone(), cliente.getDocumento(), cliente.getEmail()});
    }
}
