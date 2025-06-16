// src/Raposa.java
import java.util.List;

public class Raposa extends Animal {
    private static final int IDADE_REPRODUCAO = 10;
    private static final int IDADE_MAXIMA = 100;
    private static final double PROBABILIDADE_REPRODUCAO = 0.08;
    private static final int TAMANHO_MAXIMO_NINHADA = 2;
    private static final int VALOR_ALIMENTO_COELHO = 9;

    private int comida;

    public Raposa(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        super(idadeAleatoria, campo, localizacao);
        comida = idadeAleatoria ? Randomizador.obterRandom().nextInt(VALOR_ALIMENTO_COELHO) : VALOR_ALIMENTO_COELHO;
    }

    @Override
    public void agir(List<Ator> novosAtores) {
        incrementarIdade();
        comida--;
        if (estaAtivo()) {
            if (comida <= 0) {
                morrer();
                return;
            }

            Campo campo = obterCampo();
            Localizacao destino = encontrarComida();

            if (destino == null && campo != null) {
                destino = campo.localizacaoVizinhaLivre(obterLocalizacao());
            }

            if (destino != null) {
                definirLocalizacao(destino);
            } else {
                morrer();
            }

            reproduzir(novosAtores);
        }
    }

    private Localizacao encontrarComida() {
        List<Localizacao> vizinhos = obterCampo().localizacoesVizinhas(obterLocalizacao());
        for (Localizacao loc : vizinhos) {
            Object obj = obterCampo().obterObjetoEm(loc);
            if (obj instanceof Coelho coelho && coelho.estaAtivo()) {
                coelho.morrer();
                comida = VALOR_ALIMENTO_COELHO;
                return loc;
            }
        }
        return null;
    }

    @Override
    protected int obterIdadeMaxima() {
        return IDADE_MAXIMA;
    }

    @Override
    protected Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        return new Raposa(false, campo, localizacao);
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
