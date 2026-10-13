package view;

import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;


// esse Forumalrio panel vai criar um sempre que for chamado em cada situação, cadastro Cliente,
// venda, item etc... AS classes usam ele para criar cada interação que deve, de acordo com suas regras

public class FormularioPanel extends JPanel {
    protected final JPanel panel = new JPanel(new GridLayout(0, 4, 12, 8));
    protected final JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    public final JTable tabela = new JTable();
    public final JScrollPane resumo = new JScrollPane(new JLabel(" "));

    public FormularioPanel(String titulo) {
        setLayout(new BorderLayout(12, 16));
        Color fundo = new Color(143,188,143);
        setBackground(fundo);

        // cor pro tras do botoes
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel topo = new JPanel(new BorderLayout(0, 16));
        topo.setOpaque(false);

        JLabel cabecalho = new JLabel(titulo);
        cabecalho.setFont(new Font("SansSerif", Font.BOLD, 24));
        topo.add(cabecalho, BorderLayout.NORTH);
        topo.add(panel, BorderLayout.CENTER);
        topo.add(acoes, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        tabela.setBackground(new Color(245, 250, 245));
        tabela.setForeground(Color.BLACK);
        resumo.setBorder(BorderFactory.createEmptyBorder());
        resumo.setPreferredSize(new Dimension(0, 38));
        resumo.getViewport().setBackground(fundo);

        add(resumo, BorderLayout.SOUTH);
        configurarTabela(tabela);
    }

    protected void campo(String nome, JComponent componente) {
        JLabel label = new JLabel(nome);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        label.setLabelFor(componente);
        componente.setPreferredSize(new Dimension(150, 32));
        panel.add(label);
        panel.add(componente);
    }

    protected JButton botao(String nome) {
        JButton botao = new JButton(nome);
        botao.setMargin(new Insets(8, 14, 8, 14));
        acoes.add(botao);
        return botao;
    }

    protected JButton botao(String nome, Runnable acao) {
        JButton botao = botao(nome);
        botao.addActionListener(e -> acao.run());
        return botao;
    }

    public static void configurarTabela(JTable tabela) {
        tabela.setRowHeight(30);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setFillsViewportHeight(true);
        tabela.setShowVerticalLines(false);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setDefaultRenderer(BigDecimal.class, new DefaultTableCellRenderer() {
            private final NumberFormat moeda = NumberFormat.getCurrencyInstance(
                    Locale.forLanguageTag("pt-BR"));

            @Override
            protected void setValue(Object valor) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                setText(valor == null ? "" : moeda.format(valor));
            }
        });
    }

    public void mensagem(String mensagem) {
        resumo.setViewportView(new JLabel(mensagem));
    }

    public boolean confirmar(String mensagem) {
        return JOptionPane.showConfirmDialog(this, mensagem, "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
