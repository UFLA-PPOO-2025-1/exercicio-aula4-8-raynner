// src/Animal.java
import java.util.List;
import java.util.Random;

public abstract class Animal implements Ator {
    private static final Random rand = Randomizador.obterRandom();

    private int idade;
    private boolean vivo;
    private Localizacao localizacao;
    private Campo campo;

    public Animal(boolean idadeAleatoria, Campo campo, Localizacao localizacao) {
        vivo = true;
        idade = 0;
        if (idadeAleatoria) {
            idade = rand.nextInt(obterIdadeMaxima());
        }
        this.campo = campo;
        definirLocalizacao(localizacao);
    }

    public int obterIdade() {
        return idade;
    }

    protected void incrementarIdade() {
        idade++;
        if (idade > obterIdadeMaxima()) {
            morrer();
        }
    }

    @Override
    public boolean estaAtivo() {
        return vivo;
    }

    public void morrer() {
        vivo = false;
        if (localizacao != null) {
            campo.limpar(localizacao);
            localizacao = null;
            campo = null;
        }
    }

    public Localizacao obterLocalizacao() {
        return localizacao;
    }

    protected void definirLocalizacao(Localizacao novaLocalizacao) {
        if (localizacao != null) {
            campo.limpar(localizacao);
        }
        localizacao = novaLocalizacao;
        campo.colocar(this, novaLocalizacao);
    }

    public Campo obterCampo() {
        return campo;
    }

    protected int procriar() {
        int nascimentos = 0;
        if (podeProcriar() && rand.nextDouble() <= obterProbabilidadeReproducao()) {
            nascimentos = rand.nextInt(obterTamanhoMaximoNinhada()) + 1;
        }
        return nascimentos;
    }

    protected void reproduzir(List<Ator> novosAtores) {
        List<Localizacao> livres = campo.localizacoesVizinhasLivres(localizacao);
        int nascimentos = procriar();
        for (int i = 0; i < nascimentos && !livres.isEmpty(); i++) {
            Localizacao local = livres.remove(0);
            Animal filhote = criarNovoFilhote(false, campo, local);
            novosAtores.add(filhote);
        }
    }

    private boolean podeProcriar() {
        return idade >= obterIdadeReproducao();
    }

    @Override
    public abstract void agir(List<Ator> novosAtores);

    protected abstract int obterIdadeMaxima();
    protected abstract Animal criarNovoFilhote(boolean idadeAleatoria, Campo campo, Localizacao localizacao);
    protected abstract int obterIdadeReproducao();
    protected abstract double obterProbabilidadeReproducao();
    protected abstract int obterTamanhoMaximoNinhada();
}
