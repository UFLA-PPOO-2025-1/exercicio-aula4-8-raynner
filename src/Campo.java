// src/Campo.java
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Campo {
    private Object[][] campo;
    private int comprimento;
    private int largura;
    private static final Random rand = Randomizador.obterRandom();

    public Campo(int comprimento, int largura) {
        this.comprimento = comprimento;
        this.largura = largura;
        campo = new Object[comprimento][largura];
    }

    public void limpar() {
        for (int linha = 0; linha < comprimento; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                campo[linha][coluna] = null;
            }
        }
    }

    public void limpar(Localizacao localizacao) {
        campo[localizacao.obterLinha()][localizacao.obterColuna()] = null;
    }

    public void colocar(Object objeto, Localizacao localizacao) {
        campo[localizacao.obterLinha()][localizacao.obterColuna()] = objeto;
    }

    public Object getObjeto(Localizacao localizacao) {
        return campo[localizacao.obterLinha()][localizacao.obterColuna()];
    }

    public Object obterObjetoEm(int linha, int coluna) {
        return campo[linha][coluna];
    }

    public Localizacao localLivreAleatoria() {
        List<Localizacao> livres = new LinkedList<>();
        for (int linha = 0; linha < comprimento; linha++) {
            for (int coluna = 0; coluna < largura; coluna++) {
                if (campo[linha][coluna] == null) {
                    livres.add(new Localizacao(linha, coluna));
                }
            }
        }
        if (livres.isEmpty()) return null;
        return livres.get(rand.nextInt(livres.size()));
    }

    public Localizacao localAleatoria() {
        int linha = rand.nextInt(comprimento);
        int coluna = rand.nextInt(largura);
        return new Localizacao(linha, coluna);
    }

    public int obterComprimento() {
        return comprimento;
    }

    public int obterLargura() {
        return largura;
    }

    /**
     * Retorna uma lista embaralhada com todas as localizações vizinhas válidas.
     */
    public List<Localizacao> localizacoesVizinhas(Localizacao localizacao) {
        List<Localizacao> vizinhos = new LinkedList<>();
        int linha = localizacao.obterLinha();
        int coluna = localizacao.obterColuna();

        for (int deltaLinha = -1; deltaLinha <= 1; deltaLinha++) {
            int novaLinha = linha + deltaLinha;
            if (novaLinha < 0 || novaLinha >= comprimento) continue;

            for (int deltaColuna = -1; deltaColuna <= 1; deltaColuna++) {
                int novaColuna = coluna + deltaColuna;
                if (novaColuna < 0 || novaColuna >= largura) continue;
                if (deltaLinha == 0 && deltaColuna == 0) continue;

                vizinhos.add(new Localizacao(novaLinha, novaColuna));
            }
        }

        Collections.shuffle(vizinhos, rand);
        return vizinhos;
    }

    /**
     * Retorna as localizações vizinhas que estão livres (sem objeto).
     */
    public List<Localizacao> localizacoesVizinhasLivres(Localizacao localizacao) {
        List<Localizacao> livres = new LinkedList<>();
        for (Localizacao loc : localizacoesVizinhas(localizacao)) {
            if (getObjeto(loc) == null) {
                livres.add(loc);
            }
        }
        return livres;
    }

    /**
     * Retorna uma única localização vizinha livre, ou null se não houver.
     */
    public Localizacao localizacaoVizinhaLivre(Localizacao localizacao) {
        List<Localizacao> livres = localizacoesVizinhasLivres(localizacao);
        if (!livres.isEmpty()) {
            return livres.get(0);
        }
        return null;
    }
}
