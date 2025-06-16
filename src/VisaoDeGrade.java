import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VisaoDeGrade implements VisaoSimulador {
    private JFrame frame;
    private JPanel grade;
    private MapeadorDeCores mapeador;
    private int largura;
    private int altura;
    private Campo campo; // ✅ ADICIONADO

    private static final Color COR_PADRAO = Color.LIGHT_GRAY;

    public VisaoDeGrade(int altura, int largura) {
        this.altura = altura;
        this.largura = largura;
        mapeador = new MapeadorDeCores();
        configurarJanela();
    }

    private void configurarJanela() {
        frame = new JFrame("Simulador Ecológico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grade = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenhar(g);
            }
        };
        grade.setPreferredSize(new Dimension(largura * 10, altura * 10));
        frame.add(grade);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void definirCor(Class<?> tipo, Color cor) {
        mapeador.definirCor(tipo, cor);
    }

    @Override
    public boolean ehViavel(Campo campo) {
        return true;
    }

    @Override
    public void mostrarStatus(int passo, Campo campo) {
        this.campo = campo; // ✅ SALVO PARA USAR EM desenhar()
        grade.repaint();
        frame.setTitle("Passo: " + passo);
    }

    @Override
    public void reiniciar() {}

    @Override
    public void reabilitarOpcoes() {}

    private void desenhar(Graphics g) {
        for (int linha = 0; linha < altura; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                Object objeto = campo.obterObjetoEm(linha, coluna);  // ✅ AGORA FUNCIONA
                Color cor = COR_PADRAO;
                if (objeto != null) {
                    cor = mapeador.obterCor(objeto.getClass());
                }
                g.setColor(cor);
                g.fillRect(coluna * 10, linha * 10, 10, 10);
            }
        }
    }

    private static class MapeadorDeCores {
        private final Map<Class<?>, Color> mapa = new HashMap<>();

        public void definirCor(Class<?> tipo, Color cor) {
            mapa.put(tipo, cor);
        }

        public Color obterCor(Class<?> tipo) {
            return mapa.getOrDefault(tipo, COR_PADRAO);
        }
    }
}
