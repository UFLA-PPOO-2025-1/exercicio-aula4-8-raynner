// src/Coelho.java
import java.util.List;

public class Coelho extends Animal {
    private static final int IDADE_REPRODUCAO = 5;
    private static final int IDADE_MAXIMA = 40;
    private static final double PROBABILIDADE_REPRODUCAO = 0.12;
    private static final int TAMANHO_MAXIMO_NINHADA = 4;

    public Coelho(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(idadeAleatoria, campo, localizacao);
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        incrementarIdade();
        if (estaAtivo()) {
            reproduzir(novosAtores);
            Campo campo = obterCampo();
            if (campo != null) {
                Localizacao novaLocalizacao = campo.localizacaoVizinhaLivre(obterLocalizacao());
                if (novaLocalizacao != null) {
                    definirLocalizacao(novaLocalizacao);
                } else {
                    morrer();
                }
            }
        }
    }

    @Override
    protected int obterIdadeMaxima() {
        return IDADE_MAXIMA;
    }

    @Override
    protected Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        return new Coelho(false, campo, localizacao);
    }

    @Override
    protected int obterIdadeReproducao() {
        return IDADE_REPRODUCAO;
    }

    @Override
    protected double obterProbabilidadeReproducao() {
        return PROBABILIDADE_REPRODUCAO;
    }

    @Override
    protected int obterTamanhoMaximoNinhada() {
        return TAMANHO_MAXIMO_NINHADA;
    }
}
