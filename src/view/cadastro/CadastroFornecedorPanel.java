package view.cadastro;

import javax.swing.*;

import model.entitties.Fornecedor;
import view.FormularioPanel;
import view.tablemodel.FornecedorTableModel;
import java.util.List;
import javax.swing.JComboBox;
import model.entitties.Produto;

public class CadastroFornecedorPanel extends FormularioPanel {



    public final JTextField nome = new JTextField();
    public final JTextField telefone = new JTextField();
    public final JTextField cnpj = new JTextField();
    public final JTextField razaoSocial = new JTextField();
    public final JComboBox<Produto> prodFornecido = new JComboBox<>();

    public final JButton salvar, remover, limpar;
    public final FornecedorTableModel model = new FornecedorTableModel();

    public CadastroFornecedorPanel() {
        super("Cadastro de fornecedores");

        campo("Nome", nome);
        campo("Telefone", telefone);
        campo("CNPJ", cnpj);
        campo("Razão social", razaoSocial);
        campo("Produto fornecido", prodFornecido);

        salvar = botao("Salvar");
        remover = botao("Remover");
        limpar = botao("Limpar", this::limparCampos);

        tabela.setModel(model);
        configurarTabela(tabela);

        tabela.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting()) {
                Fornecedor fornecedor = getSelecionado();

                if (fornecedor != null) {
                    preencherCampos(fornecedor);
                }
            }
        });

        mensagem("Preencha os campos ou selecione um fornecedor para visualizar seus dados.");
    }

    public void limparCampos() {
        tabela.clearSelection();
        nome.setText("");
        telefone.setText("");
        cnpj.setText("");
        razaoSocial.setText("");
        nome.requestFocusInWindow();
        prodFornecido.setSelectedIndex(-1);
    }

    public void preencherCampos(Fornecedor fornecedor) {
        if (fornecedor == null) {
            limparCampos();
            return;
        }

        nome.setText(fornecedor.getNome());
        telefone.setText(fornecedor.getTelefone());
        cnpj.setText(fornecedor.getDocumento());
        razaoSocial.setText(fornecedor.getRazaoSocial());
        prodFornecido.setSelectedItem(fornecedor.getProdutoFornecido());
    }

    public Fornecedor getSelecionado() {
        int linha = tabela.getSelectedRow();

        if (linha < 0) {
            return null;
        }

        return model.getItem(tabela.convertRowIndexToModel(linha));
    }

    public void definirProd(List<Produto> prod) {
        prodFornecido.removeAllItems();

        for (Produto produto : prod) {
            prodFornecido.addItem(produto);
        }

        prodFornecido.setSelectedIndex(-1);
    }

    public Produto getProdutoSelecionado() {
        return (Produto) prodFornecido.getSelectedItem();
    }
}