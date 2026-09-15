package etpcsale.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel painelPrincipal = new JPanel(layout);
    private final JPanel menu = new JPanel(new GridBagLayout(0, 1, 0, 8));

    public TelaPrincipal(){
        super ( "EtPcSale - loja de computadores" );

        setDefaultCloseOperation(DDO_NOTHING_ON_CLOSE);
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);
        JPanel lateral = new JPanel(new BorderLayout(0, 24));
        lateral.setBorder(BorderFactory.createEmptyBorder(24, 16, 16, 24));
        lateral.setPreferredSize(new Dimension(210, 600));
        lateral.setBackground(new Color(0x1E1E1E));
        JLabel marca = new JLabel("EtPcSale");
        marca.setFont(new Font("Arial", Font.BOLD, 24));
        marca.setForeground(getForeground(new Color(30, 85, 160)));

        add(lateral, BorderLayout.WEST); 
        add(conteudo, BorderLayout.CENTER);
        JLabel avisoJLabel = new JLabel("Aviso: Fechar a janela encerrará o programa");
        avisoJlabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(aviso, BorderLayout.SOUTH);
        avisoJLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcao = JOptionPane.showConfirmDialog(TelaPrincipal.this, "Deseja realmente sair do programa?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public  void adicionarTela(String nomeTela, JPanel tela, Runnable aoAbrirTela){
        conteudo.add(tela, nomeTela);
        JButton botao = new JButton(nomeTela);
        menu.add(botao);
        botao.addActionListener(e -> {
            layout.show(conteudo, nomeTela);
            if (aoAbrirTela != null) {
                aoAbrirTela.run();
            }
        });
    }

}