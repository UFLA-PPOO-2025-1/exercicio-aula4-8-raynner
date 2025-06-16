// src/Simulador.java
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Simulador {
    private static final int LARGURA_PADRAO = 80;
    private static final int ALTURA_PADRAO = 50;

    private List<Ator> atores;
    private Campo campo;
    private int passo;
    private VisaoSimulador visao;

    public Simulador() {
        this(LARGURA_PADRAO, ALTURA_PADRAO);
    }

    public Simulador(int largura, int altura) {
        atores = new ArrayList<>();
        campo = new Campo(altura, largura);
        visao = new VisaoDeGrade(altura, largura); // ✅ classe concreta aqui

        // Define cores
        visao.definirCor(Coelho.class, Color.ORANGE);
        visao.definirCor(Raposa.class, Color.BLUE);
        visao.definirCor(Cacador.class, Color.BLACK);

        criarPopulacao();
        visao.mostrarStatus(passo, campo); // método correto da interface
    }

    public void simular(int passos) {
        for (int i = 0; i < passos; i++) {
            simularUmPasso();
        }
    }

    public void simularUmPasso() {
        passo++;

        List<Ator> novos = new ArrayList<>();
        for (Ator ator : atores) {
            ator.agir(novos);
        }

        atores.removeIf(a -> !a.estaAtivo());
        atores.addAll(novos);

        visao.mostrarStatus(passo, campo);
    }

    public void resetar() {
        passo = 0;
        atores.clear();
        criarPopulacao();
        visao.reiniciar();
        visao.mostrarStatus(passo, campo);
    }

    private void criarPopulacao() {
        GeradorDePopulacoes gerador = new GeradorDePopulacoes(campo);
        gerador.povoar(atores);
    }
}
