package etpcsale.view;

import java.awt.*;
import javax.swing.*;


public class FormularioPanel extends JPanel {

    protected final JPanel panel = new JPanel(new GridLayout(0, 4, 12, 8));
    protected final JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
    public final JTable tabela = new JTable();
    public final JScrollPane resumo = new JScrollPane(new JLabel(" "));

    public FormularioPanel(String titulo){

        setLayout(new BorderLayout(12, 12));

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 14));

        JLabel cabecalho = new JLabel(titulo);

        cabecalho.setFont(new Font("Arial", Font.BOLD, 24));

        topo.add(cabecalho, BorderLayout.NORTH);

        topo.add(panel, BorderLayout.CENTER);

        topo.add(acoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(resumo, BorderLayout.SOUTH);
        tabela.setRowHeight(28);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(false);
        tabela.getTableHeader().setReorderingAllowed(false);
        
    }

    protected void campo(String nome, JComponent componente){
        JLabel label = new JLabel(nome);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        panel.add(componente);
    }

    protected JButton botao(String nome, Runnable acao){
        JButton botao = new JButton(nome);
        botao.addActionListener(e -> acao.run());
        acoes.add(botao);
        return botao;
    }

    public void mensagem(String mensagem){
        resumo.setViewportView(new JLabel(mensagem));
    }

    public boolean confirmar(String mensagem){
        int opcao = JOptionPane.showConfirmDialog(panel, mensagem, "Confirmação", JOptionPane.YES_NO_OPTION);
        return opcao == JOptionPane.YES_OPTION;
    }

}

