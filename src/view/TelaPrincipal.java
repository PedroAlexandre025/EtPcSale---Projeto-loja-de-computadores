package view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.*;


public class TelaPrincipal extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel painelPrincipal = new JPanel(layout);
    private final JPanel menu = new JPanel(new GridLayout(0, 1, 0, 8));
    private final Map<String, JButton> botoes = new LinkedHashMap<>();
    private final Map<String, Runnable> aoAbrir = new LinkedHashMap<>();
    private final JLabel aviso = new JLabel(" ");

    public TelaPrincipal() {
        super("EtPcSale--Loja de computadores");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1180, 720);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        JPanel lateral = new JPanel(new BorderLayout(0, 0));
        lateral.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));
        lateral.setPreferredSize(new Dimension(230, 600));
        lateral.setBackground(new Color(107,142,35));

        //se isso nao funcionar é pq faltou a imagem

        // nao esquecer de referenciar no README qquem fez o icon <a href="https://www.flaticon.com/br/icones-gratis/et" title="et ícones">Et ícones criados por Paul J. - Flaticon</a>
        JLabel marca = new JLabel("EtPcSale");

        ImageIcon  icone = new ImageIcon (
                Objects.requireNonNull(getClass().getResource("/ResourceIcons/OEtDaLoja.png"))
        );

        // aqui fiz a definição da imagem em 40x40 pixels
        Image Imagem = icone.getImage().getScaledInstance(140,140, Image.SCALE_SMOOTH);

        marca.setIcon(new ImageIcon(Imagem));

        //centralizar o painel dos conjutno
        marca.setHorizontalAlignment(SwingConstants.CENTER);

        // nome da loja acima da imagem
        marca.setHorizontalTextPosition(SwingConstants.CENTER);
        marca.setVerticalTextPosition(SwingConstants.TOP);

        // a distancia do texto pra imagem
        marca.setIconTextGap(10);

        marca.setFont(new Font("SansSerif", Font.BOLD, 26));
        marca.setForeground(Color.WHITE);
        lateral.add(marca, BorderLayout.NORTH);

        menu.setOpaque(false);
        JPanel areaMenu = new JPanel(new BorderLayout());
        areaMenu.setOpaque(false);
        areaMenu.add(menu, BorderLayout.NORTH);
        lateral.add(areaMenu, BorderLayout.CENTER);
        JLabel descricao = new JLabel("Loja de computadores e componentes");
        descricao.setForeground(new Color(0xD5E2F0));
        lateral.add(descricao, BorderLayout.SOUTH);
        add(lateral, BorderLayout.WEST);
        add(painelPrincipal, BorderLayout.CENTER);
        aviso.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        add(aviso, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcao = JOptionPane.showConfirmDialog(TelaPrincipal.this,
                        "Deseja realmente sair do programa?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });
    }

    public void adicionarTela(String nomeTela, JPanel tela, Runnable aoAbrirTela) {
        if (botoes.containsKey(nomeTela)) {
            throw new IllegalArgumentException("Tela já cadastrada: " + nomeTela);
        }
        painelPrincipal.add(tela, nomeTela);
        JButton botao = new JButton(nomeTela);
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setMargin(new Insets(12, 10, 12, 10));
        botao.addActionListener(e -> mostrarTela(nomeTela));
        botoes.put(nomeTela, botao);
        aoAbrir.put(nomeTela, aoAbrirTela);
        menu.add(botao);
        if (botoes.size() == 1) {
            mostrarTela(nomeTela);
        }
        menu.revalidate();
        menu.repaint();
    }

    public void mostrarTela(String nomeTela) {
        if (!botoes.containsKey(nomeTela)) {
            throw new IllegalArgumentException("Tela não encontrada: " + nomeTela);
        }
        layout.show(painelPrincipal, nomeTela);
        botoes.forEach((nome, botao) -> botao.setEnabled(!nome.equals(nomeTela)));
        Runnable acao = aoAbrir.get(nomeTela);
        if (acao != null) {
            acao.run();
        }
    }

    public void aviso(String mensagem) {
        aviso.setText(mensagem);
    }
}
