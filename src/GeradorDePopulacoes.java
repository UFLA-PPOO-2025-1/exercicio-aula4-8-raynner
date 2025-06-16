// src/GeradorDePopulacoes.java
import java.util.List;
import java.util.Random;

public class GeradorDePopulacoes {
    private Campo campo;
    private Random rand = Randomizador.obterRandom();
    private static final double PROB_CACA = 0.002;
    private static final double PROB_COELHO = 0.08;
    private static final double PROB_RAPOSA = 0.02;

    public GeradorDePopulacoes(Campo campo) {
        this.campo = campo;
    }

    public void povoar(List<Ator> atores) {
        campo.limpar();
        for (int linha = 0; linha < campo.obterComprimento(); linha++) {
            for (int coluna = 0; coluna < campo.obterLargura(); coluna++) {
                Localizacao localizacao = new Localizacao(linha, coluna);
                double prob = rand.nextDouble();
                if (prob <= PROB_CACA) {
                    Cacador cacador = new Cacador(campo, localizacao);
                    atores.add(cacador);
                } else if (prob <= PROB_CACA + PROB_RAPOSA) {
                    Raposa raposa = new Raposa(true, campo, localizacao);
                    atores.add(raposa);
                } else if (prob <= PROB_CACA + PROB_RAPOSA + PROB_COELHO) {
                    Coelho coelho = new Coelho(true, campo, localizacao);
                    atores.add(coelho);
                }
            }
        }
    }
}
