// src/GeradorDePopulacoes.java
import java.util.List;
import java.util.Random;

public class GeradorDePopulacoes {
    private Campo campo;
    private static final double PROB_COELHO = 0.08;
    private static final double PROB_RAPOSA = 0.02;
    private static final int NUM_CACADORES = 2;
    private static final Random rand = Randomizador.obterRandom();

    public GeradorDePopulacoes(Campo campo) {
        this.campo = campo;
    }

    public void povoar(List<Ator> lista) {
        campo.limpar();
        for (int linha = 0; linha < campo.obterComprimento(); linha++) {
            for (int coluna = 0; coluna < campo.obterLargura(); coluna++) {
                if (rand.nextDouble() <= PROB_COELHO) {
                    Localizacao loc = new Localizacao(linha, coluna);
                    Coelho coelho = new Coelho(true, campo, loc);
                    lista.add(coelho);
                } else if (rand.nextDouble() <= PROB_RAPOSA) {
                    Localizacao loc = new Localizacao(linha, coluna);
                    Raposa raposa = new Raposa(true, campo, loc);
                    lista.add(raposa);
                }
            }
        }

        // Adiciona caçadores em posições aleatórias
        for (int i = 0; i < NUM_CACADORES; i++) {
            Localizacao livre = campo.localLivreAleatoria();
            if (livre != null) {
                Cacador c = new Cacador(campo, livre);
                lista.add(c);
            }
        }
    }
}
