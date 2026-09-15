package etpcsale.view.tablemodel;

import etpcsale.model.entitties.Cliente;

public class ClienteTableModel {

    public ClienteTableModel(Cliente cliente) {
        super(new String[]{"ID", "Nome", "Telefone", "CPF", "Email"}, 
        p -> new Object[]{cliente.getId(), cliente.getNome(), 
            cliente.getTelefone(), cliente.getDocumento(), cliente.getEmail()}  );
    }
}
